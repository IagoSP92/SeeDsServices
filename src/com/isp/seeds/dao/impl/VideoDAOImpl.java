package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.dao.utils.Utils;
import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.exceptions.InstanceNotFoundException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.util.Results;

public class VideoDAOImpl extends ContenidoDAOImpl implements VideoDAO {
	
	private static Logger logger = LogManager.getLogger(VideoDAOImpl.class);
	
	ContenidoDAO contenidoDao = null;

	public VideoDAOImpl( ) {
		contenidoDao = new ContenidoDAOImpl();
	}
	
	
	@Override
	public Video findById(Connection connection, Long idSesion, Long idVideo) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			StringBuilder queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
							+", V.DESCRIPCION, V.URL_VIDEO "
							+", UC.DENUNCIADO, UC.GUARDADO, UC.VALORACION, UC.COMENTARIO "
							+" FROM VIDEO V INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = V.ID_CONTENIDO ) "
							+" LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO=UC.CONTENIDO_ID_CONTENIDO) ");
			
			if(idSesion!=null) { queryString.append(" AND (UC.USUARIO_ID_CONTENIDO= ? ) ");}
			queryString.append(" WHERE V.ID_CONTENIDO = ? ");
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if(idSesion!=null) {
				preparedStatement.setLong(i++, idSesion);
			}			
			preparedStatement.setLong(i++, idVideo);
 
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();

			Video video = null;

			if (resultSet.next()) {
				video = loadNext(connection, resultSet);				
			} else {
				logger.debug("Video con id= {} no encontrado.", idVideo );
				throw new DataException(" Video con id= "+idVideo+" no encontrado.");
			}
			return video;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}


	@Override
	public Results<Video> verVideosLista(Connection connection, Long idLista, int startIndex, int count) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
							+", V.DESCRIPCION, V.URL_VIDEO "
					+" FROM VIDEO V INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = V.ID_CONTENIDO ) "
					+" INNER JOIN VIDEO_LISTA VL ON (VL.VIDEO_ID_CONTENIDO = V.ID_CONTENIDO ) "
					+" LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO ) "
					+" WHERE VL.LISTA_ID_CONTENIDO = ? GROUP BY VL.VIDEO_ID_CONTENIDO ORDER BY VL.POSICION ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idLista);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			List<Video> page = new ArrayList<Video>();    
			Video video = null;

			int currentCount = 0;
			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					video = loadNext(connection, resultSet);
					page.add(video);
					currentCount++;
				} while ((currentCount < count) &&  resultSet.next()) ;
			}
			int totalRows = JDBCUtils.getTotalRows(resultSet);
			Results<Video> results = new Results<Video>(page, startIndex, totalRows);
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}  finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}
	
	
	@Override
	public List<Contenido> verContenidosLista(Connection connection, Long idLista) throws DataException {
		
		PreparedStatement preparedStatement = null; 
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+" FROM VIDEO V INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = V.ID_CONTENIDO ) "
					+" INNER JOIN VIDEO_LISTA VL ON (VL.VIDEO_ID_CONTENIDO = V.ID_CONTENIDO ) "
					+" LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO ) "
					+" WHERE VL.LISTA_ID_CONTENIDO = ? GROUP BY VL.VIDEO_ID_CONTENIDO ORDER BY VL.POSICION ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idLista);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			List<Contenido> results = new ArrayList<Contenido>();    
			Contenido video = null;

			if (resultSet.next()) {
				do {
					video = ContenidoDAOImpl.loadNext(connection, resultSet);
					results.add(video);  
				} while (resultSet.next()) ;
			}			
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}  finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}
	
	

	
	@Override
	public Video create(Connection connection, Video video) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			video= (Video) contenidoDao.create(connection, video);

			String queryString = "INSERT INTO Video (ID_CONTENIDO, USUARIO_ID_CONTENIDO, DESCRIPCION, URL_VIDEO ) "
					+ "VALUES (?, ?, ?, ? )";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);			
			int i = 1;    
			preparedStatement.setLong(i++, video.getId());
			preparedStatement.setLong(i++, video.getAutor());
			preparedStatement.setString(i++, video.getDescripcion());
			preparedStatement.setString(i++, video.getUrl());	
		
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				logger.debug("No se ha podido insertar en tabla VIDEO");
				throw new SQLException("No se ha podido insertar en tabla VIDEO");
			}			
			return video;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}
	

	@Override
	public void update(Connection connection, Video video) throws DataException {
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {			
			queryString = new StringBuilder(" UPDATE VIDEO " );
			
			boolean first = true;

			if (video.getDescripcion()!=null) {
				addUpdate(queryString, first, " DESCRIPCION = ? ");
				first = false;
			}						
			if (video.getUrl()!=null) {
				addUpdate(queryString, first, " URL_VIDEO = ? ");
				first = false;
			}
			
			queryString.append("WHERE ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			if (video.getDescripcion()!=null)
				preparedStatement.setString(i++,video.getDescripcion());
			
			if (video.getUrl()!=null)
				preparedStatement.setString(i++,video.getUrl());

			
			preparedStatement.setLong(i++, video.getId());

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				logger.debug("Video con id= {} no encontrado.", video.getId() );
				throw new InstanceNotFoundException(video.getId(), Video.class.getName());
			}

			if (updatedRows > 1) {
				logger.debug("Video con id= {} duplicado.", video.getId() );
				throw new SQLException("Video con id= '" + 
						video.getId() + " duplicado en tabla VIDEO");
			}     			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);  
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}

	
	@Override
	public void delete(Connection connection, Long id) throws DataException {
		PreparedStatement preparedStatement = null;

		try {
			String queryString = "DELETE FROM video WHERE id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				logger.debug("No se ha podido eliminar en al tabla VIDEO" );
				throw new SQLException("No se ha podido eliminar en al tabla VIDEO");
			}			
			contenidoDao.delete(connection, id);
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}
	
		
	@Override
	public Results<Contenido> cargarGuardados(Connection connection, Long idSesion, int startIndex, int count)
			throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+" FROM CONTENIDO C INNER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO) "
							+" AND (UC.USUARIO_ID_CONTENIDO= ? ) "
					+" INNER JOIN VIDEO V ON (V.ID_CONTENIDO=C.ID_CONTENIDO)"
					+" WHERE UC.USUARIO_ID_CONTENIDO = ? AND UC.GUARDADO= TRUE "
					+" GROUP BY UC.CONTENIDO_ID_CONTENIDO");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i=1;
			preparedStatement.setLong(i++, idSesion);
			preparedStatement.setLong(i++, idSesion);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			Contenido contenido = new Contenido();
			List<Contenido> page = new ArrayList<Contenido>();
			int currentCount = 0;
			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					contenido = ContenidoDAOImpl.loadNext(connection, resultSet);
					page.add(contenido);
					currentCount++;
				} while ((currentCount < count) &&  resultSet.next()) ;
			}
			int totalRows = JDBCUtils.getTotalRows(resultSet);
			Results<Contenido> results = new Results<Contenido>(page, startIndex, totalRows);
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}

		
	
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}
	
	protected static Video loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				Long idContenido = resultSet.getLong(i++);
				String nombre = resultSet.getString(i++);
				Date fechaAlta =  resultSet.getDate(i++);
				Date fechaMod =  resultSet.getDate(i++);
				Long autor = resultSet.getLong(i++);	
				Integer tipo = resultSet.getInt(i++);
				Integer reproducciones = resultSet.getInt(i++);
				Double valoracion = resultSet.getDouble(i++);
				
				String descripcion = resultSet.getString(i++);
				String url = resultSet.getString(i++);
			
				Video v = new Video();
				v.setId(idContenido);
				v.setNombre(nombre);
				v.setFechaAlta(fechaAlta);
				v.setFechaMod(fechaMod);
				v.setAutor(autor);
				v.setTipo(tipo);
				v.setReproducciones(reproducciones);
				v.setValoracion(valoracion);

				v.setDescripcion(descripcion);
				v.setUrl(url);
				
				if(i<resultSet.getMetaData().getColumnCount()) {
					Boolean denunciado = resultSet.getBoolean(i++);
					Boolean guardado = resultSet.getBoolean(i++);
					Double valorado = resultSet.getDouble(i++);
					String comentado = resultSet.getString(i++);					
					v.setDenunciado(denunciado);
					v.setGuardado(guardado);	
					v.setValorado(valorado);
					v.setComentado(comentado);					
					v.setComentarios(Utils.cargarComentarios(connection, idContenido));
				}				
				return v;
			}


}
