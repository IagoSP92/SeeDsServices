package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Lista;

public interface ListaService {
	
	public Lista crearLista (Lista lista) 
			throws DataException;
	
	public void eliminarLista (Lista lista) 
			throws DataException;
	
	
	public void editarLista (Lista lista)
			throws DataException;
	
	public void meterVideo (Long idLista, Long idVideo)
			throws DataException;
	
	public void sacarVideo (Long idLista, Long idVideo)
			throws DataException;
	

	public Lista buscarId (Long idVideo)
			throws DataException;
	
	public List<Lista> buscarTodas ()
			throws DataException;
	
	public List<Lista> buscarPorAutor (Long idAutor, String idioma)
			throws DataException;
	
	public List<Lista> buscarPorCategoria (Long idCategoria, String idioma)
			throws DataException;
}
