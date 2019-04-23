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
import com.isp.seeds.dao.spi.ListaDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Lista;
import com.isp.seeds.model.Video;

public class ListaDAOImpl extends ContenidoDAOImpl implements ListaDAO {
	
	private static Logger logger = LogManager.getLogger(ListaDAOImpl.class);


	@Override
	public Lista findById(Connection connection, Long idSesion, Long idLista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion={} IdLista= {}", idSesion, idLista);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+", L.DESCRIPCION, L.PUBLICA "
					+", UC.SIGUIENDO, UC.DENUNCIADO, UC.GUARDADO "
					+" FROM LISTA L INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = L.ID_CONTENIDO ) "
					+" INNER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO=UC.CONTENIDO_ID_CONTENIDO) "
													+" AND (UC.USUARIO_ID_CONTENIDO= ? ) "
					+" WHERE L.ID_CONTENIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idSesion);
			preparedStatement.setLong(i++, idLista);
 
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			Lista e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new DataException("\n Lista with id " +idLista+ "not found\n");
			}
			

			return e;

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
			
			ContenidoDAO daoc = new ContenidoDAOImpl();
			lista = (Lista) daoc.create(connection, lista);

			String queryString = "INSERT INTO Lista (ID_CONTENIDO, USUARIO_ID_CONTENIDO, DESCRIPCION, publica ) "
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
				throw new SQLException("Can not add row to table 'Lista'");
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
						lista.getId() + "' in table 'Lista'");
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
					  "DELETE FROM Lista " 
					+ "WHERE id_contenido = ? ";
			
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
			
			ContenidoDAO daoc = new ContenidoDAOImpl();
			daoc.delete(connection, idLista);			

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}




	
	@Override
	public void insertInList (Connection connection, Long idLista, Long idVideo, int posicion)
			throws DataException, SQLException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} idVideo= {} Posicion={}", idLista, idVideo, posicion);
		}
		
		if (!esEnLista(connection, idLista, idVideo)) {
			
			int ultima = ultimaPosicion(connection, idLista);
			
			if(posicion > ultima) {
				insertarEnPosicion(connection, idLista, idVideo, ultima+1);
			}
			else {
				sumarPosicion(connection, idLista, posicion);
				insertarEnPosicion(connection, idLista, idVideo, posicion);
			}
		}
	}
	
	@Override
	public void deleteFromList(Connection connection, Long idLista, Long idVideo)
			throws DataException, SQLException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} idVideo= {} ", idLista, idVideo);
		}
		
		if (esEnLista(connection, idLista, idVideo)) {
			int deleteHere = getPosicion(connection, idLista, idVideo);
			int ultima = ultimaPosicion(connection, idLista);
			if(deleteHere <= ultima) { //1. ELIMINAR. 2. MODIFICAR POSICIONES POSTERIORES
				deleteVideoFromList(connection, idLista, idVideo);
				restarPosicion(connection, idLista, deleteHere+1);
			}

		}
		
	}
	
	public Integer getPosicion (Connection connection, Long idLista, Long idVideo)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("IdLista= {} IdVideo= {}", idLista, idVideo);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer valor = null;
		String queryString = null;
		try {
			queryString = " SELECT POSICION FROM VIDEO_LISTA WHERE LISTA_ID_CONTENIDO = ? AND VIDEO_ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idLista);
			preparedStatement.setLong(i++, idVideo);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				if(resultSet.getObject(1) != null) {
					valor = resultSet.getInt(1);
				}
			} else {
				logger.debug("Video con id= {} no encontrado en Lista con id= {}.", idVideo, idLista );
				throw new DataException("\nPosicion de Video="+idVideo+" en Lista="+idLista+" no encontrada \n");
			}
			return valor;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		}  finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		
	}
	
	
	private Boolean esUltimaPosicion (Connection connection, Long idLista, int posicion  )  // SUBSTITUIBLE POR ULTIMAPOSICION
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} Posicion={}", idLista, posicion);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = "SELECT COUNT(*) FROM VIDEO_LISTA " +
								" WHERE LISTA_ID_CONTENIDO = ? AND POSICION >= ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idLista);
			preparedStatement.setInt(i++, posicion);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				if ( resultSet.getInt(1) > 0 ) { return false; }
			}
			return true;
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		
	}
	
	private int ultimaPosicion (Connection connection, Long idLista  ) 
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
	
	private void sumarPosicion (Connection connection, Long idLista, int posicion  ) 
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} Posicion={}", idLista, posicion);
		}
		
		int ultima = ultimaPosicion(connection, idLista);
		int actual= posicion;
		int nueva = actual++;
		for (int j=posicion; j <= ultima+1; j++) {
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;			
			
			try {
				String queryString = "UPDATE VIDEO_LISTA SET POSICION = ? " +
									" WHERE LISTA_ID_CONTENIDO = ? AND POSICION = ? ";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;
				preparedStatement.setInt(i++, nueva);
				preparedStatement.setLong(i++, idLista);
				preparedStatement.setInt(i++, actual);

				//resultSet = preparedStatement.executeQuery();
				
				if(logger.isDebugEnabled()) {
					logger.debug("QUERY= {}",preparedStatement);
				}
				
				int updatedRows = preparedStatement.executeUpdate();

				if (updatedRows == 0) {
					throw new InstanceNotFoundException (idLista, Lista.class.getName() );
				}

				if (updatedRows > 1) {
					throw new SQLException("Duplicate position in list with id = '" + 
							idLista + "' in table 'Video_Lista'");
				}
				nueva++;
				actual++;
				
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
			}
		}
	}
	
	
	private void restarPosicion (Connection connection, Long idLista, int posicion  ) 
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} Posicion={}", idLista, posicion);
		}
		
		int ultima = ultimaPosicion(connection, idLista);
		int actual= posicion;
		int nueva = actual--;
		for (int j=posicion; j <= ultima; j++) {
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;			
			
			try {
				String queryString = "UPDATE VIDEO_LISTA SET POSICION = ? " +
									" WHERE LISTA_ID_CONTENIDO = ? AND POSICION = ? ";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;
				preparedStatement.setInt(i++, nueva);
				preparedStatement.setLong(i++, idLista);
				preparedStatement.setInt(i++, actual);

				//resultSet = preparedStatement.executeQuery();
				
				if(logger.isDebugEnabled()) {
					logger.debug("QUERY= {}",preparedStatement);
				}
				
				int updatedRows = preparedStatement.executeUpdate();

				if (updatedRows == 0) {
					throw new InstanceNotFoundException (idLista, Lista.class.getName() );
				}

				if (updatedRows > 1) {
					throw new SQLException("Duplicate position in list with id = '" + 
							idLista + "' in table 'Video_Lista'");
				}
				nueva++;
				actual++;
				
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
			}
		}
	}
	
	
	private Boolean esEnLista (Connection connection, Long idLista, Long idVideo  )  // SUBSTITUIBLE POR ULTIMAPOSICION
			throws DataException, SQLException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} idVideo= {} ", idLista, idVideo);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = "SELECT COUNT(*) FROM VIDEO_LISTA " +
								" WHERE LISTA_ID_CONTENIDO = ? AND VIDEO_ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idLista);
			preparedStatement.setLong(i++, idVideo);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				if ( resultSet.getInt(1) < 1 ) { return false; }
				else {
					if ( resultSet.getInt(1) > 1 ) { 
						throw new SQLException("Video con id: "+idVideo+" duplicado en lista con id: "+idLista);
					}
				}
			}
			return true;
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new SQLException(e);
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
				throw new SQLException("Can not add row to table 'Video_Lista'");
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	private void deleteVideoFromList (Connection connection, Long idLista, Long idVideo)
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} idVideo= {} ", idLista, idVideo);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String queryString = "DELETE FROM VIDEO_LISTA WHERE LISTA_ID_CONTENIDO = ? AND VIDEO_ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1; 
			preparedStatement.setLong(i++, idLista);
			preparedStatement.setLong(i++, idVideo);
			
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new DataException("No removed row (VIDEO_LISTA) idVideo="+idVideo+" idLista="+idLista);
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}
	
	private Lista loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				Long idContenido = resultSet.getLong(i++);
				String nombre = resultSet.getString(i++);
				Date fechaAlta =  resultSet.getDate(i++);
				Date fechaMod =  resultSet.getDate(i++);
				Long autor = resultSet.getLong(i++);	
				Integer tipo = resultSet.getInt(i++);	
				
				String descripcion = resultSet.getString(i++);
				Boolean publica = resultSet.getBoolean(i++);
				
				Boolean siguiendo = resultSet.getBoolean(i++);
				Boolean denunciado = resultSet.getBoolean(i++);
				Boolean guardado = resultSet.getBoolean(i++);
			
				Lista l = new Lista();
				l.setId(idContenido);
				l.setNombre(nombre);
				l.setFechaAlta(fechaAlta);
				l.setFechaMod(fechaMod);
				l.setAutor(autor);
				l.setTipo(tipo);

				l.setDescripcion(descripcion);
				l.setPublica(publica);
				
				l.setSiguiendo(siguiendo);
				l.setDenunciado(denunciado);
				l.setGuardado(guardado);

				return l;
			}

	
//	@Override
//	public List<Lista> findByCriteria(Connection connection, ListaCriteria lista) throws DataException {
//
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		StringBuilder queryString = null;
//
//		try {	
//			queryString = new StringBuilder(
//					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
//							+ " l.descripcion, l.publica " + 
//							" FROM Lista l INNER JOIN Contenido c ON (c.id_contenido = l.id_contenido ) ");
//			
//			boolean first = true;
//
//			if (lista.getIdContenido()!=null) {
//				addClause(queryString, first, " l.ID_CONTENIDO LIKE ? ");
//				first = false;
//			}
//			
//			if (lista.getDescripcion()!=null) {
//				addClause(queryString, first, " l.descripcion LIKE ? ");
//				first = false;
//			}
//
//			if (lista.getPublica()!=null) {
//				addClause(queryString, first, " l.publica LIKE ? ");
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
//			if (lista.getIdContenido()!=null)
//				preparedStatement.setString(i++, "%" + lista.getIdContenido() + "%");
//			if (lista.getDescripcion()!=null) 
//				preparedStatement.setString(i++, "%" + lista.getDescripcion() + "%");
//			if (lista.getPublica()!=null)
//				preparedStatement.setString(i++, "%" + lista.getPublica() + "%");
//			/*
//			if (idioma!=null) 
//				preparedStatement.setString(i++,idioma);
//				*/			
//			resultSet = preparedStatement.executeQuery();
//
//			List<Lista> results = new ArrayList<Lista>(); 
//
//			Lista e = null;
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

//	@Override
//	public List<Lista> findAllListas(Connection connection) throws DataException {
//		
//		//logger
//
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		StringBuilder queryString = null;
//
//		try {
//			queryString = new StringBuilder(
//					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, "
//							+ " L.DESCRIPCION, L.PUBLICA " + 
//					" FROM LISTA L INNER JOIN CONTENiDO C ON (C.ID_CONTENIDO = L.ID_CONTENIDO ) ");
//
//			preparedStatement = connection.prepareStatement(queryString.toString(),
//					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//
//			if(logger.isDebugEnabled()) {
//				logger.debug("QUERY= {}",preparedStatement);
//			}			
//			resultSet = preparedStatement.executeQuery();
//
//			List<Lista> results = new ArrayList<Lista>(); 
//
//			Lista lista = null;
//
//			while (resultSet.next()) {
//				lista = loadNext(connection, resultSet);
//				results.add(lista);               	
//			} 
//			return results;
//
//		} catch (SQLException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		} catch (DataException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		}  finally {
//			JDBCUtils.closeResultSet(resultSet);
//			JDBCUtils.closeStatement(preparedStatement);
//		}
//	}
	
	
//	@Override
//	public List<Lista> findByAutor(Connection connection, Long idAutor) throws DataException {
//		
//		if(logger.isDebugEnabled()) {
//			logger.debug ("idAutor= {} ", idAutor);
//		}
//
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		StringBuilder queryString = null;
//
//		try {
//			queryString = new StringBuilder(
//					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, "
//							+ " V.DESCRIPCION, V.REPRODUCCIONES, V.URL_VIDEO  " + 
//					" FROM VIDEO V INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = V.ID_CONTENIDO ) "+
//					" WHERE C.AUTOR_ID_CONTENIDO = ? ");
//
//			preparedStatement = connection.prepareStatement(queryString.toString(),
//					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			
//			preparedStatement.setLong(1, idAutor);
//
//			if(logger.isDebugEnabled()) {
//				logger.debug("QUERY= {}",preparedStatement);
//			}			
//			resultSet = preparedStatement.executeQuery();
//
//			List<Lista> results = new ArrayList<Lista>(); 
//
//			Lista lista = null;
//
//			while (resultSet.next()) {
//				lista = loadNext(connection, resultSet);
//				results.add(lista);               	
//			} 
//			return results;
//
//		} catch (SQLException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		} catch (DataException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		}  finally {
//			JDBCUtils.closeResultSet(resultSet);
//			JDBCUtils.closeStatement(preparedStatement);
//		}
//	}
//
//	@Override
//	public List<Lista> findByCategoria(Connection connection, Long idCategoria) throws DataException {
//		
//		if(logger.isDebugEnabled()) {
//			logger.debug ("idCategoria= {} ", idCategoria);
//		}
//
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		StringBuilder queryString = null;
//
//		try {
//			queryString = new StringBuilder(
//					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, "
//							+ " L.DESCRIPCION, L.PUBLICA  " + 
//					" FROM LISTA L INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = L.ID_CONTENIDO ) "+
//					"  INNER JOIN CATEGORIA_CONTENIDO CC ON (CC.ID_CONTENIDO = L.ID_CONTENIDO ) "+
//					" WHERE CC.ID_CATEGORIA = ? ");
//
//			preparedStatement = connection.prepareStatement(queryString.toString(),
//					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			
//			preparedStatement.setLong(1, idCategoria);
//
//			if(logger.isDebugEnabled()) {
//				logger.debug("QUERY= {}",preparedStatement);
//			}			
//			resultSet = preparedStatement.executeQuery();
//
//			List<Lista> results = new ArrayList<Lista>(); 
//
//			Lista lista = null;
//
//			while (resultSet.next()) {
//				lista = loadNext(connection, resultSet);
//				results.add(lista);               	
//			} 
//			return results;
//
//		} catch (SQLException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		} catch (DataException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		}  finally {
//			JDBCUtils.closeResultSet(resultSet);
//			JDBCUtils.closeStatement(preparedStatement);
//		}
//	}
	

}
