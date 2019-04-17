package com.isp.seeds.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.spi.CategoriaService;
import com.isp.seeds.service.spi.ContenidoService;
import com.isp.seeds.service.spi.EtiquetaService;
import com.isp.seeds.service.spi.VideoService;
import com.isp.seeds.service.util.Results;

public class TestBusqueda {

	public static void main(String[] args) {
		ContenidoService contenidoSvc = new ContenidoServiceImpl();
		CategoriaService categoriaSvc = new CategoriaServiceImpl();
		EtiquetaService etiquetaSvc = new EtiquetaServiceImpl();
		VideoService videoSvc = new VideoServiceImpl();
		String idioma = "ES";

		
		Contenido contenido1 =new Contenido();
		Video contenido2 =new Video();
		Contenido contenido3 =new Contenido();
		
		
		try {
			
			contenido1.setFechaAlta(new Date());
			contenido1.setFechaMod(new Date());
			contenido1.setIdAutor(null);
			contenido1.setNombre("Contenido1Persona");
			contenido1.setTipo(1);
			
			contenido2.setFechaAlta(new Date());
			contenido2.setFechaMod(new Date());
			contenido2.setIdAutor(null);
			contenido2.setNombre("Contenido2video");
			contenido2.setTipo(2);
			//videoSvc.
			contenido2.setReproducciones(50);
			contenido2.setValoracion(5d);// ESTO É DE PRUEBA PA BUSCAR, NON SE SACA ASI
			
			
			contenido3.setFechaAlta(new Date());
			contenido3.setFechaMod(new Date());
			contenido3.setIdAutor(null);
			contenido3.setNombre("Contenido3lista");
			contenido3.setTipo(3);
			
			System.out.println("____________________________________________ Insertamos 1 ");
			contenido1=contenidoSvc.crearContenido(contenido1);
			System.out.println("____________________________________________ Insertamos 2 ");
			contenido2=videoSvc.crearVideo(contenido2);
			System.out.println("____________________________________________ Insertamos 3 ");
			contenido3=contenidoSvc.crearContenido(contenido3);
			System.out.println();
			System.out.println("____________________________________________ Inicio buscarIdTest");
			System.out.println(contenidoSvc.buscarId(contenido1.getId(), "ES").toString());
			System.out.println(contenidoSvc.buscarId(contenido2.getId(), "ES").toString());
			System.out.println(contenidoSvc.buscarId(contenido3.getId(), "ES").toString());
			System.out.println("____________________________________________    fin verContenidoTest");
			System.out.println();System.out.println();
			
			ContenidoCriteria cc = new ContenidoCriteria();
			//cc.setNombre("Contenido2video");
			cc.setReproduccionesMax(51);
			cc.setReproduccionesMin(49);
			
			List<Contenido> page = new ArrayList<Contenido>();    
			Results<Contenido> resultados = null;
			
			resultados= contenidoSvc.buscarCriteria(cc, 0, 50, idioma);
			
			System.out.println(resultados.getTotal());
			for(Contenido v:resultados.getPage()) {
				System.out.println(v.toString());
				//System.out.println("reps: "+((Video) v).getReproducciones());
			}
			
			
			
			} catch (DataException e) {
				e.printStackTrace();
			}

	}

}
