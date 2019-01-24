package com.isp.seeds.dao.utils;

import java.util.List;

import com.isp.seeds.model.Pais;

public interface PaisDAO {

	public Pais findById(String id) 
			throws Exception;


	public List<Pais> findAll() 
			throws Exception;
}
