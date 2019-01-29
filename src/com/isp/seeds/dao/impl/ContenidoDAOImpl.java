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
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.criteria.ContenidoCriteria;

public class ContenidoDAOImpl implements ContenidoDAO {
	
	
	
	

	@Override
	public Contenido findById(Connection connection, Contenido contenido, Long id) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
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
					" SELECT c.ID_CONTENIDO, c.fecha_alta, c.fecha_mod, c.autor_id_contenido " + 
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

			/*			if (idioma!=null) 
				preparedStatement.setString(i++,idioma);
				*/
			
			resultSet = preparedStatement.executeQuery();
			
			
			List<Contenido> results = new ArrayList<Contenido>();    
			Usuario e = null;
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Contenido create (Connection connection, Contenido c) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {

			// Creamos el preparedstatement
			
			String queryString = "INSERT INTO contenido (nombre, fecha_alta, fecha_mod, autor_id_contenido) "
					+ "VALUES (?, ?, ?, ?)";

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
			
			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Usuario'");
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
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	@Override
	public Contenido update(Connection connection, Contenido c) throws DataException {
		// TODO Auto-generated method stub
		return c;
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
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	private Contenido loadNext(Connection connection, ResultSet resultSet, Contenido c)
			throws SQLException, DataException {

				int i = 1;
				Long idContenido = resultSet.getLong(i++);
				Date fechaAlta =  resultSet.getDate(i++);
				Date fechaMod =  resultSet.getDate(i++);
				Long autor = resultSet.getLong(i++);	
			
				c.setIdContenido(idContenido);
				c.setFechaAlta(fechaAlta);
				c.setFechaMod(fechaMod);
				c.setIdAutor(autor);

				return c;
			}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}

}