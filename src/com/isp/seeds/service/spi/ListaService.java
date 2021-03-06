package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Lista;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.util.Results;

public interface ListaService {
	
	public Lista crearLista (Lista lista) 
			throws DataException;
	
	public void editarLista (Lista lista)
			throws DataException;	
	
	public void eliminarLista (Lista lista) 
			throws DataException;	

	public Lista buscarId (Long idSesion, Long idLista)
			throws DataException;
	
	
	public Results<Video> verVideosLista (Long idLista, int startIndex, int count)
			throws DataException;
	
	public List<Contenido> verContenidosLista (Long idLista)
			throws DataException;
	
	
	public void redefinirIncluidos(Long idLista, List<Long> listIdVideo)
			throws DataException;
	
	
	public Results<Contenido> cargarGuardados(Long idSesion, int startIndex,
			int count) throws DataException;
	
	public Results<Contenido> cargarSeguidos(Long idSesion, int startIndex,
			int count) throws DataException;

}
