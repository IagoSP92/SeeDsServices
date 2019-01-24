package com.isp.seeds.dao.utils;

import java.sql.Date;
import java.util.List;

import com.isp.seeds.model.Lista;

public interface ListaDAO {
	
	public List<Lista> findByAutor(Long autor, Boolean publica)
			throws Exception;
	
	public List<Lista> findByCategoria(Long id)
			throws Exception;
	
	public List<Lista> findByNombre(String nombre, Boolean publica) 
			throws Exception;
	
	public List<Lista> findByFechaAlta(Date min, Date max, Boolean publica) 
			throws Exception;
	
	public List<Lista> findByFechaMod(Date min, Date max, Boolean publica) 
			throws Exception;
	
	public List<Lista> findByNombreAlta(String nombre, Date min, Date max, Boolean publica) 
			throws Exception;
	
	
	public String findById (Long id) 
			throws Exception;

	public Lista create (Lista p) 
			throws Exception;
	
	public Boolean update (Lista p) 
			throws Exception;
	
	public void delete (Lista p) 
			throws Exception;
}
