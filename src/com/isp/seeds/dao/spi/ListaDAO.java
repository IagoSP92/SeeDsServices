package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Lista;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.criteria.ListaCriteria;

public interface ListaDAO {
	
	
	public Lista findById (Connection connection, Long idLista) 
			throws DataException;
	
	public List <Lista>  findByAutor (Connection connection, Long idAutor) 
			throws DataException;
	
	public List <Lista>  findByCategoria (Connection connection, Long idCategoria) 
			throws DataException;

	
//	public List <Lista> findByCriteria(Connection connection, ListaCriteria lista) 
//			throws DataException;
	

	public List <Lista> findAllListas(Connection connection) 
			throws DataException;
	

	public Lista create (Connection connection, Lista lista) 
			throws DataException;
	
	public void update (Connection connection, Lista lista) 
			throws DataException;
	
	public void delete (Connection connection, Long idLista) 
			throws DataException;
	
	
	
	public void insertInList (Connection connection, Long idLista, Long idVideo, int posicion)
			throws DataException, SQLException;
	
	public void deleteFromList (Connection connection, Long idLista, Long idVideo)
			throws DataException, SQLException;

	
}
