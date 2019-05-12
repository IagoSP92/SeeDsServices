package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.util.Results;

public interface ContenidoService {
	
	
	public void update (Contenido contenido)
			throws DataException;	
	
	public void eliminarContenido (Long idContenido) 
			throws DataException;
	
	
	public Contenido buscarId (Long idContenido) 
			throws DataException;

	public Results<Contenido> buscarCriteria(ContenidoCriteria contenido, int startIndex, int count, String idioma)
			throws DataException;

	
	public Results<Contenido> cargarSeguidos (Long idContenido, int startIndex, int count)
			throws DataException;
	
	public Results<Contenido> cargarGuardados (Long idContenido, int startIndex, int count)
			throws DataException;
	
	public List<Contenido> verVideosAutor (Long idAutor)
			throws DataException;
	
	
		
	
	public void asignarCategoria (Long idContenido, Long idCategoria)
			throws DataException;
		
	public void modificarCategoria (Long idContenido, Long idCategoria)
			throws DataException;
	
	public Categoria verCategoria (Long id, String idioma) 
			throws DataException;
	

		
	public Double getValoracion (Long idContenido) 
			throws DataException;	
		
	public void seguirContenido (Long idUsuario, Long idContenido, Boolean siguiendo)
			throws DataException;
	
	public void denunciarContenido (Long idUsuario, Long idContenido, Boolean denunciado)
			throws DataException;
	
	public void cancelarDenuncia (Long idUsuario, Long idContenido, Boolean denunciado)
			throws DataException;
	
	public void valorarContenido (Long idUsuario, Long idContenido, Integer valoracion)
			throws DataException;
	
	public void guardarContenido (Long idUsuario, Long idContenido, Boolean guardado)
			throws DataException;
	
	public void comentarContenido (Long idUsuario, Long idContenido, String comentario)
			throws DataException;
	
	public void borrarComentario (Long idUsuario, Long idContenido)
			throws DataException;

	
}
