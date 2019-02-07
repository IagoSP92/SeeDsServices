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

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.Exceptions.InstanceNotFoundException;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;

public class VideoDAOImpl extends ContenidoDAOImpl implements VideoDAO {
	
	private static Logger logger = LogManager.getLogger(VideoDAOImpl.class);


	@Override
	public Video findById(Connection connection, Long id) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
							+ " v.descripcion, v.reproducciones, v.url_video " + 
							"FROM Video v INNER JOIN Contenido c ON (c.id_contenido = v.id_contenido ) " +
							"WHERE v.id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);
 
			resultSet = preparedStatement.executeQuery();

			Video e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				logger.debug("Video con id= {} no encontrado.", id );
				throw new DataException("\n Video with id " +id+ "not found\n");
			}

			return e;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}

	
//	@Override
//	public List<Video> findByCriteria(Connection connection, VideoCriteria video) throws DataException {
//
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		StringBuilder queryString = null;
//
//		try {	
//			queryString = new StringBuilder(
//					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
//							+ " v.descripcion, v.reproducciones, v.url_video " + 
//							" FROM Video v INNER JOIN Contenido c ON (c.id_contenido = v.id_contenido ) ");
//			
//			boolean first = true;
//
//			if (video.getIdContenido()!=null) {
//				addClause(queryString, first, " v.ID_CONTENIDO LIKE ? ");
//				first = false;
//			}
//			
//			if (video.getDescripcion()!=null) {
//				addClause(queryString, first, " v.descripcion LIKE ? ");
//				first = false;
//			}
//
//			if (video.getReproducciones()!=null) {
//				addClause(queryString, first, " v.reproducciones LIKE ? ");
//				first = false;
//			}			
//			
//			if (video.getUrl()!=null) {
//				addClause(queryString, first, " v.url_video LIKE ? ");
//				first = false;
//			}	
//			/*
//			if (idioma!=null) {
//				addClause(queryString, first, " pi.id_idioma LIKE ? ");
//				first = false;
//			}*/
//
//			preparedStatement = connection.prepareStatement(queryString.toString(),
//					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			int i = 1;
//			if (video.getIdContenido()!=null)
//				preparedStatement.setString(i++, "%" + video.getIdContenido() + "%");
//			if (video.getDescripcion()!=null) 
//				preparedStatement.setString(i++, "%" + video.getDescripcion() + "%");
//			if (video.getReproducciones()!=null)
//				preparedStatement.setString(i++, "%" + video.getReproducciones() + "%");
//			if (video.getUrl()!=null) 
//				preparedStatement.setString(i++, "%" + video.getUrl() + "%");
//			/*
//			if (idioma!=null) 
//				preparedStatement.setString(i++,idioma);
//				*/			
//			resultSet = preparedStatement.executeQuery();
//
//			List<Video> results = new ArrayList<Video>(); 
//
//			Video e = null;
//			//int currentCount = 0;
//
//			//if ((startIndex >=1) && resultSet.absolute(startIndex)) {
//			if(resultSet.next()) {
//				do {
//					e = loadNext(connection, resultSet);
//					results.add(e);               	
//					//currentCount++;                	
//				} while (/*(currentCount < count) && */ resultSet.next()) ;
//			}
//			//}
//
//			return results;
//	
//			} catch (SQLException e) {
//				//logger.error("Error",e);
//				throw new DataException(e);
//			} catch (DataException de) {
//				//logger.error("Error",e);
//				throw new DataException(de);
//			}  finally {
//				JDBCUtils.closeResultSet(resultSet);
//				JDBCUtils.closeStatement(preparedStatement);
//		}
//	}

	
	@Override
	public List<Video> findAllVideos(Connection connection) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO,"
							+ " V.DESCRIPCION, V.REPRODUCCIONES, V.URL_VIDEO  " + 
					" FROM VIDEO V INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = V.ID_CONTENIDO ) ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Video> results = new ArrayList<Video>(); 

			Video video = null;

			while (resultSet.next()) {
				video = loadNext(connection, resultSet);
				results.add(video);               	
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
			
			ContenidoDAO daoc = new ContenidoDAOImpl();
			
			Contenido c= daoc.create(connection, video);
			video.setIdContenido(c.getIdContenido());


			String queryString = "INSERT INTO Video (ID_CONTENIDO, USUARIO_ID_CONTENIDO, DESCRIPCION, reproducciones, url_video ) "
					+ "VALUES (?, ?, ?, ?, ? )";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;    
			preparedStatement.setLong(i++, video.getIdContenido());
			preparedStatement.setLong(i++, video.getIdAutor());
			preparedStatement.setString(i++, video.getDescripcion());
			preparedStatement.setInt(i++, video.getReproducciones());
			preparedStatement.setString(i++, video.getUrl());	
			
			System.out.println(preparedStatement.toString());
			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				logger.debug("No se ha podido insertar en tabla VIDEO");
				throw new SQLException("Can not add row to table 'Video'");
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
			
			queryString = new StringBuilder(
					" UPDATE Video " 
					);
			
			boolean first = true;

			if (video.getDescripcion()!=null) {
				addUpdate(queryString, first, " descripcion = ? ");
				first = false;
			}
			
			if (video.getReproducciones()!=null) {
				addUpdate(queryString, first, " reproducciones = ? ");
				first = false;
			}
			
			if (video.getUrl()!=null) {
				addUpdate(queryString, first, " url_video = ? ");
				first = false;
			}
			
			queryString.append("WHERE id_contenido = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			if (video.getDescripcion()!=null)
				preparedStatement.setString(i++,video.getDescripcion());
			
			if (video.getReproducciones()!=null)
				preparedStatement.setInt(i++,video.getReproducciones());
			
			if (video.getUrl()!=null)
				preparedStatement.setString(i++,video.getUrl());

			
			preparedStatement.setLong(i++, video.getIdContenido());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				logger.debug("Video con id= {} no encontrado.", video.getIdContenido() );
				throw new InstanceNotFoundException(video.getIdContenido(), Video.class.getName()); //Esto ultimo pa mostrar o nome da clase??
			}

			if (updatedRows > 1) {
				logger.debug("Video con id= {} duplicado.", video.getIdContenido() );
				throw new SQLException("Duplicate row for id = '" + 
						video.getIdContenido() + "' in table 'Video'");
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
			String queryString =	
					  "DELETE FROM video " 
					+ "WHERE id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				logger.debug("No se ha podido eliminar en al tabla VIDEO" );
				throw new SQLException("Can not delete row in table 'Video'");
			}
			
			ContenidoDAO daoc = new ContenidoDAOImpl();
			daoc.delete(connection, id);	
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}
	
	private Video loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				Long idContenido = resultSet.getLong(i++);
				String nombre = resultSet.getString(i++);
				Date fechaAlta =  resultSet.getDate(i++);
				Date fechaMod =  resultSet.getDate(i++);
				Long autor = resultSet.getLong(i++);	
				
				String descripcion = resultSet.getString(i++);
				Integer reproducciones = resultSet.getInt(i++);
				String url = resultSet.getString(i++);
			
				Video v = new Video();
				v.setIdContenido(idContenido);
				v.setNombre(nombre);
				v.setFechaAlta(fechaAlta);
				v.setFechaMod(fechaMod);
				v.setIdAutor(autor);

				v.setDescripcion(descripcion);
				v.setReproducciones(reproducciones);
				v.setUrl(url);

				return v;
			}


	@Override
	public Integer getReproducciones(Connection connection, Long idContenido) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String queryString = null;
		Integer valor = null;

		try {
			queryString =" SELECT REPRODUCCIONES FROM VIDEO WHERE ID_CONTENIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idContenido);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				if(resultSet.getObject(1) != null) {
					valor = resultSet.getInt(1);
				}
			} else {
				logger.debug("Reproducciones del video con id= {} no encontradas.",idContenido );
				throw new DataException("\nReproducciones del video con id " +idContenido+ " no encontradas \n");
			}

			return valor;
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}


	@Override
	public List<Video> findByAutor(Connection connection, Long idAutor) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO,"
							+ " V.DESCRIPCION, V.REPRODUCCIONES, V.URL_VIDEO  " + 
					" FROM VIDEO V INNER JOIN CONTEINDO C ON (C.ID_CONTENIDO = V.ID_CONTENIDO ) "+
					" WHERE C.AUTOR_ID_CONTENIDO = ? ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idAutor);

			resultSet = preparedStatement.executeQuery();

			List<Video> results = new ArrayList<Video>(); 

			Video video = null;

			while (resultSet.next()) {
				video = loadNext(connection, resultSet);
				results.add(video);               	
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
	public List<Video> findByCategoria(Connection connection, Long idCategoria) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO,"
							+ " V.DESCRIPCION, V.REPRODUCCIONES, V.URL_VIDEO  " + 
					" FROM VIDEO V INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = V.ID_CONTENIDO ) "+
					"  INNER JOIN CATEGORIA_CONTENIDO CC ON (CC.ID_CONTENIDO = V.ID_CONTENIDO ) "+
					" WHERE CC.ID_CATEGORIA = ? ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idCategoria);

			resultSet = preparedStatement.executeQuery();

			List<Video> results = new ArrayList<Video>(); 

			Video video = null;

			while (resultSet.next()) {
				video = loadNext(connection, resultSet);
				results.add(video);               	
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

}
