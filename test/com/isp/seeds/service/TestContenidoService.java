package com.isp.seeds.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Etiqueta;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.spi.CategoriaService;
import com.isp.seeds.service.spi.ContenidoService;
import com.isp.seeds.service.spi.EtiquetaService;

public class TestContenidoService {
	

	public static void main(String[] args) {
		ContenidoService contenidoSvc = new ContenidoServiceImpl();
		CategoriaService categoriaSvc = new CategoriaServiceImpl();
		EtiquetaService etiquetaSvc = new EtiquetaServiceImpl();

		
		Contenido contenido1 =new Contenido();
		Contenido contenido2 =new Contenido();
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
			contenido3.setFechaAlta(new Date());
			contenido3.setFechaMod(new Date());
			contenido3.setIdAutor(null);
			contenido3.setNombre("Contenido3lista");
			contenido3.setTipo(3);
			
			System.out.println("____________________________________________ Insertamos 1 ");
			contenido1=contenidoSvc.crearContenido(contenido1);
			System.out.println("____________________________________________ Insertamos 2 ");
			contenido2=contenidoSvc.crearContenido(contenido2);
			System.out.println("____________________________________________ Insertamos 3 ");
			contenido3=contenidoSvc.crearContenido(contenido3);
			System.out.println();
			System.out.println("____________________________________________ Inicio buscarIdTest");
			System.out.println(contenidoSvc.buscarId(contenido1.getIdContenido(), "ESP").toString());
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido(), "ESP").toString());
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido(), "ESP").toString());
			System.out.println("____________________________________________    fin verContenidoTest");
			System.out.println();System.out.println();
			System.out.println("____________________________________________ Inicio modificarNombre1");
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido(), "ESP").toString());
			//contenidoSvc.cambiarNombre(contenido2.getIdContenido(), "Contenido2NombreModificado");
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido(), "ESP").toString());
			System.out.println();
			System.out.println("____________________________________________ Inicio modificarNombre2");
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido(), "ESP").toString());
			contenido3.setNombre("Contenido3lista___Nombre___Modificado");
			contenido3=contenidoSvc.cambiarNombre(contenido3.getIdContenido(), contenido3.getNombre(), "ESP");
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido(), "ESP").toString());
			System.out.println();
			System.out.println("____________________________________________  Asignar categoria");
			contenidoSvc.asignarCategoria(contenido2.getIdContenido(), categoriaSvc.findByNombre("VIDEOCLIPS", "ESP"));
			System.out.println( contenidoSvc.verCategoria(contenido2.getIdContenido(), "ESP")  );
			System.out.println();
			System.out.println("____________________________________________  Modificar categoria");
			contenidoSvc.modificarCategoria(contenido2.getIdContenido(), categoriaSvc.findByNombre("CORTOS", "ESP"));
			System.out.println( contenidoSvc.verCategoria(contenido2.getIdContenido(), "ESP")  );
			System.out.println();System.out.println();
			
			System.out.println("____________________________________________  Asignar etiqueta");
			contenidoSvc.asignarEtiqueta(contenido2.getIdContenido(), etiquetaSvc.findByNombre("Rock", "ESP").getIdEtiqueta());
			contenidoSvc.asignarEtiqueta(contenido2.getIdContenido(), etiquetaSvc.findByNombre("Pop", "ESP").getIdEtiqueta());
			List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
			etiquetas=contenidoSvc.verEtiquetas(contenido2.getIdContenido(), "ESP");
			for(Etiqueta e: etiquetas) {
				System.out.println(e.toString());
			}
			System.out.println();
			System.out.println("____________________________________________  Eliminar etiqueta");
			contenidoSvc.eliminarEtiqueta(contenido2.getIdContenido(), etiquetaSvc.findByNombre("Rock", "ESP").getIdEtiqueta());
			etiquetas = new ArrayList<Etiqueta>();
			etiquetas=contenidoSvc.verEtiquetas(contenido2.getIdContenido(), "ESP");
			for(Etiqueta e: etiquetas) {
				System.out.println(e.toString());
			}
			System.out.println();
			System.out.println();
			
			System.out.println(contenidoSvc.buscarId(contenido1.getIdContenido(), "ESP").toString());
			
			contenidoSvc.eliminarContenido(contenido1.getIdContenido());
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido(), "ESP").toString());
			
			contenidoSvc.eliminarContenido(contenido2.getIdContenido());
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido(), "ESP").toString());
			
			contenidoSvc.eliminarContenido(contenido3.getIdContenido());

			List<Contenido> todos = new ArrayList<Contenido>();
			todos = contenidoSvc.verTodos(5,1,"ESP");
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}
			
			System.out.println();
			System.out.println();	
			System.out.println("____________________________________________  Buscar buscarCriteriaTodo  10-1-01 a 16-1-01");

			ContenidoCriteria contenidoCriteria = new ContenidoCriteria();
			
			Calendar calendar  = Calendar.getInstance();
			Date date = new Date();
			
			calendar.set(2001, Calendar.JANUARY, 10);
			date= calendar.getTime();
			contenidoCriteria.setFechaAlta(date);
			
			calendar.set(2001, Calendar.JANUARY, 16);
			date= calendar.getTime();
			contenidoCriteria.setFechaAltaHasta(date);
			
			
			todos = contenidoSvc.buscarCriteria(contenidoCriteria, 0, 10, "ESP");
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}System.out.println("---------------------------------------------------------------");
			contenidoCriteria= null;
			System.out.println("____________________________________________  Buscar buscarCriteriaTodo  10-1-01 a 16-2-01");
			contenidoCriteria= new ContenidoCriteria();
			
			
			calendar.set(2001, Calendar.JANUARY, 10);
			date= calendar.getTime();
			contenidoCriteria.setFechaMod(date);
			
			calendar.set(2001, Calendar.FEBRUARY, 16);
			date= calendar.getTime();
			contenidoCriteria.setFechaModHasta(date);
			
			

			todos = contenidoSvc.buscarCriteria(contenidoCriteria, 0, 10, "ESP");
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}System.out.println("---------------------------------------------------------------");
			System.out.println("____________________________________________  Buscar buscarCriteriaTodo  al");
			contenidoCriteria= null;
			contenidoCriteria= new ContenidoCriteria();
			
			contenidoCriteria.setNombre("al");
			
			
			todos = contenidoSvc.buscarCriteria(contenidoCriteria, 0, 10, "ESP");
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}System.out.println("---------------------------------------------------------------");
			
			System.out.println("____________________________________________  Buscar buscarCriteriaTodo  al + +");
			contenidoCriteria= null;
			contenidoCriteria= new ContenidoCriteria();
			
			contenidoCriteria.setNombre("al");
			
			calendar.set(2010, Calendar.OCTOBER, 9);
			date= calendar.getTime();
			contenidoCriteria.setFechaAlta(date);
			
			calendar.set(2010, Calendar.OCTOBER, 11);
			date= calendar.getTime();
			contenidoCriteria.setFechaAltaHasta(date);
			
			calendar.set(2010, Calendar.OCTOBER, 9);
			date= calendar.getTime();
			contenidoCriteria.setFechaMod(date);
			
			calendar.set(2010, Calendar.OCTOBER, 11);
			date= calendar.getTime();
			contenidoCriteria.setFechaModHasta(date);
			
			contenidoCriteria.setIdAutor(7l);
			contenidoCriteria.setTipo(2);
			
			
			todos = contenidoSvc.buscarCriteria(contenidoCriteria, 0, 10, "ESP");
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}System.out.println("----------------------------------------------"
					+ "-----------------");
			System.out.println("aa");
			
			
			contenidoCriteria = new ContenidoCriteria();
			
			contenidoCriteria.setNombre("al");
			
			calendar.set(2010, Calendar.OCTOBER, 9);
			date= calendar.getTime();
			contenidoCriteria.setFechaAlta(date);
			
			calendar.set(2010, Calendar.OCTOBER, 12);
			date= calendar.getTime();
			contenidoCriteria.setFechaAltaHasta(date);
			
			calendar.set(2010, Calendar.OCTOBER, 9);
			date= calendar.getTime();
			contenidoCriteria.setFechaMod(date);
			
			calendar.set(2010, Calendar.OCTOBER, 12);
			date= calendar.getTime();
			contenidoCriteria.setFechaModHasta(date);
			
			contenidoCriteria.setIdAutor(7l);
			contenidoCriteria.setTipo(2);
			
			contenidoCriteria.setValoracionMin(5);
			contenidoCriteria.setValoracionMax(7);
			
			contenidoCriteria.setReproduccionesMin(16);
			contenidoCriteria.setReproduccionesMax(18);

			
			todos = contenidoSvc.buscarCriteria(contenidoCriteria, 0, 10, "ESP");
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}System.out.println("---------------------------------------------------------------");
			System.out.println("______________________________________________________________________________________");
			
			
			todos = contenidoSvc.verTodos(0, 10, "ESP");
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}System.out.println("---------------------------------------------------------------");
			System.out.println("______________________________________________________________________________________");
			
			} catch (DataException e) {
				e.printStackTrace();
			}

	}

}
