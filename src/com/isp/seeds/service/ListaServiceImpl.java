package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.ListaDAOImpl;
import com.isp.seeds.dao.spi.ListaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.spi.ListaService;

public class ListaServiceImpl implements ListaService {

	private static Logger logger = LogManager.getLogger(ListaServiceImpl.class);
	ListaDAO listaDao= null;

	public ListaServiceImpl () {
		listaDao = new ListaDAOImpl();
	}

	@Override
	public Lista crearLista(Lista lista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();
			lista = listaDao.create(connection, lista);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException e) { 
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) {  
			logger.warn(e.getMessage(), e);
		}
		return lista;
	}

	@Override
	public void eliminarLista(Lista lista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();
			listaDao.delete(connection, lista.getIdContenido());
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException e) { 
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) { 
			logger.warn(e.getMessage(), e);
		}
	}

	@Override
	public void editarLista(Lista lista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();
			listaDao.update(connection, lista);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException e) {  
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) {  
			logger.warn(e.getMessage(), e);
		}	
	}

	@Override
	public void meterVideo(Long idLista, Long idVideo) throws DataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sacarVideo(Long idLista, Long idVideo) throws DataException {
		// TODO Auto-generated method stub

	}

	@Override
	public Lista buscarId(Long idLista) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} ", idLista);
		}
		
		try {
			if(idLista !=null) {
				Connection connection = ConnectionManager.getConnection();
				Lista lista = new Lista();
				lista = listaDao.findById(connection, idLista);
				JDBCUtils.closeConnection(connection);
				return lista;
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
	public List<Lista> buscarTodas() throws DataException {
		
		//LOGGER
		
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Lista> listas = new ArrayList<Lista>();
			listas = listaDao.findAllListas(connection);
			JDBCUtils.closeConnection(connection);
			return listas;
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
	public List<Lista> buscarPorAutor(Long idAutor, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idAutor= {} idioma= {}", idAutor, idioma);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Lista> listas = new ArrayList<Lista>();
			listas = listaDao.findByAutor (connection, idAutor);
			JDBCUtils.closeConnection(connection);
			return listas;
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
	public List<Lista> buscarPorCategoria(Long idCategoria, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idCategoria= {} idioma= {}", idCategoria, idioma);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Lista> listas = new ArrayList<Lista>();
			listas = listaDao.findByCategoria (connection, idCategoria);
			JDBCUtils.closeConnection(connection);
			return listas;
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
