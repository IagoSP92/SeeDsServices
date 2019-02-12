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

		if(lista != null) {

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
		}
		return lista;
	}

	@Override
	public void eliminarLista(Lista lista) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}

		if(lista != null) {

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
	}

	@Override
	public void editarLista(Lista lista) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}

		if(lista != null) {

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
	}

	@Override
	public void meterVideo(Long idLista, Long idVideo, Integer posicion) throws DataException {    // PODRIA INSERTAR AL FINAL SI POSICION ES NULL

		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} idVideo= {} posicion= {}", idLista, idVideo, posicion);
		}

		if(idLista!=null && idVideo!=null  && posicion!=null) {
			
			try {
				Connection connection = ConnectionManager.getConnection();
				listaDao.insertInList(connection, idLista, idVideo, posicion);
				JDBCUtils.closeConnection(connection);
			}
			catch (SQLException e) {  
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				logger.warn(e.getMessage(), e);
			}
		}
	}


	@Override
	public void sacarVideo(Long idLista, Long idVideo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} idVideo= {} ", idLista, idVideo);
		}

		if(idLista!=null && idVideo!=null) {
			
			try {
				Connection connection = ConnectionManager.getConnection();
				listaDao.deleteFromList(connection, idLista, idVideo);
				JDBCUtils.closeConnection(connection);
			}
			catch (SQLException e) {  
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				logger.warn(e.getMessage(), e);
			}
		}
	}

	
	@Override
	public Lista buscarId(Long idLista) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} ", idLista);
		}

		Lista lista = null;
		if(idLista != null) {
			
			try {
				Connection connection = ConnectionManager.getConnection();
				lista = listaDao.findById(connection, idLista);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return lista;
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
	public List<Lista> buscarPorAutor(Long idAutor) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idAutor= {} ", idAutor);
		}

		if(idAutor != null) {
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
		}
		return null;
	}


	@Override
	public List<Lista> buscarPorCategoria(Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idCategoria= {} ", idCategoria);
		}

		if(idCategoria != null) {
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
		}
		return null;
	}

}
