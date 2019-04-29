package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.PaisDAOImpl;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.dao.utils.SingleDAO;
import com.isp.seeds.model.Pais;
import com.isp.seeds.service.spi.PaisService;

public class PaisServiceImpl implements PaisService{
	
	private static Logger logger = LogManager.getLogger(CategoriaServiceImpl.class);

	@Override
	public Pais findById(String idPais, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idPais= {} idioma= {} ", idPais, idioma);
		}
		
		Pais pais = null;
		if(idPais != null && idioma != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				pais = SingleDAO.paisDao.findById(connection, idPais, idioma);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return pais;
	}


	@Override
	public List<Pais> findAll(String idioma) throws DataException {
		
			// LOOOOOOGGEEERR
		
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Pais> paises = new ArrayList<Pais>();
			paises = SingleDAO.paisDao.findAll(connection, idioma);

			JDBCUtils.closeConnection(connection);
			return paises;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

}
