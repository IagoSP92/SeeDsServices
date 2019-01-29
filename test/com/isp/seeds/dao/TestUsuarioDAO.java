package com.isp.seeds.dao;



import java.sql.Connection;
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
			System.out.println("Exception");
		}
	}
	
	public static void testFindCriteria (Connection conexion, UsuarioDAO dao){
		
		try {
			
			UsuarioCriteria dos = new UsuarioCriteria();
			
			dos.setIdContenido(5l);
			dos.setEmail("ana1@gmail.com");
			dos.setDescripcion("Hola! Disfruten de mis videos!");
			dos.setAvatarUrl("C:img.jpg");
			dos.setNombreReal("ana");
			dos.setApellidos("LEDO PIÑEIRO");
			
			PaisDAO daop = new PaisDAOImpl();
			dos.setPais(daop.findById(conexion, "ES"));
			
			
			List<Usuario> lista = dao.findByCriteria(conexion, dos);
			
			for(Usuario u: lista) {
				System.out.println(u.toString());
			}
		}
		catch (Exception e) {  
			System.out.println("Exception");
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
	
	

	public static void main(String[] args) throws Exception {
		
		Connection conexion = ConnectionManager.getConnection();
		UsuarioDAO dao = new UsuarioDAOImpl();
		
		Usuario c = new Usuario();
		
		Pais pais = new Pais();
		pais.setIdPais("AD");
		pais.setNombrePais("ANDORRA");
		
		c.setNombre("unouno");
		c.setFechaAlta(new java.sql.Date(12) );
		c.setFechaMod(new java.sql.Date(12) );
		c.setIdAutor(null);
		
		c.setNombreReal("realUnouno");
		c.setEmail("unouno@talicual.com");
		c.setContrasena("abcde");
		c.setDescripcion("descripcion");
		c.setApellidos("apellidoUnouno");
		c.setAvatarUrl("/tata/tata/avatarUNo");
		c.setPais(pais);
		
		//testFind (conexion, dao, 1l);
		
		//testCreateContenido ( conexion,  dao, c);
		
		//testContenido ( conexion, c);
		
		//testDelete ( conexion,dao, 60l);
		//dao.delete(conexion, 58l);
		
		//testCreateUsuario ( conexion, dao, c);
		
		testFindCriteria(conexion, dao);


		JDBCUtils.closeConnection(conexion);
	}

}
