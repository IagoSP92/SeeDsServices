package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.CategoriaDAOImpl;
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.service.spi.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService{
	
	private static Logger logger = LogManager.getLogger(CategoriaServiceImpl.class);
	
	private CategoriaDAO categoriaDao = null;
	
	public CategoriaServiceImpl() {
		categoriaDao = new CategoriaDAOImpl();
	}
	
	
	@Override
	public Categoria findById(Long idCategoria, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idCategoria= {} idioma= {} ", idCategoria, idioma);
		}

		Categoria categoria= null;

		if(idCategoria != null && idioma != null) {
			Connection connection = null;
			try {
				connection = ConnectionManager.getConnection();
				categoria = categoriaDao.findById(connection, idCategoria, idioma);					

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return categoria;			

	}


	
	@Override
	public List<Categoria> findAll(String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idioma= {} ", idioma);
		}
		
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			
			List<Categoria> categorias = new ArrayList<Categoria>();
			categorias = categoriaDao.findAll(connection, idioma);			
			return categorias;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			JDBCUtils.closeConnection(connection);
		}
		return null;
	}

}
