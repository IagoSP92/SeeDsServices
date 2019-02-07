package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.EtiquetaDAOImpl;
import com.isp.seeds.dao.spi.EtiquetaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Etiqueta;
import com.isp.seeds.service.spi.EtiquetaService;

public class EtiquetaServiceImpl implements EtiquetaService {
	
	private static Logger logger = LogManager.getLogger(EtiquetaServiceImpl.class);
	private static EtiquetaDAO etiquetaDao = new EtiquetaDAOImpl();


	@Override
	public Etiqueta findById(Long idEtiqueta, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idEtiqueta= {} idioma= {} ", idEtiqueta, idioma);
		}
		
		try {

			if(idEtiqueta != null && idioma != null) {
				Connection connection = ConnectionManager.getConnection();
				Etiqueta etiqueta = new Etiqueta();
				etiqueta = etiquetaDao.findById(connection, idEtiqueta, idioma);

				JDBCUtils.closeConnection(connection);
				return etiqueta;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public Long findByNombre(String nombreEtiqueta, String idioma) throws  DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("nombreEtiqueta= {} idioma= {} ", nombreEtiqueta, idioma);
		}
		
		try {

			if(nombreEtiqueta != null) {
				Connection connection = ConnectionManager.getConnection();
				Long idEtiqueta = null;
				idEtiqueta = etiquetaDao.findByNombre(connection, nombreEtiqueta, idioma);

				JDBCUtils.closeConnection(connection);
				return idEtiqueta;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public List<Etiqueta> findAll() throws DataException {
		
		// LOGGER
		
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
			etiquetas = etiquetaDao.findAll(connection);

			JDBCUtils.closeConnection(connection);
			return etiquetas;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public Etiqueta create(Etiqueta etiqueta, String idioma) throws  DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Etiqueta= {} idioma= {} ", etiqueta, idioma);
		}
		
		try {

			if(etiqueta != null) {
				Connection connection = ConnectionManager.getConnection();

				etiquetaDao.create(connection, etiqueta, idioma);
				
				JDBCUtils.closeConnection(connection);

				return etiqueta;
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void delete(Long idEtiqueta, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idEtiqueta= {} idioma= {} ", idEtiqueta, idioma);
		}
		
		try {

			if(idEtiqueta != null) {
				Connection connection = ConnectionManager.getConnection();
				
				if (!etiquetaDao.exists(connection, idEtiqueta, idioma)) {
					etiquetaDao.delete(connection, idEtiqueta, idioma);
				}

				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

}
