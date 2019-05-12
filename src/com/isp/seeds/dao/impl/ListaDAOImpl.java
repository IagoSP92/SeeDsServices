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
import com.isp.seeds.dao.spi.ListaDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.dao.utils.Utils;
import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.exceptions.InstanceNotFoundException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.util.Results;

public class ListaDAOImpl extends ContenidoDAOImpl implements ListaDAO {
	
	private static Logger logger = LogManager.getLogger(ListaDAOImpl.class);

	private ContenidoDAO daoc = null;
	
	public ListaDAOImpl() {	
		daoc = new ContenidoDAOImpl();
	}
	

	@Override
	public Lista findById(Connection connection, Long idSesion, Long idLista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion={} IdLista= {}", idSesion, idLista);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			StringBuilder queryString = new StringBuilder(
					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+", L.DESCRIPCION, L.PUBLICA "
					+", UC.SIGUIENDO, UC.DENUNCIADO, UC.GUARDADO, UC.VALORACION, UC.COMENTARIO "
					+" FROM LISTA L INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = L.ID_CONTENIDO ) "
					+" LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO=UC.CONTENIDO_ID_CONTENIDO) ");
					if(idSesion!=null) { queryString.append(" AND (UC.USUARIO_ID_CONTENIDO= ? ) ");}
					queryString.append(" WHERE L.ID_CONTENIDO = ? ");
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if(idSesion!=null) {
				preparedStatement.setLong(i++, idSesion);
			}	
			preparedStatement.setLong(i++, idLista);
 
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			Lista lista = null;

			if (resultSet.next()) {
				lista = loadNext(connection, resultSet);				
			} else {
				throw new DataException("Lista with id " +idLista+ "not found");
			}			

			return lista;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}



	@Override
	public Lista create(Connection connection, Lista lista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {}", lista);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			lista = (Lista) daoc.create(connection, lista);

			String queryString = "INSERT INTO Lista (ID_CONTENIDO, USUARIO_ID_CONTENIDO, DESCRIPCION, PUBLICA ) "
					+ "VALUES (?, ?, ?, ? )";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);

			int i = 1;    
			preparedStatement.setLong(i++, lista.getId());
			preparedStatement.setLong(i++, lista.getAutor());
			preparedStatement.setString(i++, lista.getDescripcion());
			preparedStatement.setBoolean(i++, lista.getPublica());

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'LISTA'");
			}
			return lista;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Lista lista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}
		
		daoc.update(connection, lista);
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {
			
			queryString = new StringBuilder(" UPDATE Lista ");
			
			boolean first = true;

			if (lista.getDescripcion()!=null) {
				addUpdate(queryString, first, " descripcion = ? ");
				first = false;
			}
			
			if (lista.getPublica()!=null) {
				addUpdate(queryString, first, " publica = ? ");
				first = false;
			}
			
			queryString.append("WHERE id_contenido = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			if (lista.getDescripcion()!=null)
				preparedStatement.setString(i++,lista.getDescripcion());
			
			if (lista.getPublica()!=null)
				preparedStatement.setBoolean(i++,lista.getPublica());
			
			preparedStatement.setLong(i++, lista.getId());

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(lista.getId(), Lista.class.getName()); //Esto ultimo pa mostrar o nome da clase??
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						lista.getId() + "' in table 'LISTA'");
			}			
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}    
		
	}

	@Override
	public void delete(Connection connection, Long idLista) throws DataException {
		PreparedStatement preparedStatement = null;
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} ", idLista);
		}
		try {
			String queryString =	
					  "DELETE FROM Lista WHERE id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idLista);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new SQLException("Can not delete row in table 'Lista'");
			}
			
			daoc.delete(connection, idLista);			

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
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
					+" INNER JOIN LISTA L ON (L.ID_CONTENIDO=C.ID_CONTENIDO)"
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
	
	@Override
	public Results<Contenido> cargarSeguidos(Connection connection, Long idSesion, int startIndex, int count)
			throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+" FROM CONTENIDO C INNER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO) "
							+" AND (UC.USUARIO_ID_CONTENIDO= ? ) "
					+" INNER JOIN LISTA L ON (L.ID_CONTENIDO=C.ID_CONTENIDO)"
					+" WHERE UC.USUARIO_ID_CONTENIDO = ? AND UC.SIGUIENDO= TRUE "
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


	@Override
	public void redefinirIncluidos(Connection connection, Long idLista, List<Long> listIdVideo) throws DataException {
		
		borrarIncluidos(connection, idLista);
		
		Integer ultima = getUltimaPosicion(connection, idLista);
		ultima++;
		for (Long idVideo:listIdVideo) {
			insertarEnPosicion(connection, idLista, idVideo, ultima++);
		}
		
	}
	
	private int getUltimaPosicion (Connection connection, Long idLista) 
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} ", idLista);
		}		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = "SELECT COUNT(*) FROM VIDEO_LISTA " +
								" WHERE LISTA_ID_CONTENIDO = ? AND POSICION > 0 ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idLista);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();
			
			int ultima =0;
			if (resultSet.next()) {
				if ( resultSet.getInt(1) > ultima ) { ultima =  resultSet.getInt(1); }
			}
			return ultima;
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}

	
	private void insertarEnPosicion(Connection connection, Long idLista, Long idVideo, int posicion)
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} idVideo= {} Posicion={}", idLista, idVideo, posicion);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String queryString = "INSERT INTO VIDEO_LISTA (LISTA_ID_CONTENIDO, VIDEO_ID_CONTENIDO, POSICION ) "
					+ "VALUES (?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1; 
			preparedStatement.setLong(i++, idLista);
			preparedStatement.setLong(i++, idVideo);
			preparedStatement.setInt(i++, posicion);
			
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'VIDEO_LISTA'");
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	private void borrarIncluidos (Connection connection, Long idLista) throws DataException {
		PreparedStatement preparedStatement = null;
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Borrar Incluidos en Lista -> idLista={} ", idLista);
		}
		try {
			String queryString =	
					  "DELETE FROM VIDEO_LISTA WHERE LISTA_ID_CONTENIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);
			int i = 1;
			preparedStatement.setLong(i++, idLista);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}
	
	
	
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}
	
	protected static Lista loadNext(Connection connection, ResultSet resultSet)
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
				Boolean publica = resultSet.getBoolean(i++);
			
				Lista l = new Lista();
				l.setId(idContenido);
				l.setNombre(nombre);
				l.setFechaAlta(fechaAlta);
				l.setFechaMod(fechaMod);
				l.setAutor(autor);
				l.setTipo(tipo);
				l.setReproducciones(reproducciones);
				l.setValoracion(valoracion);

				l.setDescripcion(descripcion);
				l.setPublica(publica);
				
				if(i<resultSet.getMetaData().getColumnCount()) {
					Boolean siguiendo = resultSet.getBoolean(i++);
					Boolean denunciado = resultSet.getBoolean(i++);
					Boolean guardado = resultSet.getBoolean(i++);
					Double valorado = resultSet.getDouble(i++);
					String comentado = resultSet.getString(i++);					
					l.setDenunciado(denunciado);
					l.setGuardado(guardado);	
					l.setValorado(valorado);
					l.setComentado(comentado);
					l.setSiguiendo(siguiendo);
					l.setComentarios(Utils.cargarComentarios(connection, idContenido));
				}
				return l;
			}
}