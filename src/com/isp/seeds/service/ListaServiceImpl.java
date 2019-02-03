package com.isp.seeds.service;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.spi.ListaService;

public class ListaServiceImpl implements ListaService {

	@Override
	public Lista crearLista(Lista lista) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lista eliminarLista(Lista lista) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editarLista(Lista lista, String idioma) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void meterVideo(Lista lista, String idioma) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sacarVideo(Lista lista, String idioma) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Lista buscarId(Long idVideo, String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lista> buscarTodas(String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lista> buscarPorAutor(Long idAutor, String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lista> buscarPorCategoria(Long idCategoria, String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

}
