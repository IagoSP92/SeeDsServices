package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.VideoDAOImpl;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.spi.VideoService;

public class VideoServiceImpl implements VideoService {

	VideoDAO videoDao= null;

	public VideoServiceImpl () {
		videoDao = new VideoDAOImpl();
	}

	@Override
	public Video crearVideo(Video video) throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			video = videoDao.create(connection, video);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException se) { 
			se.printStackTrace();
		}
		catch (Exception e) {  
			e.printStackTrace();
		}
		return video;
	}

	@Override
	public void eliminarVideo(Video video) throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			videoDao.delete(connection, video.getIdContenido());
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException se) { 
			se.printStackTrace();
		}
		catch (Exception e) { 
			e.printStackTrace();
		}
	}

	@Override
	public void editarVideo(Video video) throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			videoDao.update(connection, video);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException se) {  
			se.printStackTrace();
		}
		catch (Exception e) {  
			e.printStackTrace();
		}		
	}

	@Override
	public Video buscarId(Long idVideo) throws DataException {
		try {
			if(idVideo !=null) {
				Connection connection = ConnectionManager.getConnection();
				Video video = new Video();
				video = videoDao.findById(connection, idVideo);
				JDBCUtils.closeConnection(connection);
				return video;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public List<Video> buscarTodos() throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Video> videos = new ArrayList<Video>();
			videos = videoDao.findAllVideos(connection);
			JDBCUtils.closeConnection(connection);
			return videos;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public List<Video> buscarPorAutor(Long idAutor, String idioma) throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Video> videos = new ArrayList<Video>();
			videos = videoDao.findByAutor(connection, idAutor);
			JDBCUtils.closeConnection(connection);
			return videos;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public List<Video> buscarPorCategoria(Long idCategoria, String idioma) throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Video> videos = new ArrayList<Video>();
			videos = videoDao.findByCategoria(connection, idCategoria);
			JDBCUtils.closeConnection(connection);
			return videos;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

}
