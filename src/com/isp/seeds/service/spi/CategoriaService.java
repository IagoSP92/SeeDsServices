package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Categoria;

public interface CategoriaService {
	
	public Categoria findById( Long id, String idioma) 
			throws DataException;
	
	public Long findByNombre( String nombreCategoria, String idioma ) 
			throws DataException;


	public List<Categoria> findAll() 
			throws DataException;

}
