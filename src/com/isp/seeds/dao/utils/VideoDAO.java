package com.isp.seeds.dao.utils;

import java.sql.Date;
import java.util.List;

import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;

public interface VideoDAO {
	
	public List<Video> findByUsuario(Long id)
			throws Exception;
	
	public List<Video> findByCategoria(Long id) 
			throws Exception;
	
	public List<Video> findByNombre(String nombre) 
			throws Exception;
	
	public List<Video> findByFechaAlta(Date min, Date max) 
			throws Exception;
	
	public List<Video> findByFechaMod(Date min, Date max) 
			throws Exception;
	
	public List<Video> findByAutor(Long autor) 
			throws Exception;
	
	public List<Video> findByNombreAlta(String nombre, Date min, Date max) 
			throws Exception;
	
	


	public List<Video> findAll() 
			throws Exception;

	public Video findById(Long id) 
			throws Exception;
	
	public Video create (Video p) 
			throws Exception;
	
	public Boolean update (Video p) 
			throws Exception;
	
	public void delete (Video p) 
			throws Exception;

}
