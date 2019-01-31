package com.isp.seeds.service.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Etiqueta;

public interface ContenidoService {
	
	public Contenido verContenido (Long idContenido) 
			throws DataException;
	
	
	public Contenido crearContenido (Contenido contenido) 
			throws DataException;
	
	public void eliminarContenido (Long idContenido) 
			throws DataException;
	
	public Contenido cambiarNombre (Contenido contenido)
			throws DataException;
	
	public void cambiarNombre (Long idContenido, String nuevo)
			throws DataException;
	
	
	public void asignarCategoria (Long idContenido, Long idCategoria)
			throws DataException;
	
	public void asignarEtiqueta (Long idContenido, Long idEtiqueta)
			throws DataException;
	
	public void modificarCategoria (Long idContenido, Long idCategoria)
			throws DataException;
	
	public void eliminarEtiqueta (Long idContenido, Long idEtiqueta)
			throws DataException;
	
	public Categoria verCategoria (Long id, String idioma) 
			throws DataException;
	
	public List<Etiqueta> verEtiquetas (Long id, String idioma) 
			throws DataException;
	
	
	public void seguirContenido (Long idUsuario, Long idContenido, Boolean siguiendo)
			throws DataException;
	
	public void denunciarContenido (Long idUsuario, Long idContenido, String denunciado)
			throws DataException;
	
	public void cancelarDenuncia (Long idUsuario, Long idContenido)
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
