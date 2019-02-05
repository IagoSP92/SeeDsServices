package com.isp.seeds.dao;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.dao.impl.ListaDAOImpl;
import com.isp.seeds.dao.spi.ListaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.criteria.ListaCriteria;

public class TestListaDAO {
	
	
	public static void pruebaUpdate () {
		try {

		Connection conexion = ConnectionManager.getConnection();
		ListaDAO dao = new ListaDAOImpl();
		
		Lista listaUno = new Lista();
		Lista lista2 = new Lista();
		listaUno.setFechaAlta(new java.sql.Date(12));
		listaUno.setFechaMod(new java.sql.Date(12));
		listaUno.setIdAutor(007l);
		listaUno.setNombre("nombre");
		listaUno.setTipo(3);
		
		listaUno.setDescripcion("descripcion");
		listaUno.setPublica(true);
		
		lista2 = dao.create(conexion, listaUno);
		System.out.println("-------------------------------------------------------------------");
		System.out.println("IMPRIMIMOS INSERTADO:");
		System.out.println(lista2.toString());
		System.out.println("INSERTADO IMPRIMIDO");
		System.out.println("BUSCAMOS EL RECIEN INSERTADO");
		System.out.println(dao.findById(conexion, lista2.getIdContenido()));
		System.out.println("AHI QUEDA, AHORA EDITAMOS");
		System.out.println("modificar");
		lista2.setDescripcion("he cambiaadoo la descripcion");
		lista2.setPublica(false);
		lista2.setTipo(3);
		dao.update(conexion, lista2);
		System.out.println(dao.findById(conexion, lista2.getIdContenido()));
		
		System.out.println("1 - BUSCAMOS ALL:");
		List<Lista> lista = dao.findAllListas(conexion);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDAll:");
		for(Lista u: lista){System.out.println(u.toString());}
		System.out.println("3 - FIN DE LA IMPRESION");
		System.out.println("BORRAMOS");
		dao.delete(conexion, lista2.getIdContenido());
		System.out.println("MOSTRALL");
		lista = dao.findAllListas(conexion);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDAll:");
		for(Lista u: lista){System.out.println(u.toString());}

		JDBCUtils.closeConnection(conexion);
		System.out.println("fin");
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION EN TEST");
		}
	}
	
	
	public static void pruebaCriteria () {
		try {

		Connection conexion = ConnectionManager.getConnection();
		ListaDAO dao = new ListaDAOImpl();
		
		ListaCriteria listaUno = new ListaCriteria();
		
//		videoUno.setIdContenido(22l);
//		videoUno.setDescripcion("Sansun");
//		videoUno.setReproducciones(10);
//		videoUno.setUrl("herencia.mp4");
//		listaUno.setNombre("cuatro");
		
		System.out.println("1 - BUSCAMOS POR CATEGORIA:");
		List<Lista> lista = dao.findByCategoria(conexion, 2l);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDA:");
		for(Lista u: lista){System.out.println(u.toString());}
		System.out.println("3 - FIN DE LA IMPRESION");
		
		JDBCUtils.closeConnection(conexion);
		System.out.println("fin");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		
		
		pruebaUpdate();
		pruebaCriteria();
	}

}
