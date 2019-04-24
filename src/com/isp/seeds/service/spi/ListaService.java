package com.isp.seeds.service.spi;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Lista;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.util.Results;

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

	public Lista buscarId (Long idSesion, Long idLista)
			throws DataException;
	
//	public List<Lista> buscarTodas ()
//			throws DataException;
	
//	public List<Lista> buscarPorAutor (Long idAutor)
//			throws DataException;
	
//	public List<Lista> buscarPorCategoria (Long idCategoria)
//			throws DataException;
	
	public Results<Video> verVideosLista (Long idLista, int startIndex, int count)
			throws DataException;
	
	public Results<Contenido> cargarGuardados(Long idSesion, Long idContenido, int startIndex,
			int count) throws DataException;
}
