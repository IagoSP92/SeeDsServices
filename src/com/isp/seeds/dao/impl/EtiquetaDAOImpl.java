package com.isp.seeds.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.spi.EtiquetaDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Etiqueta;

public class EtiquetaDAOImpl implements EtiquetaDAO {

	private Etiqueta loadNext (ResultSet resultSet)
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
	public Etiqueta update(Connection conexion, Etiqueta e, String idioma) throws SQLException, DataException {
		// TODO Auto-generated method stub
		return null;
	}

}
