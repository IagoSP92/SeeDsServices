package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Categoria;

public interface CategoriaDAO {

	public Categoria findById(Connection connection, Long id, String idioma) 
			throws DataException;


	public List<Categoria> findAll(Connection connection) 
			throws DataException;

}
