package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Lista;
import com.isp.seeds.model.Video;

public interface ListaService {
	
	public Lista crearLista (Lista lista) 
			throws DataException;
	
	public void eliminarLista (Lista lista) 
			throws DataException;
	
	
	public void editarLista (Lista lista)
			throws DataException;
	
	public void meterVideo (Long idLista, Long idVideo, Integer posicion)
			throws DataException;
	
	public void sacarVideo (Long idLista, Long idVideo)
			throws DataException;
	

	public Lista buscarId (Long idLista)
			throws DataException;
	
	public List<Lista> buscarTodas ()
			throws DataException;
	
	public List<Lista> buscarPorAutor (Long idAutor)
			throws DataException;
	
	public List<Lista> buscarPorCategoria (Long idCategoria)
			throws DataException;
	
	public List<Video> verVideosLista (Long idLista)
			throws DataException;
}
