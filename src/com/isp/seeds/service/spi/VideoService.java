package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Video;

public interface VideoService {
	
	public Video crearVideo (Video video) 
			throws DataException;
	
	public void eliminarVideo (Video video) 
			throws DataException;
	
	
	public void editarVideo (Video video)
			throws DataException;
	

	public Video buscarId (Long idVideo)
			throws DataException;
	
	public List<Video> buscarTodos ()
			throws DataException;
	
	public List<Video> buscarPorAutor (Long idAutor, String idioma)
			throws DataException;
	
	public List<Video> buscarPorCategoria (Long idCategoria, String idioma)
			throws DataException;
	
	
	
	

}
