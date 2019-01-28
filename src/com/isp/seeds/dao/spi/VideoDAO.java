package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.model.Video;
import com.isp.seeds.service.VideoCriteria;

public interface VideoDAO {	
	
	
	public Video findById(Connection connection, Long id) 
			throws Exception;
	
	public List<Video> findByCriteria(Connection connection, VideoCriteria video) 
			throws Exception;


	public List<Video> findAll(Connection connection) 
			throws Exception;

	
	public Video create (Connection connection, Video p) 
			throws Exception;
	
	public Boolean update (Connection connection, Video p) 
			throws Exception;
	
	public void delete (Connection connection, Long id) 
			throws Exception;

}
