package com.isp.seeds.service.spi;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.util.Results;

public interface VideoService {
	
	public Video crearVideo (Video video) 
			throws DataException;
	
	public void eliminarVideo (Long idVideo) 
			throws DataException;	
	
	public void editarVideo (Video video)
			throws DataException;	

	public Video buscarId (Long idSesion, Long idVideo)
			throws DataException;
	
	public Results<Contenido> cargarGuardados (Long idSesion, Long idContenido, int startIndex, int count)
			throws DataException;
	
//	public List<Video> buscarTodos ()
//			throws DataException;
	
//	public List<Video> buscarPorAutor (Long idAutor)
//			throws DataException;
	
//	public List<Video> buscarPorCategoria (Long idCategoria)
//			throws DataException;
	
	
	
	

}
