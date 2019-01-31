package com.isp.seeds.service;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.spi.ContenidoService;

public class ContenidoServiceImpl implements ContenidoService {

	@Override
	public Contenido verContenido(Long idContenido) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contenido crearContenido(Contenido contenido) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contenido eliminarContenido(Contenido contenido) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contenido cambiarNombre(Contenido contenido) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void asignarCategoria(Long idContenido, Long idCategoria) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asignarEtiqueta(Long idContenido, Long idEtiqueta) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seguirContenido(Long idUsuario, Long idContenido, Boolean siguiendo) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void denunciarContenido(Long idUsuario, Long idContenido, Boolean denunciado) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valorarContenido(Long idUsuario, Long idContenido, int valoracion) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardarContenido(Long idUsuario, Long idContenido, Boolean guardado) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void comentarContenido(Long idUsuario, Long idContenido, String comentario) throws DataException {
		// TODO Auto-generated method stub
		
	}

}
