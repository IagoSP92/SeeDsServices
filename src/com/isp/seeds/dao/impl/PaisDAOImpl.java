package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Pais;

public class PaisDAOImpl implements PaisDAO{
	
	private static Logger logger = LogManager.getLogger(PaisDAOImpl.class);
	
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
	public Pais findById(Connection connection, String idPais, String idioma)
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idPais= {} idioma={}", idPais, idioma);
		}

		Pais p = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql;
			sql =  "SELECT p.ID_PAIS, pi.NOMBRE_PAIS "
					+"FROM PAIS p inner join PAIS_idioma pi on (p.ID_PAIS = pi.ID_PAIS)"
					+"WHERE p.ID_PAIS = ? and pi.id_idioma = ? ";

			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, idPais);
			preparedStatement.setString(i++, idioma);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();						

			if (resultSet.next()) {
				p  = loadNext(resultSet);				
			}
			else {
				throw new DataException("Non se encontrou o pais "+idPais);
			}
			if (resultSet.next()) {
				throw new DataException("Pais "+idPais+" duplicado");
			}
			
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return p;
	}


	@Override
	public List<Pais> findAll(Connection connection, String idioma) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = 
					"SELECT p.id_pais, pI.nombre_pais " + 
					"FROM Pais p inner join PAIS_idioma pi on (p.ID_PAIS = pi.ID_PAIS)  " +
					" WHERE PI.ID_IDIOMA = ? " +
					"ORDER BY pi.nombre_pais asc ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setString(1, idioma);
			
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			List<Pais> results = new ArrayList<Pais>();                        
			Pais pais = null;
						
			if (resultSet.next()) {
				do {
					pais = loadNext(resultSet);
					results.add(pais);  
				} while (resultSet.next()) ;
			}
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

}


