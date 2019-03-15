package com.isp.seeds.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.spi.ContenidoService;

public class TestCrono {

	public static void main(String[] args) throws DataException {
		
		Logger logger = LogManager.getLogger(TestCrono.class);
		
		ContenidoService contenidoSvc = new ContenidoServiceImpl();

		
		ContenidoCriteria contenidoCriteria = new ContenidoCriteria();
		
		Calendar calendar  = Calendar.getInstance();
		Date date = new Date();
		
		calendar.set(2001, Calendar.JANUARY, 10);
		date= calendar.getTime();
		contenidoCriteria.setFechaAlta(date);
		
		calendar.set(2001, Calendar.JANUARY, 16);
		date= calendar.getTime();
		contenidoCriteria.setFechaAltaHasta(date);
		
		List<Contenido> todos = new ArrayList<Contenido>();

		todos = contenidoSvc.buscarCriteria(contenidoCriteria);
		todos = contenidoSvc.buscarCriteria(contenidoCriteria);
		todos = contenidoSvc.buscarCriteria(contenidoCriteria);
		todos = contenidoSvc.buscarCriteria(contenidoCriteria);

		todos = contenidoSvc.buscarCriteria(contenidoCriteria);

		
//		for(Contenido c : todos) {
//			System.out.println(c.toString());
//		}

	}

}
