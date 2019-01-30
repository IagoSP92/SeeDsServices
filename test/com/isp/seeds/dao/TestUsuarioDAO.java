package com.isp.seeds.dao;



import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.impl.PaisDAOImpl;
import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Pais;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.criteria.UsuarioCriteria;

public class TestUsuarioDAO {
	
	public static void testContenido (Connection conexion, Contenido c) {
		
		ContenidoDAO daoc = new ContenidoDAOImpl();
		
		System.out.println(c.toString());
		
		try {
		Contenido cont = daoc.create(conexion, c);
		System.out.println(cont.toString());
		}catch (Exception e) {
			System.out.println("Exception");
		}
	}
	
	public static void testCreateUsuario (Connection conexion, UsuarioDAO dao, Usuario u) {
		
		try {

		System.out.println(u.toString());
		u = dao.create(conexion, u);
		System.out.println(u.toString());
		
		}
		catch (Exception e) {  
			System.out.println("Exception");
		}
	}
	
	public static void testFind (Connection conexion, UsuarioDAO dao, Long id) {
		
		try {
			
			Usuario dos = null;
			
			dos= dao.findById(conexion, id);
			
			System.out.println(dos.toString());
			
		}
		catch (Exception e) {  
			System.out.println("Exception FindId");
		}
	}
	
	
	public static void testDelete (Connection conexion, UsuarioDAO dao, Long id) {
		
		try {
			
			//System.out.println(dao.findById(conexion, id).toString());
			System.out.println("BORRAR: "+id);
			
			dao.delete(conexion, id);
			
			System.out.println("BORRRADO: "+id);
			
		}
		catch (Exception e) {  
			System.out.println("Exception: Probablemente non exista o que quixeche borrar");
		}
	}
	
	
	public static void pruebaUpdate () {
		try {

		Connection conexion = ConnectionManager.getConnection();
		UsuarioDAO dao = new UsuarioDAOImpl();
		
		Usuario jamele = new Usuario();
		Usuario jamele2 = new Usuario();
		
		Pais pais = new Pais();
		pais.setIdPais("AD");
		pais.setNombrePais("ANDORRA");

		jamele.setFechaAlta(new java.sql.Date(12));
		jamele.setFechaMod(new java.sql.Date(12));
		jamele.setIdAutor(null);
		
		jamele.setApellidos("apellidos");
		jamele.setAvatarUrl("avatarUrl");
		jamele.setContrasena("contrasena");
		jamele.setDescripcion("descripcion");
		jamele.setEmail("email");
		jamele.setNombre("nombre");
		jamele.setNombreReal("nombreReal");
		jamele.setPais(pais);
		jamele.setFechaNac(new java.sql.Date(12));

		jamele2 = dao.create(conexion, jamele);
		System.out.println("IMPRIMIMOS INSERTADO:");
		System.out.println(jamele2.toString());
		System.out.println("INSERTADO IMPRIMIDO");
		System.out.println("BUSCAMOS EL RECIEN INSERTADO");
		System.out.println(dao.findById(conexion, jamele2.getIdContenido()));
		System.out.println("AHI QUEDA, AHORA EDITAMOS");
		
		System.out.println("modificar");
		jamele2.setApellidos("aaaaaaaaaaaaaa");
		jamele2.setAvatarUrl("bbbbbbbbbbbbbb");
		jamele2.setContrasena("cccccccccccc");
		jamele2.setDescripcion("dddddddddddddd");
		jamele2.setEmail("eeeeeeeeeee");
		jamele2.setFechaNac(new Date());
		
		dao.update(conexion, jamele2);
		System.out.println(dao.findById(conexion, jamele2.getIdContenido()));
		
		
		System.out.println("1 - BUSCAMOS ALL:");
		List<Usuario> lista = dao.findAll(conexion);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDAll:");
		for(Usuario u: lista){System.out.println(u.toString());}
		System.out.println("3 - FIN DE LA IMPRESION");
		System.out.println("BORRAMOS");
		dao.delete(conexion, jamele2.getIdContenido());
		System.out.println("MOSTRALL");
		lista = dao.findAll(conexion);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDAll:");
		for(Usuario u: lista){System.out.println(u.toString());}

		JDBCUtils.closeConnection(conexion);
		System.out.println("fin");
		
		} catch (Exception e) {
			System.out.println("EXCEPTION EN TEST");
		}
	}
	
	
	public static void pruebaCriteria () {
		try {

		Connection conexion = ConnectionManager.getConnection();
		UsuarioDAO dao = new UsuarioDAOImpl();
		
		UsuarioCriteria dos = new UsuarioCriteria();
		
	//	dos.setIdContenido(5l);
//		dos.setEmail("ana1@gmail.com");
//		dos.setDescripcion("Hola! Disfruten de mis videos!");
//		dos.setAvatarUrl("C:img.jpg");
//		dos.setNombreReal("ana");
//		dos.setApellidos("LEDO PIÑEIRO");
		
		PaisDAO daop = new PaisDAOImpl();
		dos.setPais(daop.findById(conexion, "ES"));
		
		System.out.println("1 - BUSCAMOS POR CRITERIA:");
		List<Usuario> lista = dao.findByCriteria(conexion, dos);
		System.out.println("2 - IMPRIMIMOS RESULTADOS BUSQUEDA:");
		for(Usuario u: lista){System.out.println(u.toString());}
		System.out.println("3 - FIN DE LA IMPRESION");
		

		JDBCUtils.closeConnection(conexion);
		System.out.println("fin");
		
		} catch (Exception e) {
			System.out.println("EXCEPTION EN TEST");
		}
	}
	

	public static void main(String[] args) throws Exception {
		
		//pruebaCriteria();
		pruebaUpdate();

	}

}
