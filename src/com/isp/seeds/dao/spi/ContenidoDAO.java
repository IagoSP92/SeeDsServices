package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.util.Results;

public interface ContenidoDAO {
	
	public Boolean exists (Connection connection, Long idContenido) 
			throws DataException;	
	
	public Contenido create (Connection connection, Contenido contenido)
			throws DataException;
	
	public void update (Connection connection, Contenido contenido)
			throws DataException;	
	
	public void delete (Connection connection, Long idContenido) 
			throws DataException;	

	
	
	public Contenido findById(Connection connection, Long idContenido) 
			throws DataException;	
	
	public Results<Contenido> findByCriteria(Connection connection, ContenidoCriteria contenido, int startIndex, int count, String idioma)
			throws DataException;	
	
	public Results<Contenido> findAll(Connection connection, int startIndex, int count, String idioma) 
			throws DataException;	
 
	
	public Results<Contenido> cargarSeguidos (Connection connection, Long idContenido, int startIndex, int count)
			throws DataException;
	
	public Results<Contenido> cargarGuardados (Connection connection, Long idContenido, int startIndex, int count)
			throws DataException;
	
	public List<Contenido> verVideosAutor (Connection connection, Long idAutor)
			throws DataException;
	
		

		
	
	public void agignarCategoria (Connection connection, Long idContenido, Long idCategoria)
			throws DataException;
	
	public void modificarCategoria (Connection connection,Long idContenido, Long idCategoria)
			throws DataException;
	
	
	public Boolean comprobarExistenciaRelacion(Connection connection, Long idUsuario, Long idContenido) 
			throws DataException;
	
	public void crearRelacion(Connection connection, Long idUsuario, Long idContenido) 
			throws DataException;	
	
	public Categoria verCategoria (Connection connection, Long id, String idioma) 
			throws DataException;
	
	
		

	public Double getValoracion(Connection connection, Long idContenido)
			throws DataException;	
	
	public void seguirContenido (Connection connection, Long idUsuario, Long idContenido, Boolean siguiendo)
			throws DataException;
	
	public void denunciarContenido (Connection connection, Long idUsuario, Long idContenido, Boolean denunciado)
			throws DataException;
	
	public void valorarContenido (Connection connection, Long idUsuario, Long idContenido, Integer valoracion)
			throws DataException;
	
	public void guardarContenido (Connection connection, Long idUsuario, Long idContenido, Boolean guardado)
			throws DataException;
	
	public void comentarContenido (Connection connection, Long idUsuario, Long idContenido, String comentario)
			throws DataException;
}
