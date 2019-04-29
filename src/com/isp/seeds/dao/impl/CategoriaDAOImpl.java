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
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {
	
	private static Logger logger = LogManager.getLogger(CategoriaDAOImpl.class);
	

	public Categoria findById(Connection connection, Long id, String idioma)
			throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idCategoria= {} idioma={}", id, idioma);
		}

		Categoria c = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql;
			sql =  "SELECT C.ID_CATEGORIA, CI.NOMBRE_CATEGORIA "
					+" FROM CATEGORIA C INNER JOIN CATEGORIA_IDIOMA CI ON (C.ID_CATEGORIA = CI.ID_CATEGORIA) "
					+" WHERE C.ID_CATEGORIA = ? AND CI.ID_IDIOMA = ? ";

			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);
			preparedStatement.setString(i++, idioma);
			
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();			

			if (resultSet.next()) {
				c  = loadNext(resultSet);
			} else {
				throw new DataException("Non se encontrou a categoria "+id);
			}
			if (resultSet.next()) {
				throw new DataException("Categoria "+id+" duplicado");
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return c;
	}


	@Override
	public List<Categoria> findAll(Connection connection, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idioma={}", idioma);
		}
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = 
					"SELECT C.ID_CATEGORIA, CI.NOMBRE_CATEGORIA "
					+" FROM CATEGORIA C INNER JOIN CATEGORIA_IDIOMA CI ON (C.ID_CATEGORIA = CI.ID_CATEGORIA) "
					+" WHERE CI.ID_IDIOMA = ? "
					+" ORDER BY C.ID_CATEGORIA ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;
			preparedStatement.setString(i++, idioma);
			
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			List<Categoria> results = new ArrayList<Categoria>();                        
			Categoria categoria = null;
						
			if (resultSet.next()) {
				do {
					categoria = loadNext(resultSet);
					results.add(categoria);  
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
	
	
	
	public Boolean exists (Connection connection, Long idCategoria) 
			throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idCategoria= {} ", idCategoria);
		}
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					" SELECT ID_CATEGORIA FROM CATEGORIA WHERE ID_CATEGORIA = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idCategoria);
			
			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return exist;
	}
	
	
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


	/*@Override
	public Long findByNombre(Connection connection, String nombreCategoria, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("nombreCategoria= {} idioma={}", nombreCategoria, idioma);
		}

		Long idCategoria = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql;
			sql =  "SELECT ID_CATEGORIA "
					+" FROM CATEGORIA_IDIOMA"
					+" WHERE NOMBRE_CATEGORIA = ? AND ID_IDIOMA = ? ";

			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;
			preparedStatement.setString(i++, nombreCategoria);
			preparedStatement.setString(i++, idioma);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
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

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return idCategoria;
	}*/

}
