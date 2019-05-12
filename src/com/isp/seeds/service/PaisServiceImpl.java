package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.dao.impl.PaisDAOImpl;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Pais;
import com.isp.seeds.service.spi.PaisService;

public class PaisServiceImpl implements PaisService{
	
	private static Logger logger = LogManager.getLogger(CategoriaServiceImpl.class);
	
	PaisDAO paisDao = null;
	
	public PaisServiceImpl() {
		paisDao = new PaisDAOImpl();
	}
	
	
	@Override
	public Pais findById(String idPais, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idPais= {} idioma= {} ", idPais, idioma);
		}
		
		Pais pais = null;
		Connection connection = null;
		
		if(idPais != null && idioma != null) {

			try {
				connection = ConnectionManager.getConnection();
				pais = paisDao.findById(connection, idPais, idioma);				

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return pais;
	}


	@Override
	public List<Pais> findAll(String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Find All Pais -> idioma= {} ", idioma);
		}
		
		Connection connection =null;
		List<Pais> paises = null;
		
		try {
			connection = ConnectionManager.getConnection();			
			paises = paisDao.findAll(connection, idioma);			

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			JDBCUtils.closeConnection(connection);
		}
		return paises;
	}

}
