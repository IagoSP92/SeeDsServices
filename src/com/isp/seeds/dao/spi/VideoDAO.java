package com.isp.seeds.dao.spi;

import java.sql.Connection;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.util.Results;

public interface VideoDAO {	
	
	public Video findById(Connection connection,Long idUsuario, Long idVideo) 
			throws DataException;
	
	public Results<Video> verVideosLista (Connection connection, Long idLista, int startIndex, int count)
			throws DataException;
	
	public Video create (Connection connection, Video video) 
			throws DataException;
	
	public void update (Connection connection, Video video) 
			throws DataException;
	
	public void delete (Connection connection, Long idVideo) 
			throws DataException;
	
	public Results<Contenido> cargarSeguidos (Connection connection, Long idContenido, int startIndex, int count)
			throws DataException;
	

	Results<Contenido> cargarGuardados(Connection connection, Long idSesion, int startIndex,
			int count) throws DataException;
	
//	public List<Video> findByAutor(Connection connection, Long idAutor) 
//			throws DataException;
	
//	public List<Video> findByCategoria(Connection connection, Long idCategoria) 
//			throws DataException;
	
//	public List<Video> findByCriteria(Connection connection, VideoCriteria video) 
//			throws DataException;


//	public List<Video> findAllVideos(Connection connection) 
//			throws DataException;
	
//	public Integer getReproducciones (Connection connection, Long idVideo)  // AÑADIR SUMAR 1?
//			throws DataException;



}
