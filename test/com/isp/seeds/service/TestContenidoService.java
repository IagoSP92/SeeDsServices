package com.isp.seeds.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Etiqueta;
import com.isp.seeds.service.spi.CategoriaService;
import com.isp.seeds.service.spi.ContenidoService;
import com.isp.seeds.service.spi.EtiquetaService;

public class TestContenidoService {
	
	ContenidoService contenidoSvc = new ContenidoServiceImpl();


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
			System.out.println(contenidoSvc.buscarId(contenido1.getIdContenido()).toString());
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido()).toString());
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido()).toString());
			System.out.println("____________________________________________    fin verContenidoTest");
			System.out.println();System.out.println();
			System.out.println("____________________________________________ Inicio modificarNombre1");
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido()).toString());
			contenidoSvc.cambiarNombre(contenido2.getIdContenido(), "Contenido2NombreModificado");
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido()).toString());
			System.out.println();
			System.out.println("____________________________________________ Inicio modificarNombre2");
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido()).toString());
			contenido3.setNombre("Contenido3lista___Nombre___Modificado");
			contenido3=contenidoSvc.cambiarNombre(contenido3);
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido()).toString());
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
			contenidoSvc.asignarEtiqueta(contenido2.getIdContenido(), etiquetaSvc.findByNombre("Rock", "ESP"));
			contenidoSvc.asignarEtiqueta(contenido2.getIdContenido(), etiquetaSvc.findByNombre("Pop", "ESP"));
			List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
			etiquetas=contenidoSvc.verEtiquetas(contenido2.getIdContenido(), "ESP");
			for(Etiqueta e: etiquetas) {
				System.out.println(e.toString());
			}
			System.out.println();
			System.out.println("____________________________________________  Eliminar etiqueta");
			contenidoSvc.eliminarEtiqueta(contenido2.getIdContenido(), etiquetaSvc.findByNombre("Rock", "ESP"));
			etiquetas = new ArrayList<Etiqueta>();
			etiquetas=contenidoSvc.verEtiquetas(contenido2.getIdContenido(), "ESP");
			for(Etiqueta e: etiquetas) {
				System.out.println(e.toString());
			}
			System.out.println();
			System.out.println();
			
			System.out.println(contenidoSvc.buscarId(contenido1.getIdContenido()).toString());
			
			contenidoSvc.eliminarContenido(contenido1.getIdContenido());
			System.out.println(contenidoSvc.buscarId(contenido2.getIdContenido()).toString());
			
			contenidoSvc.eliminarContenido(contenido2.getIdContenido());
			System.out.println(contenidoSvc.buscarId(contenido3.getIdContenido()).toString());
			
			contenidoSvc.eliminarContenido(contenido3.getIdContenido());

			List<Contenido> todos = new ArrayList<Contenido>();
			todos = contenidoSvc.verTodos();
			for(Contenido c : todos) {
				System.out.println(c.toString());
			}
			
			} catch (DataException e) {
				e.printStackTrace();
			}

	}

}
