package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.VideoDAOImpl;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.spi.VideoService;

public class VideoServiceImpl implements VideoService {

	private static Logger logger = LogManager.getLogger(VideoServiceImpl.class);
	VideoDAO videoDao= null;

	public VideoServiceImpl () {
		videoDao = new VideoDAOImpl();
	}

	@Override
	public Video crearVideo(Video video) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Video= {}", video);
		}
		
		if(video !=null) {
			try {
				Connection connection = ConnectionManager.getConnection();
				video = videoDao.create(connection, video);
				JDBCUtils.closeConnection(connection);
			}
			catch (SQLException e) { 
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				logger.warn(e.getMessage(), e);
			}
		}
		return video;
	}

	@Override
	public void eliminarVideo(Long idVideo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idVideo= {} ", idVideo);
		}

		if(idVideo !=null) {
			try {
				Connection connection = ConnectionManager.getConnection();
				videoDao.delete(connection, idVideo);
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
	public void editarVideo(Video video) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Video= {} ", video);
		}

		if(video !=null) {
			try {
				Connection connection = ConnectionManager.getConnection();
				videoDao.update(connection, video);
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
	public Video buscarId(Long idVideo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idVideo= {} ", idVideo);
		}

		Video video = null;
		if(idVideo!=null) {

			try {
				Connection connection = ConnectionManager.getConnection();
				video = videoDao.findById(connection, idVideo);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return video;
	}

	@Override
	public List<Video> buscarTodos() throws DataException {
		
		//LOGGER
		
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Video> videos = new ArrayList<Video>();
			videos = videoDao.findAllVideos(connection);
			JDBCUtils.closeConnection(connection);
			return videos;
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
	public List<Video> buscarPorAutor(Long idAutor) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idAutor= {} ", idAutor);
		}

		if(idAutor != null) {

			try {
				Connection connection = ConnectionManager.getConnection();
				List<Video> videos = new ArrayList<Video>();
				videos = videoDao.findByAutor(connection, idAutor);
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
	public List<Video> buscarPorCategoria(Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idCategoria= {} ", idCategoria);
		}

		if (idCategoria!=null) {
			try {
				Connection connection = ConnectionManager.getConnection();
				List<Video> videos = new ArrayList<Video>();
				videos = videoDao.findByCategoria(connection, idCategoria);
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

}
