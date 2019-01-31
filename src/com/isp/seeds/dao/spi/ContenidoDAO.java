package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;

public interface ContenidoDAO {
	
	
	
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
	
	
	
}
