package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Etiqueta;

public interface EtiquetaDAO {
	
	public Etiqueta findById(Connection conexion, Long id) 
			throws SQLException, DataException;

	public List<Etiqueta> findAll(Connection conexion) 
			throws SQLException, DataException;
	
	public Etiqueta create (Connection conexion, Etiqueta e) 
			throws SQLException, DataException;

}
