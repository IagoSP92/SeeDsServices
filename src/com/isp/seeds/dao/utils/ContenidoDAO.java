package com.isp.seeds.dao.utils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.isp.seeds.model.Contenido;

public interface ContenidoDAO {
	
	
	
	public Contenido findById(Long id) 
			throws Exception;
	
	/*
	public List<Contenido> findByNombre(String nombre) 
			throws Exception;
	
	public List<Contenido> findByFechaAlta(Date min, Date max) 
			throws Exception;
	
	public List<Contenido> findByFechaMod(Date min, Date max) 
			throws Exception;
	
	public List<Contenido> findByAutor(Long autor) 
			throws Exception;
	
	public List<Contenido> findByNombreAlta(String nombre, Date min, Date max) 
			throws Exception;
	*/



	public List<Contenido> findByCriteria(Connection connection, Contenido criteria)
			throws Exception;
	
	
	public List<Contenido> findAll(Connection connection) 
			throws Exception;
	
	public Contenido create (Connection connection, Contenido c)
			throws Exception;
	
	
}
