package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.impl.VideoDAOImpl;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.spi.VideoService;
import com.isp.seeds.service.util.Results;

public class VideoServiceImpl implements VideoService {

	private static Logger logger = LogManager.getLogger(VideoServiceImpl.class);
	
	private VideoDAO videoDao = null;
	private ContenidoDAO contenidoDao = null;
	
	public VideoServiceImpl() {
		
		videoDao = new VideoDAOImpl();
		contenidoDao = new ContenidoDAOImpl();		
	}
		

	@Override
	public Video crearVideo(Video video) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Video= {}", video);
		}
		Connection connection =null;
		Boolean commit = false;
		
		if(video !=null) {
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				
				video = videoDao.create(connection, video);
				contenidoDao.agignarCategoria(connection, video.getId(), video.getCategoria().getIdCategoria());
				commit=true;
				return video;
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
	public void eliminarVideo(Long idVideo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idVideo= {} ", idVideo);
		}
		
		Connection connection = null;

		if(idVideo !=null) {
			try {
				connection = ConnectionManager.getConnection();
				videoDao.delete(connection, idVideo);				
			}
			catch (SQLException e) { 
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) { 
				logger.warn(e.getMessage(), e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
	}

	
	@Override
	public void editarVideo(Video video) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Video= {} ", video);
		}
		
		Connection connection = null;
		boolean commit = false;

		if(video !=null) {
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				videoDao.update(connection, video);
				commit=true;				
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
	}
	

	@Override
	public Video buscarId(Long idSesion, Long idVideo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idVideo= {} ", idVideo);
		}
		
		Connection connection = null;
		Video video = null;
		
		if(idVideo!=null) {
			try {
				connection = ConnectionManager.getConnection();
				video = videoDao.findById(connection,idSesion, idVideo);				

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return video;
	}

	
	@Override
	public Results<Contenido> cargarGuardados(Long idSesion,  int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion= {} ", idSesion);
		}
		
		Connection connection =null;

		if(idSesion != null) {
			try {
				connection = ConnectionManager.getConnection();
				Results<Contenido> contenidos = videoDao.cargarGuardados(connection, idSesion, startIndex, count);
				return contenidos;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return null;
	}
	

}
