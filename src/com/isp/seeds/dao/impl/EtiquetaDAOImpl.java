package com.isp.seeds.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.spi.EtiquetaDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Etiqueta;

public class EtiquetaDAOImpl implements EtiquetaDAO {
	
	private static Logger logger = LogManager.getLogger(EtiquetaDAOImpl.class);


	@Override
	public Etiqueta loadNext (ResultSet resultSet)
			throws SQLException {

		Etiqueta e = new Etiqueta();
		int i=1;
		
		Long idEtiqueta = resultSet.getLong(i++);
		String nombre = resultSet.getString(i++);

		e.setIdEtiqueta(idEtiqueta);
		e.setNombreEtiqueta(nombre);

		return e;		
	}
	
	@Override
	public Boolean exists (Connection connection, Long idEtiqueta) 
			throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT ID_ETIQUETA " + 
							" FROM ETIQUETA " +
							" WHERE ID_ETIQUETA = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idEtiqueta);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}

	@Override
	public Etiqueta findById (Connection connection, Long id, String idioma) throws SQLException , DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT e.id_etiqueta, ei.nombre_etiqueta  "+
							"FROM Etiqueta e inner join etiqueta_idioma ei on (e.id_etiqueta = ei.id_etiqueta) " +
							"WHERE e.id_etiqueta = ? AND eI.ID_IDIOMA = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);
			preparedStatement.setString(i++, idioma);
 
			resultSet = preparedStatement.executeQuery();

			Etiqueta e = null;

			if (resultSet.next()) {
				e = loadNext(resultSet);				
			} else {
				throw new DataException("\nEtiqueta with id " +id+ "not found\n");
			}

			return e;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}

	@Override
	public List<Etiqueta> findAll(Connection connection) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

		String queryString = 
				"SELECT e.id_etiqueta, ei.nombre_etiqueta "+
						"FROM Etiqueta e inner join etiqueta_idioma ei on (e.id_etiqueta = ei.id_etiqueta) "; //+
						//"WHERE ci.id_idioma LIKE ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			/*
			int i = 1;                
			preparedStatement.setString(i++, idioma); */
			
			resultSet = preparedStatement.executeQuery();
			
			
			List<Etiqueta> results = new ArrayList<Etiqueta>();                        
			Etiqueta etiqueta = null;
			//int currentCount = 0;

			if(resultSet.next()) {
			//if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					etiqueta = loadNext(resultSet);// DA FALLO AQUI
					results.add(etiqueta);
					//currentCount++;                	
				} while (/*(currentCount < count) &&*/ resultSet.next()) ;
			//}
			}

			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Etiqueta create(Connection connection, Etiqueta e, String idioma)
			throws DataException {
		//Connection connection = null; 
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			//connection = ConnectionManager.getConnection();

			String queryString = "INSERT INTO ETIQUETA () "
					+ "VALUES ()";

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);

			/**/
//			int i = 1;     			
//			preparedStatement.setString(i++, e.getNombreEtiqueta());
			/**/

			int insertedRows = preparedStatement.executeUpdate();

			
			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Etiqueta'");
			}
			
			resultSet = preparedStatement.getGeneratedKeys(); //  FALLO AQUI

			if (resultSet.next()) {
				Long id = resultSet.getLong(1);
				e.setIdEtiqueta(id);				
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}
			
			queryString = "INSERT INTO ETIQUETA_IDIOMA (ID_IDIOMA, ID_ETIQUETA, NOMBRE_ETIQUETA) "
					+ "VALUES (?, ?, ? )";
			
			preparedStatement = connection.prepareStatement(queryString);  //  por que warning????
			
			int i = 1;
			preparedStatement.setString(i++, idioma);
			preparedStatement.setLong(i++, e.getIdEtiqueta());
			preparedStatement.setString(i++, e.getNombreEtiqueta());

			insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Etiqueta'");
			}

			return e;					

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);			
			//JDBCUtils.closeConnection(connection);
		}

	}

	@Override
	public void delete(Connection connection, Long id, String idioma) throws SQLException, DataException {

		PreparedStatement preparedStatement = null;

		try {
			
			String queryString =	
					  "DELETE FROM ETIQUETA " 
					+ "WHERE id_ETIQUETA = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				//throw new DataException("Exception: No removed rows");
			} 

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Long findByNombre(Connection connection, String nombreEtiqueta, String idioma)
			throws SQLException, DataException {

		Long idEtiqueta = null;

		//Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//connection = ConnectionManager.getConnection();

			String sql;
			sql =  "SELECT ID_ETIQUETA "
					+" FROM ETIQUETA_IDIOMA"
					+" WHERE NOMBRE_ETIQUETA = ? AND ID_IDIOMA = ? ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// Establece os parámetros
			int i = 1;
			preparedStatement.setString(i++, nombreEtiqueta);
			preparedStatement.setString(i++, idioma);

			resultSet = preparedStatement.executeQuery();			

			if (resultSet.next()) {
				int j=1;
				idEtiqueta  = resultSet.getLong(j++);

			} else {
				throw new DataException("Non se encontrou a categoria "+nombreEtiqueta);
			}
			if (resultSet.next()) {
				throw new DataException("Categoria "+nombreEtiqueta+" duplicado");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
			//JDBCUtils.closeConnection(connection);
		}  	

		return idEtiqueta;
	}

	@Override
	public Boolean exists(Connection connection, Long idEtiqueta, String idioma) throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT ID_ETIQUETA " + 
							" FROM ETIQUETA_IDIOMA " +
							" WHERE ID_ETIQUETA = ? and ID_IDIOMA = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idEtiqueta);
			preparedStatement.setString(i++, idioma);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}

}
