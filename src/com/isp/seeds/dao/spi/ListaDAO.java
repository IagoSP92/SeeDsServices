package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.util.Results;

public interface ListaDAO {
	
	
	public Lista findById (Connection connection, Long idSesion, Long idLista) 
			throws DataException;

	public Lista create (Connection connection, Lista lista) 
			throws DataException;
	
	public void update (Connection connection, Lista lista) 
			throws DataException;
	
	public void delete (Connection connection, Long idLista) 
			throws DataException;
	
	public void redefinirIncluidos(Connection connection, Long idLista, List<Long> listIdVideo)
			throws DataException;
	

	public Results<Contenido> cargarGuardados(Connection connection, Long idSesion,
			int startIndex, int count) throws DataException;
	
	public Results<Contenido> cargarSeguidos(Connection connection, Long idSesion,
			int startIndex, int count) throws DataException;	
	
	
	
	/********** FUNCIONES DE UTILIDAD NO EMPLEADAS ACTUALMENTE       ********/
	
	public void insertInList (Connection connection, Long idLista, Long idVideo, Integer posicion)
			throws DataException, SQLException;
	
	public void deleteFromList (Connection connection, Long idLista, Long idVideo)
			throws DataException, SQLException;

}
