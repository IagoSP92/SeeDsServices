package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Video;

public interface VideoService {
	
	public Video crearVideo (Video video) 
			throws DataException;
	
	public Video eliminarVideo (Video video) 
			throws DataException;
	
	
	public void editarVideo (Video video, String idioma)
			throws DataException;
	

	public Video buscarId (Long idVideo, String idioma)
			throws DataException;
	
	public List<Video> buscarTodos (String idioma)
			throws DataException;
	
	public List<Video> buscarPorAutor (Long idAutor, String idioma)
			throws DataException;
	
	public List<Video> buscarPorCategoria (Long idCategoria, String idioma)
			throws DataException;
	
	
	
	

}
