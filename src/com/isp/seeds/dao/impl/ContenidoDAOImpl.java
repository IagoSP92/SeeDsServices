package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.Exceptions.InstanceNotFoundException;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;

public class ContenidoDAOImpl implements ContenidoDAO {
	
	ContenidoDAO contenidoDao = new ContenidoDAOImpl();

	@Override
	public Contenido findById(Connection connection, Contenido contenido, Long id) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.fecha_alta, c.fecha_mod, c.autor_id_contenido, tipo"
							+ " u.email, u.contrasena, u.descripcion, u.url_avatar, u.nombre_real, u.apellidos, u.id_pais " + 
							"FROM Usuario u INNER JOIN Contenido c ON (c.id_contenido = u.id_contenido ) " +
							"WHERE u.id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);
 
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				contenido = loadNext(connection, resultSet, contenido);				
			} else {
				throw new DataException("\nContenido with id " +id+ "not found\n");
			}
			return contenido;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}


	@Override
	public List<Contenido> findByCriteria (Connection connection, ContenidoCriteria contenido) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT c.ID_CONTENIDO, c.fecha_alta, c.fecha_mod, c.autor_id_contenido, tipo " + 
					" FROM Contenido c " );
			
			boolean first = true;
			
			if (contenido.getIdContenido()!=null) {
				addClause(queryString, first, " u.ID_CONTENIDO LIKE ? ");
				first = false;
			}
			
			if (contenido.getFechaAlta()!=null) {
				addClause(queryString, first, " u.EMAIL LIKE ? ");
				first = false;
			}

			if (contenido.getFechaMod()!=null) {
				addClause(queryString, first, " u.URL_AVATAR LIKE ? ");
				first = false;
			}			
			
			if (contenido.getIdAutor()!=null) {
				addClause(queryString, first, " u.NOMBRE_REAL LIKE ? ");
				first = false;
			}
			
			if (contenido.getTipo()!=null) {
				addClause(queryString, first, " u.tipo LIKE ? ");
				first = false;
			}	
			
			/*if (idioma!=null) {
				addClause(queryString, first, " pi.id_idioma LIKE ? ");
				first = false;
			}*/

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;
			if (contenido.getIdContenido()!=null)
				preparedStatement.setString(i++, "%" + contenido.getIdContenido() + "%");
			if (contenido.getFechaAlta()!=null) 
				preparedStatement.setString(i++, "%" + contenido.getFechaAlta() + "%");
			if (contenido.getFechaMod()!=null)
				preparedStatement.setString(i++, "%" + contenido.getFechaMod() + "%");
			if (contenido.getIdAutor()!=null) 
				preparedStatement.setString(i++, "%" + contenido.getIdAutor() + "%");
			if (contenido.getTipo()!=null) 
				preparedStatement.setString(i++, "%" + contenido.getTipo() + "%");

			/*			if (idioma!=null) 
				preparedStatement.setString(i++,idioma);
				*/
			
			resultSet = preparedStatement.executeQuery();
			
			
			List<Contenido> results = new ArrayList<Contenido>();    
			Contenido e = null;
			//int currentCount = 0;

			//if ((startIndex >=1) && resultSet.absolute(startIndex)) {
			if(resultSet.next()) {
				do {
					e = loadNext(connection, resultSet, e);
					results.add(e);               	
					//currentCount++;                	
				} while (/*(currentCount < count) && */ resultSet.next()) ;
			}
			//}

			return results;
	
			} catch (SQLException e) {
				e.printStackTrace();
				//logger.error("Error",e);
				throw new DataException(e);
			} catch (DataException de) {
				//logger.error("Error",e);
				throw new DataException(de);
			}  finally {
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
		}
		
	}
	

	@Override
	public List<Contenido> findAll(Connection connection) throws DataException {
		ContenidoDAO dao= new ContenidoDAOImpl();
		ContenidoCriteria contenido= new ContenidoCriteria();
		return dao.findByCriteria(connection, contenido);
	}


	@Override
	public Contenido create (Connection connection, Contenido c) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Creamos el preparedstatement
			String queryString = "INSERT INTO contenido (nombre, fecha_alta, fecha_mod, autor_id_contenido, tipo) "
					+ "VALUES (?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);

			// Rellenamos el "preparedStatement"
			int i = 1;
			preparedStatement.setString(i++, c.getNombre());
			preparedStatement.setDate(i++, new java.sql.Date(c.getFechaAlta().getTime()));
			preparedStatement.setDate(i++, new java.sql.Date(c.getFechaMod().getTime()));
			if(c.getIdAutor() == null) { // INSERTAR USUARIOS: AUTOR=NULL
				preparedStatement.setNull(i++, Types.NULL);
			}
			else {  // INSERTAR VIDEOS Y LISTAS
				preparedStatement.setLong(i++, c.getIdAutor());
			}
			preparedStatement.setInt(i++, c.getTipo());

			
			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Contenido'");
			}

			// Recuperamos la PK generada
			
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				c.setIdContenido(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}
			// Return the DTO
			return c;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	@Override
	public void update(Connection connection, Contenido contenido) throws DataException {
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE Contenido " 
					);
			
			boolean first = true;
			
			if (contenido.getNombre()!=null) {
				addUpdate(queryString, first, " nombre = ? ");
				first = false;
			}
			
			if (contenido.getFechaAlta()!=null) {
				addUpdate(queryString, first, " fecha_alta = ? ");
				first = false;
			}
			
			if (contenido.getFechaMod()!=null) {
				addUpdate(queryString, first, " contrasena = ? ");
				first = false;
			}
			
			if (contenido.getIdAutor()!=null) {
				addUpdate(queryString, first, " autor_id_contenido = ? ");
				first = false;
			}
			
			if (contenido.getTipo()!=null) {
				addUpdate(queryString, first, " tipo = ? ");
				first = false;
			}

			queryString.append("WHERE id_contenido = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			if (contenido.getNombre()!=null)
				preparedStatement.setString(i++,contenido.getNombre());
			
			if (contenido.getFechaAlta()!=null)
				preparedStatement.setDate(i++,(Date) contenido.getFechaAlta());
			
			if (contenido.getFechaMod()!=null)
				preparedStatement.setDate(i++,(Date) contenido.getFechaMod());
			
			if (contenido.getIdAutor()!=null)
				preparedStatement.setLong(i++,contenido.getIdAutor());
			
			if (contenido.getTipo()!=null)
				preparedStatement.setLong(i++,contenido.getTipo());

			preparedStatement.setLong(i++, contenido.getIdContenido());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(contenido.getIdContenido(), Contenido.class.getName()); //Esto ultimo pa mostrar o nome da clase??
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						contenido.getIdContenido() + "' in table 'Contenido'");
			}     
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}    
	}



	@Override
	public long delete(Connection connection, Long id) throws DataException {

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					  "DELETE FROM CONTENIDO " 
					+ "WHERE id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new DataException("Exception: No removed rows");
			} 
			return removedRows;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	


	@Override
	public void agignarCategoria(Connection connection, Long idContenido, Long idCategoria) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			String queryString = " INSERT INTO categoria_contenido (id_categoria, id_contenido) "
					+ " VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idCategoria);
			preparedStatement.setLong(i++, idContenido);

			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("No se ha podido asignar la categoría");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public void modificarCategoria(Connection connection, Long idContenido, Long idCategoria) throws DataException {
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE etiqueta_Contenido " 
					);
			
			boolean first = true;
			
			if (idContenido != null && idCategoria != null ) {
				addUpdate(queryString, first, " id_categoria = ? ");
				first = false;
				addUpdate(queryString, first, " id_contenido = ? ");
			}

			queryString.append("WHERE id_categoria = ? and id_contenido= ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			if (idContenido != null && idCategoria != null ) {
				preparedStatement.setLong(i++, idCategoria );
				preparedStatement.setLong(i++, idContenido );
			}
			
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la categoría");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idContenido +" - "+idCategoria+ "' in table 'Categoria_Contenido'");
			}     
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public void asignarEtiqueta(Connection connection, Long idContenido, Long idEtiqueta) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			String queryString = " INSERT INTO etiqueta_contenido (idEtiqueta, id_contenido) "
					+ " VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idEtiqueta);
			preparedStatement.setLong(i++, idContenido);

			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("No se ha podido asignar la etiqueta");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public void eliminarEtiqueta(Connection connection, Long idContenido, Long idEtiqueta) throws DataException {

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					  "DELETE FROM ETIQUETA_CONTENIDO " 
					+ "WHERE id_contenido = ? and id_etiqueta = ?";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idContenido);
			preparedStatement.setLong(i++, idEtiqueta);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new DataException("Exception: No se ha eliminado la etiqueta");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	
	private Contenido loadNext(Connection connection, ResultSet resultSet, Contenido c)
			throws SQLException, DataException {

				int i = 1;
				Long idContenido = resultSet.getLong(i++);
				String nombre = resultSet.getString(i++);
				Date fechaAlta =  resultSet.getDate(i++);
				Date fechaMod =  resultSet.getDate(i++);
				Long autor = resultSet.getLong(i++);
				Integer tipo = resultSet.getInt(i++);
			
				c.setIdContenido(idContenido);
				c.setNombre(nombre);
				c.setFechaAlta(fechaAlta);
				c.setFechaMod(fechaMod);
				c.setIdAutor(autor);
				c.setTipo(tipo);

				return c;
			}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}


	@Override
	public Boolean comprobarExistenciaRelacion(Connection connection, Long idUsuario, Long idContenido)
			throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT USUARIO_ID_CONTENIDO, CONTENIDO_ID_CONTENIDO " +
							"FROM USUARIO_CONTENIDO " +
							"WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idUsuario);
			preparedStatement.setLong(i++, idContenido);
 
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;			
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public void crearRelacion(Connection connection, Long idUsuario, Long idContenido) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			String queryString = " INSERT INTO USUARIO_CONTENIDO " +
			" (USUARIO_ID_CONTENIDO, CONTENIDO_ID_CONTENIDO, SIGUIENDO, DENUNCIADO, VALORACION, GUARDADO, COMENTARIO) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idUsuario);
			preparedStatement.setLong(i++, idContenido);
			
			preparedStatement.setBoolean(i++, false);
			preparedStatement.setNull(i++, Types.NULL);
			preparedStatement.setInt(i++, 0);
			preparedStatement.setBoolean(i++, false);
			preparedStatement.setNull(i++, Types.NULL);

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("No se ha podido crear la relacion Usuario_Contenido");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public void seguirContenido(Connection connection, Long idUsuario, Long idContenido, Boolean siguiendo) throws DataException {
		
		if(!contenidoDao.comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			contenidoDao.crearRelacion(connection, idUsuario, idContenido);
		}
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);
			
			boolean first = true;
			addUpdate(queryString, first, " SIGUIENDO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			preparedStatement.setBoolean(i++, siguiendo );
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (seguir)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (seguir)'");
			}     
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
		
	}


	@Override
	public void denunciarContenido(Connection connection, Long idUsuario, Long idContenido, String denunciado)
			throws DataException {
		
		if(!contenidoDao.comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			contenidoDao.crearRelacion(connection, idUsuario, idContenido);
		}
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);
			
			boolean first = true;
			addUpdate(queryString, first, " DENUNCIADO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			if(denunciado == null) {
				preparedStatement.setNull(i++, Types.NULL);
			} else {
				preparedStatement.setString(i++, denunciado );
			}
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (denunciar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (denunciar)'");
			}     
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
		
	}


	@Override
	public void valorarContenido(Connection connection, Long idUsuario, Long idContenido, int valoracion)
			throws DataException {
		
		if(!contenidoDao.comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			contenidoDao.crearRelacion(connection, idUsuario, idContenido);
		}
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);
			
			boolean first = true;
			addUpdate(queryString, first, " VALORACION = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			preparedStatement.setInt(i++, valoracion );
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (valorar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (valorar)' ");
			}     
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public void guardarContenido(Connection connection, Long idUsuario, Long idContenido, Boolean guardado)
			throws DataException {
		
		if(!contenidoDao.comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			contenidoDao.crearRelacion(connection, idUsuario, idContenido);
		}
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);
			
			boolean first = true;
			addUpdate(queryString, first, " GUARDADO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			preparedStatement.setBoolean(i++, guardado );
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO  (guardar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (guardar)'");
			}     
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
		
		
	}


	@Override
	public void comentarContenido(Connection connection, Long idUsuario, Long idContenido, String comentario)
			throws DataException {
		
		if(!contenidoDao.comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			contenidoDao.crearRelacion(connection, idUsuario, idContenido);
		}
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);
			
			boolean first = true;
			addUpdate(queryString, first, " COMENTARIO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			if(comentario == null) {
				preparedStatement.setNull(i++, Types.NULL);
			} else {
				preparedStatement.setString(i++, comentario );
			}
			
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (comentar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (comentar)'");
			}     
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
				
	}




}