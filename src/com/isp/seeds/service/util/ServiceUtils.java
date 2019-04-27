package com.isp.seeds.service.util;

import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.impl.VideoDAOImpl;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.VideoDAO;

public class ServiceUtils {
	
	public static VideoDAO videoDao= null;
	public static ContenidoDAO contenidoDao = null;

	static {
		videoDao = new VideoDAOImpl();
		contenidoDao = new ContenidoDAOImpl();
	}

	


}
