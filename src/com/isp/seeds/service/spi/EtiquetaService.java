package com.isp.seeds.service.spi;

import java.sql.SQLException;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Etiqueta;

public interface EtiquetaService {
	
	public Etiqueta findById(Long id, String idioma) 
			throws SQLException, DataException;
	
	public Long findByNombre( String nombreEtiqueta, String idioma) 
			throws SQLException, DataException;

	public List<Etiqueta> findAll( ) 
			throws SQLException, DataException;
	
	public Etiqueta create ( Etiqueta e, String idioma) 
			throws SQLException, DataException;
	
	public void delete (Long idEtiqueta, String idioma) 
			throws SQLException, DataException;

}
