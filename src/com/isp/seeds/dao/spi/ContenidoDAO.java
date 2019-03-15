package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Etiqueta;
import com.isp.seeds.service.criteria.ContenidoCriteria;

public interface ContenidoDAO {
	
	public Boolean exists (Connection connection, Long idContenido) 
			throws DataException;	
	
	
	public Contenido findById(Connection connection, Long idContenido) 
			throws DataException;
	
	public Contenido findByNombre(Connection connection, String nombreContenido) 
			throws DataException;
	
	public List<Contenido> findByCriteria(Connection connection, ContenidoCriteria contenido, int startIndex, int count)
			throws DataException;


	
	
	public List<Contenido> findAll(Connection connection) 
			throws DataException;
	

	Double getValoracion(Connection connection, Long idContenido)
			throws DataException;
	
	
	
	public Contenido create (Connection connection, Contenido contenido)
			throws DataException;
	
	public void update (Connection connection, Contenido contenido)
			throws DataException;
	
	
	public void delete (Connection connection, Long idContenido) 
			throws DataException;
	
	
	
	public void agignarCategoria (Connection connection, Long idContenido, Long idCategoria)
			throws DataException;
	
	public void modificarCategoria (Connection connection,Long idContenido, Long idCategoria)
			throws DataException;
	
	public void asignarEtiqueta (Connection connection, Long idContenido, Long idEtiqueta)
			throws DataException;
	
	public void eliminarEtiqueta (Connection connection, Long idContenido, Long idEtiqueta)
			throws DataException;
	
	
	public Boolean comprobarExistenciaRelacion(Connection connection, Long idUsuario, Long idContenido) 
			throws DataException;
	
	public void crearRelacion(Connection connection, Long idUsuario, Long idContenido) 
			throws DataException;
	
	
	
	public Categoria verCategoria (Connection connection, Long id, String idioma) 
			throws DataException;
	
	public List<Etiqueta> verEtiquetas (Connection connection, Long id, String idioma) 
			throws DataException;
	
	
	
	
	public void seguirContenido (Connection connection, Long idUsuario, Long idContenido, Boolean siguiendo)
			throws DataException;
	
	public void denunciarContenido (Connection connection, Long idUsuario, Long idContenido, String denunciado)
			throws DataException;
	
	public void valorarContenido (Connection connection, Long idUsuario, Long idContenido, Integer valoracion)
			throws DataException;
	
	public void guardarContenido (Connection connection, Long idUsuario, Long idContenido, Boolean guardado)
			throws DataException;
	
	public void comentarContenido (Connection connection, Long idUsuario, Long idContenido, String comentario)
			throws DataException;
	/*
	
	public List<Contenido> verSeguidos (Connection connection, Long id) 
			throws DataException;
	
	public List<Contenido> verDenunciados (Connection connection, Long id) 
			throws DataException;
	
	public List<Contenido> verValorados (Connection connection, Long id) 
			throws DataException;
	
	public List<Contenido> verGuardados (Connection connection, Long id) 
			throws DataException;
	
	public List<Contenido> verComentados (Connection connection, Long id) 
			throws DataException;
	
	
	*/



	
	
}
