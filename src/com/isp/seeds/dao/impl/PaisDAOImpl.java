package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Pais;

public class PaisDAOImpl implements PaisDAO{
	
	private Pais loadNext (ResultSet resultSet) throws SQLException {
		
		Pais p = new Pais();
		int i=1;
		
		String id = resultSet.getString(i++);
		String nombre = resultSet.getString(i++);
		
		p = new Pais();
		p.setNombrePais(nombre);
		p.setIdPais(id);
		
		return p;		
	}

	@Override
	public Pais findById(Connection connection, String id)
			throws DataException {

		Pais p = null;

		//Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//connection = ConnectionManager.getConnection();

			String sql;
			sql =  "SELECT ID_PAIS, NOMBRE_PAIS "
					+"FROM PAIS "
					+"WHERE ID_PAIS = ? ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setString(i++, id);


			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			if (resultSet.next()) {
				p  = loadNext(resultSet);
				System.out.println("Cargado "+ p);
				
			} else {
				throw new DataException("Non se encontrou o pais "+id);
			}
			if (resultSet.next()) {
				throw new DataException("Pais "+id+" duplicado");
			}

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
			//JDBCUtils.closeConnection(connection);
		}  	

		return p;
	}



	public List<Pais> findByNombre(Connection connection, String criterioNombre, String ap1)
			throws DataException {	

		//Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{

			//connection = ConnectionManager.getConnection();

			String sql;
			sql =    " SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, MANAGER_ID " 
					+" FROM EMPLOYEES "
					+" WHERE "
					+"	UPPER(FIRST_NAME) LIKE '%?%'" 
					+"    and"
					+"    UPPER(LAST_NAME) LIKE '%?%'";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setString(i++, criterioNombre);
			preparedStatement.setString(i++, ap1);


			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set

			String idPais = null;
			String nombrePais = null;

			List<Pais> paises = new ArrayList<Pais>();
			Pais p = null;
			while (resultSet.next()) {
				i = 1;
				idPais = resultSet.getString(i++);
				nombrePais = resultSet.getString(i++);

				p = new Pais();
				p.setIdPais(idPais);
				p.setNombrePais(nombrePais);

				paises.add(p);
			} 
			return paises;
		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
			//JDBCUtils.closeConnection(connection);
		}  	
	}




	@Override
	public List<Pais> findAll(Connection connection) throws DataException {  // CURRENTCOUNT??

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT p.id_pais, p.nombre_pais " + 
					"FROM Pais p  " +
					"ORDER BY p.nombre_pais asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// Execute query.
			resultSet = preparedStatement.executeQuery();
			
			// Recupera la pagina de resultados
			List<Pais> results = new ArrayList<Pais>();                        
			Pais pais = null;
			int currentCount = 0;
						
			if (resultSet.next()) {
				do {
					pais = loadNext(resultSet);
					results.add(pais);  
					currentCount++;                	
				} while (resultSet.next()) ;
			}
			
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

}




