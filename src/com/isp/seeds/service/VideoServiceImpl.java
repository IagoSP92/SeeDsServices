package com.isp.seeds.service;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.spi.VideoService;

public class VideoServiceImpl implements VideoService {

	@Override
	public Video crearVideo(Video video) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Video eliminarVideo(Video video) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editarVideo(Video video, String idioma) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Video buscarId(Long idVideo, String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> buscarTodos(String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> buscarPorAutor(Long idAutor, String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> buscarPorCategoria(Long idCategoria, String idioma) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

}
