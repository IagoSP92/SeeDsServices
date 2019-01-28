package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;

public class CategoriaDAOImpl {
	
	private Categoria loadNext (ResultSet resultSet)
			throws Exception {

		Categoria c = new Categoria();
		int i=1;

		String nombre = resultSet.getString(i++);

		c = new Categoria();

		c.setNombreCategoria(nombre);

		return c;		
	}


	public Categoria findById(Connection connection, Long id)
			throws Exception {

		Categoria c = null;

		//Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//connection = ConnectionManager.getConnection();

			String sql;
			sql =  "SELECT ID_CATEGORIA, NOMBRE_CATEGORIA "
					+"FROM CATEGORIA "
					+"WHERE ID_CATEGORIA = ? ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setLong(i++, id);


			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			if (resultSet.next()) {
				c  = loadNext(resultSet);
				System.out.println("Cargado "+ c);

			} else {
				throw new Exception("Non se encontrou a categoria "+id);
			}
			if (resultSet.next()) {
				throw new Exception("Categoria "+id+" duplicado");
			}

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
			//JDBCUtils.closeConnection(connection);
		}  	

		return c;
	}

}
