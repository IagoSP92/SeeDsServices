package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.util.Results;

public interface VideoDAO {		
	
	public Video create (Connection connection, Video video) 
			throws DataException;
	
	public void update (Connection connection, Video video) 
			throws DataException;
	
	public void delete (Connection connection, Long idVideo) 
			throws DataException;
	
	
	public Video findById(Connection connection,Long idUsuario, Long idVideo) 
			throws DataException;
	
	
	public Results<Video> verVideosLista (Connection connection, Long idLista, int startIndex, int count)
			throws DataException;
	
	public List<Contenido> verContenidosLista (Connection connection, Long idLista)
			throws DataException;
	
	
	public Results<Contenido> cargarSeguidos (Connection connection, Long idContenido, int startIndex, int count)
			throws DataException;
	
	public Results<Contenido> cargarGuardados(Connection connection, Long idSesion, int startIndex,
			int count) throws DataException;
	


}
