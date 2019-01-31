package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {
	
	private Categoria loadNext (ResultSet resultSet)
			throws SQLException {

		Categoria c = new Categoria();
		int i=1;
		
		Long id = resultSet.getLong(i++);
		String nombre = resultSet.getString(i++);

		c = new Categoria();

		c.setNombreCategoria(nombre);
		c.setIdCategoria(id);

		return c;		
	}


	public Categoria findById(Connection connection, Long id, String idioma)
			throws DataException {

		Categoria c = null;

		//Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//connection = ConnectionManager.getConnection();

			String sql;
			sql =  "SELECT c.ID_CATEGORIA, ci.NOMBRE_CATEGORIA "
					+"FROM CATEGORIA C inner join categoria_idioma ci on (c.ID_CATEGORIA = ci.id_categoria)"
					+"WHERE c.ID_CATEGORIA = ? AND CI.ID_IDIOMA = ?";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setLong(i++, id);
			preparedStatement.setString(i++, idioma);


			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			if (resultSet.next()) {
				c  = loadNext(resultSet);
				System.out.println("Cargado "+ c);

			} else {
				throw new DataException("Non se encontrou a categoria "+id);
			}
			if (resultSet.next()) {
				throw new DataException("Categoria "+id+" duplicado");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
			//JDBCUtils.closeConnection(connection);
		}  	

		return c;
	}


	@Override
	public List<Categoria> findAll(Connection connection) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT c.id_Categoria, ci.nombre_Categoria " + 
					"FROM Categoria c inner join categoria_idioma ci on (c.ID_CATEGORIA = ci.id_categoria) " +
					"ORDER BY ci.id_categoria, ci.nombre_Categoria asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// Execute query.
			System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			
			// Recupera la pagina de resultados
			List<Categoria> results = new ArrayList<Categoria>();                        
			Categoria categoria = null;
			int currentCount = 0;
						
			if (resultSet.next()) {
				do {
					categoria = loadNext(resultSet);
					results.add(categoria);  
					currentCount++;                	
				} while (resultSet.next()) ;
			}
			
			return results;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public Long findByNombre(Connection connection, String nombreCategoria, String idioma) throws DataException {

		Long idCategoria = null;

		//Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//connection = ConnectionManager.getConnection();

			String sql;
			sql =  "SELECT ID_CATEGORIA "
					+" FROM CATEGORIA_IDIOMA"
					+" WHERE NOMBRE_CATEGORIA = ? AND ID_IDIOMA = ? ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// Establece os parámetros
			int i = 1;
			preparedStatement.setString(i++, nombreCategoria);
			preparedStatement.setString(i++, idioma);

			resultSet = preparedStatement.executeQuery();			

			if (resultSet.next()) {
				int j=1;
				idCategoria  = resultSet.getLong(j++);

			} else {
				throw new DataException("Non se encontrou a categoria "+nombreCategoria);
			}
			if (resultSet.next()) {
				throw new DataException("Categoria "+nombreCategoria+" duplicado");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
			//JDBCUtils.closeConnection(connection);
		}  	

		return idCategoria;
	}

}
