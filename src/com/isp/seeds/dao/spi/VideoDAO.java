package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.criteria.VideoCriteria;

public interface VideoDAO {	
	
	
	public Video findById(Connection connection, Long id) 
			throws DataException;
	
	public List<Video> findByCriteria(Connection connection, VideoCriteria video) 
			throws DataException;


	public List<Video> findAll(Connection connection) 
			throws DataException;
	
	public int getReproducciones (Connection connection, Long idContenido) 
			throws DataException;

	
	public Video create (Connection connection, Video p) 
			throws DataException;
	
	public void update (Connection connection, Video p) 
			throws DataException;
	
	public long delete (Connection connection, Long id) 
			throws DataException;

}
