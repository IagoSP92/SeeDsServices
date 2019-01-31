package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;

public interface ContenidoDAO {
	
	public Boolean exists (Connection connection, Long idContenido) 
			throws DataException;
	
	public Boolean existsCategoria (Connection connection, Long idCategoria) 
			throws DataException;
	
	public Boolean existsEtiqueta (Connection connection, Long idEtiqueta) 
			throws DataException;
	
	
	
	public Contenido findById(Connection connection, Contenido contenido, Long id) 
			throws DataException;

	public List<Contenido> findByCriteria(Connection connection, ContenidoCriteria contenido)
			throws DataException;
	
	public List<Contenido> findAll(Connection connection) 
			throws DataException;
	
	
	
	public Contenido create (Connection connection, Contenido contenido)
			throws DataException;
	
	public void update (Connection connection, Contenido contenido)
			throws DataException;
	
	
	public long delete (Connection connection, Long id) 
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
	
	
	
	public void seguirContenido (Connection connection, Long idUsuario, Long idContenido, Boolean siguiendo)
			throws DataException;
	
	public void denunciarContenido (Connection connection, Long idUsuario, Long idContenido, String denunciado)
			throws DataException;
	
	public void valorarContenido (Connection connection, Long idUsuario, Long idContenido, int valoracion)
			throws DataException;
	
	public void guardarContenido (Connection connection, Long idUsuario, Long idContenido, Boolean guardado)
			throws DataException;
	
	public void comentarContenido (Connection connection, Long idUsuario, Long idContenido, String comentario)
			throws DataException;
	
}
