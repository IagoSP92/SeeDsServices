package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Pais;

public interface PaisDAO {

	public Pais findById(Connection connection, String idPais, String idioma) 
			throws DataException;

	public List<Pais> findAll(Connection connection, String idioma) 
			throws DataException;
}
