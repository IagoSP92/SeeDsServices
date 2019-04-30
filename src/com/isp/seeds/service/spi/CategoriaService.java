package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Categoria;

public interface CategoriaService {
	
	public Categoria findById( Long idCategoria, String idioma) 
			throws DataException;

	public List<Categoria> findAll(String idioma) 
			throws DataException;

}
