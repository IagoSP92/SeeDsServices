package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Etiqueta;

public interface EtiquetaDAO {
	
	public Boolean exists (Connection connection, Long idEtiqueta, String idioma) 
			throws DataException;
	
	public Etiqueta findById(Connection conexion, Long id, String idioma) 
			throws SQLException, DataException;
	
	public Long findByNombre(Connection conexion, String nombreEtiqueta, String idioma) 
			throws SQLException, DataException;

	public List<Etiqueta> findAll(Connection conexion) 
			throws SQLException, DataException;
	
	public Etiqueta create (Connection conexion, Etiqueta e, String idioma) 
			throws SQLException, DataException;
	
	public void delete (Connection conexion, Long idEtiqueta, String idioma) 
			throws SQLException, DataException;
	
	

}
