package com.isp.seeds.dao;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.dao.impl.VideoDAOImpl;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.criteria.VideoCriteria;

public class TestVideoDAO {

	public static void pruebaUpdate () {
		try {
			

		Connection conexion = ConnectionManager.getConnection();
		VideoDAO dao = new VideoDAOImpl();
		
		Video videoUno = new Video();
		Video video2 = new Video();

		videoUno.setFechaAlta(new java.sql.Date(12));
		videoUno.setFechaMod(new java.sql.Date(12));
		videoUno.setIdAutor(007l);
		videoUno.setNombre("nombre");
		
		videoUno.setDescripcion("descripcion");
		videoUno.setReproducciones(10);
		videoUno.setUrl("url.mp4");
		
		video2 = dao.create(conexion, videoUno);
		System.out.println("IMPRIMIMOS INSERTADO:");
		System.out.println(video2.toString());
		System.out.println("INSERTADO IMPRIMIDO");
		System.out.println("BUSCAMOS EL RECIEN INSERTADO");
		System.out.println(dao.findById(conexion, video2.getIdContenido()));
		System.out.println("AHI QUEDA, AHORA EDITAMOS");
		System.out.println("modificar");
		video2.setDescripcion("he cambiaadoo la descripcion");
		video2.setReproducciones(666);
		video2.setUrl("hecambiadolaurl.mp4");
		dao.update(conexion, video2);
		System.out.println(dao.findById(conexion, video2.getIdContenido()));
		
		System.out.println("1 - BUSCAMOS ALL:");
		List<Video> lista = dao.findAll(conexion);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDAll:");
		for(Video u: lista){System.out.println(u.toString());}
		System.out.println("3 - FIN DE LA IMPRESION");
		System.out.println("BORRAMOS");
		dao.delete(conexion, video2.getIdContenido());
		System.out.println("MOSTRALL");
		lista = dao.findAll(conexion);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDAll:");
		for(Video u: lista){System.out.println(u.toString());}

		JDBCUtils.closeConnection(conexion);
		System.out.println("fin");
		
		} catch (Exception e) {
			System.out.println("EXCEPTION EN TEST");
		}
	}
	
	
	public static void pruebaCriteria () {
		try {

		Connection conexion = ConnectionManager.getConnection();
		VideoDAO dao = new VideoDAOImpl();
		
		VideoCriteria videoUno = new VideoCriteria();
		
//		videoUno.setIdContenido(22l);
//		videoUno.setDescripcion("Sansun");
//		videoUno.setReproducciones(10);
//		videoUno.setUrl("herencia.mp4");
		videoUno.setNombre("cuatro");
		
		System.out.println("1 - BUSCAMOS POR CRITERIA:");
		List<Video> lista = dao.findByCriteria(conexion, videoUno);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDA:");
		for(Video u: lista){System.out.println(u.toString());}
		System.out.println("3 - FIN DE LA IMPRESION");
		
		JDBCUtils.closeConnection(conexion);
		System.out.println("fin");
		
		} catch (Exception e) {
			System.out.println("EXCEPTION EN TEST");
		}
	}

	public static void main(String[] args) {
		
		pruebaCriteria();
		pruebaUpdate();
	}
}
