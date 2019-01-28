package com.isp.seeds.dao.utils;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.model.Pais;

public interface PaisDAO {

	public Pais findById(Connection c, String id) 
			throws Exception;


	public List<Pais> findAll(Connection c) 
			throws Exception;
}
