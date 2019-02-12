package com.isp.seeds.service.spi;

import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Etiqueta;

public interface EtiquetaService {
	
	public Etiqueta findById(Long idEtiqueta, String idioma) 
			throws DataException;
	
	public Etiqueta findByNombre( String nombreEtiqueta, String idioma) 
			throws DataException;

	public List<Etiqueta> findAll( ) 
			throws DataException;
	
	public Etiqueta create ( Etiqueta etiqueta, String idioma) 
			throws DataException;
	
	public void delete (Long idEtiqueta, String idioma) 
			throws DataException;

}
