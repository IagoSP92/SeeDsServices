package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.ListaDAOImpl;
import com.isp.seeds.dao.impl.VideoDAOImpl;
import com.isp.seeds.dao.spi.ListaDAO;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Lista;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.spi.ListaService;
import com.isp.seeds.service.util.Results;
import com.isp.seeds.service.util.ServiceUtils;

public class ListaServiceImpl implements ListaService {

	private static Logger logger = LogManager.getLogger(ListaServiceImpl.class);
	ListaDAO listaDao= null;
	VideoDAO videoDao= null;

	public ListaServiceImpl () {
		listaDao = new ListaDAOImpl();
		videoDao = new VideoDAOImpl();
	}

	@Override
	public Lista crearLista(Lista lista) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}
		Connection connection = null;
		Boolean commit = false;
		if(lista != null) {
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				lista = listaDao.create(connection, lista);
				ServiceUtils.contenidoDao.agignarCategoria(connection, lista.getId(), lista.getCategoria().getIdCategoria());
				commit=true;
				return lista;
			}
			catch (SQLException e) { 
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				logger.warn(e.getMessage(), e);
			} finally {
				JDBCUtils.closeConnection(connection, commit);
			}
		}
		return null;
	}

	@Override
	public void eliminarLista(Lista lista) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Lista= {} ", lista);
		}

		if(lista != null) {

			try {
				Connection connection = ConnectionManager.getConnection();
				listaDao.delete(connection, lista.getId());
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
	public Lista buscarId(Long idSesion, Long idLista) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion={} idLista= {} ",idSesion, idLista);
		}

		Lista lista = null;
		if(idLista != null) {
			
			try {
				Connection connection = ConnectionManager.getConnection();
				lista = listaDao.findById(connection, idSesion, idLista);
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


//	@Override
//	public List<Lista> buscarTodas() throws DataException {
//		
//		//LOGGER
//		
//		try {
//			Connection connection = ConnectionManager.getConnection();
//			List<Lista> listas = new ArrayList<Lista>();
//			listas = listaDao.findAllListas(connection);
//			JDBCUtils.closeConnection(connection);
//			return listas;
//		} catch (SQLException e) {
//			logger.warn(e.getMessage(), e);
//		} catch (DataException e) {
//			logger.warn(e.getMessage(), e);
//		}finally{
//			//JDBCUtils.closeConnection(connection);
//		}
//		return null;
//	}
	

//	@Override
//	public List<Lista> buscarPorAutor(Long idAutor) throws DataException {
//
//		if(logger.isDebugEnabled()) {
//			logger.debug ("idAutor= {} ", idAutor);
//		}
//
//		if(idAutor != null) {
//			try {
//				Connection connection = ConnectionManager.getConnection();
//				List<Lista> listas = new ArrayList<Lista>();
//				listas = listaDao.findByAutor (connection, idAutor);
//				JDBCUtils.closeConnection(connection);
//				return listas;
//			} catch (SQLException e) {
//				logger.warn(e.getMessage(), e);
//			} catch (DataException e) {
//				logger.warn(e.getMessage(), e);
//			}finally{
//				//JDBCUtils.closeConnection(connection);
//			}
//		}
//		return null;
//	}


//	@Override
//	public List<Lista> buscarPorCategoria(Long idCategoria) throws DataException {
//
//		if(logger.isDebugEnabled()) {
//			logger.debug ("idCategoria= {} ", idCategoria);
//		}
//
//		if(idCategoria != null) {
//			try {
//				Connection connection = ConnectionManager.getConnection();
//				List<Lista> listas = new ArrayList<Lista>();
//				listas = listaDao.findByCategoria (connection, idCategoria);
//				JDBCUtils.closeConnection(connection);
//				return listas;
//			} catch (SQLException e) {
//				logger.warn(e.getMessage(), e);
//			} catch (DataException e) {
//				logger.warn(e.getMessage(), e);
//			}finally{
//				//JDBCUtils.closeConnection(connection);
//			}
//		}
//		return null;
//	}

	
	@Override
	public Results<Video> verVideosLista(Long idLista, int startIndex, int count) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idLista= {} ", idLista);
		}

		if(idLista != null) {
			try {
				Connection connection = ConnectionManager.getConnection();
				Results<Video> videos = videoDao.verVideosLista (connection, idLista, startIndex, count);
				JDBCUtils.closeConnection(connection);
				return videos;
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
	public Results<Contenido> cargarGuardados(Long idSesion, int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion= {} ", idSesion);
		}

		if(idSesion != null) {

			try {
				Connection connection = ConnectionManager.getConnection();

				Results<Contenido> contenidos = listaDao.cargarGuardados(connection, idSesion, startIndex, count);
				JDBCUtils.closeConnection(connection);

				return contenidos;

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
	public Results<Contenido> cargarSeguidos(Long idSesion, int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion= {} ", idSesion);
		}

		if(idSesion != null) {

			try {
				Connection connection = ConnectionManager.getConnection();

				Results<Contenido> contenidos = listaDao.cargarSeguidos(connection, idSesion, startIndex, count);
				JDBCUtils.closeConnection(connection);

				return contenidos;

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
