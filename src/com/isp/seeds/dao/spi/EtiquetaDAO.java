package com.isp.seeds.dao.utils;

import java.util.List;

import com.isp.seeds.model.Etiqueta;

public interface EtiquetaDAO {
	
	public Etiqueta findById(Long id) 
			throws Exception;


	public List<Etiqueta> findAll() 
			throws Exception;
	
	public Etiqueta create (Etiqueta e) 
			throws Exception;

}
