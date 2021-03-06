package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Pais;

public interface PaisService {
	
	public Pais findById( String idPais, String idioma) 
			throws DataException;

	public List<Pais> findAll(String idioma) 
			throws DataException;

}
