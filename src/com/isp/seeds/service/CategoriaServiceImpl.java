package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.cache.Cache;
import com.isp.seeds.cache.CacheManager;
import com.isp.seeds.dao.impl.CategoriaDAOImpl;
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.service.spi.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService{
	
	private static Logger logger = LogManager.getLogger(CategoriaServiceImpl.class);
	private static CategoriaDAO categoriaDao = new CategoriaDAOImpl();


	@Override
	public Categoria findById(Long idCategoria, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idCategoria= {} idioma= {} ", idCategoria, idioma);
		}
		
		Cache <Long, Categoria> cache = CacheManager.getInstance().getCache(CacheNames.CATEGORIAS, Long.class, Categoria.class);
		
		Categoria categoria= cache.get(idCategoria);
		
		if (categoria != null) {
			//EXITO CACHE
			if(logger.isDebugEnabled()) {
				logger.debug("Acierto Cache",idCategoria);
			}
			return categoria;	
			
		} else {
			//FALLO CACHE
			if(logger.isDebugEnabled()) {
				logger.debug("Fallo Cache",idCategoria);
			}
			
			if(idCategoria != null && idioma != null) {

				try {

					Connection connection = ConnectionManager.getConnection();
					categoria = categoriaDao.findById(connection, idCategoria, idioma);
					JDBCUtils.closeConnection(connection);

				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				} catch (DataException e) {
					logger.warn(e.getMessage(), e);
				}finally{
					//JDBCUtils.closeConnection(connection);
				}
			}
			cache.put(idCategoria, categoria);	
			return categoria;			
			
		}
	}

	
	@Override
	public Long findByNombre(String nombreCategoria, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("nombreCategoria= {} idioma= {} ", nombreCategoria, idioma);
		}

		Long idCategoria = null;
		if(nombreCategoria != null) {

			try {

				Connection connection = ConnectionManager.getConnection();				
				idCategoria = categoriaDao.findByNombre(connection, nombreCategoria, idioma);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return idCategoria;
	}

	
	@Override
	public List<Categoria> findAll() throws DataException {
		
		Cache <String , List<Categoria>> cacheListados =
				CacheManager.getInstance().getCache(CacheNames.CATEGORIAS_LISTA, String.class, List.class);
		List<Categoria> todas = cacheListados.get("ALL");
			// LOOOOOOGGEEERR
		
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Categoria> categorias = new ArrayList<Categoria>();
			categorias = categoriaDao.findAll(connection);

			JDBCUtils.closeConnection(connection);
			return categorias;

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
