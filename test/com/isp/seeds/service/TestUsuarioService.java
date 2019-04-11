package com.isp.seeds.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.spi.UsuarioService;

public class TestUsuarioService {

	public static void main(String[] args) {

		UsuarioService usuarioSvc =  new UsuarioServiceImpl();
		
		try {
			
			List<Usuario> lista = new ArrayList<Usuario>();

			System.out.println("____________________________TODOS__________");
			lista= usuarioSvc.buscarTodos(0, 100, "ESP");
			for(Usuario u: lista) {
				System.out.println(u.toString());
			}System.out.println("________________________________________________ FIN TODOS");

			System.out.println("____________________________BUSCA EMAIL: ana1@gmail.com");
			Usuario user = new Usuario();
			user = usuarioSvc.buscarEmail("ana1@gmail.com");
			System.out.println(user.toString());

			System.out.println();

			System.out.println("____________________________CREATE ");
			Usuario usuarioNuevo = new Usuario();

			usuarioNuevo.setFechaAlta(new Date());
			usuarioNuevo.setFechaMod(new Date());
			usuarioNuevo.setNombre("nombre");
			usuarioNuevo.setTipo(1);

			usuarioNuevo.setNombreReal("nombreReal");
			usuarioNuevo.setApellidos("apellidos");
			usuarioNuevo.setFechaNac(new Date());
			usuarioNuevo.setEmail("email");
			usuarioNuevo.setContrasena("contrasena");
			try {
				Connection connection = ConnectionManager.getConnection();
				usuarioNuevo.setPais("es");
				JDBCUtils.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}

			usuarioNuevo = usuarioSvc.crearCuenta(usuarioNuevo);

			user = usuarioSvc.buscarEmail(usuarioNuevo.getEmail());
			System.out.println(user.toString());

			System.out.println();
			System.out.println("____________________________TODOS__________");
			lista= usuarioSvc.buscarTodos(0, 100, "ESP");
			for(Usuario u: lista) {
				System.out.println(u.toString());
			}System.out.println("________________________________________________ FIN TODOS");

			usuarioSvc.eliminarCuenta(user.getId());

			System.out.println();
			System.out.println("____________________________TODOS__________");
			lista= usuarioSvc.buscarTodos(0, 100, "ESP");
			for(Usuario u: lista) {
				System.out.println(u.toString());
			}System.out.println("________________________________________________ FIN TODOS");

			System.out.println("____________________________LOG IN");
			System.out.println(usuarioSvc.logIn("email", "contrasena").toString());
			System.out.println();

			System.out.println();
			System.out.println("____________________________   crear");
			usuarioNuevo = null;
			usuarioNuevo = new Usuario();

			usuarioNuevo.setFechaAlta(new Date());
			usuarioNuevo.setFechaMod(new Date());
			usuarioNuevo.setNombre("nombre");
			usuarioNuevo.setTipo(1);

			usuarioNuevo.setNombreReal("nombreReal");
			usuarioNuevo.setApellidos("apellidos");
			usuarioNuevo.setFechaNac(new Date());
			usuarioNuevo.setEmail("email");
			usuarioNuevo.setContrasena("contrasena");
			
			try {
				Connection connection = ConnectionManager.getConnection();
				usuarioNuevo.setPais("es");
				JDBCUtils.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(usuarioNuevo.getId());

			usuarioNuevo = usuarioSvc.crearCuenta(usuarioNuevo);
			System.out.println(usuarioNuevo.getId());

			System.out.println(usuarioNuevo.toString());
			usuarioNuevo = usuarioSvc.buscarId(usuarioNuevo.getId());
			System.out.println(usuarioNuevo.toString());

			System.out.println("____________________________   modificar");

			usuarioNuevo.setFechaAlta(new Date());
			usuarioNuevo.setFechaMod(new Date());
			usuarioNuevo.setNombre("nombreModificado");

			usuarioNuevo.setEmail("emailModificado");
			usuarioNuevo.setContrasena("contrasenaModificada");
			try {
				Connection connection = ConnectionManager.getConnection();
				usuarioNuevo.setPais("GB");
				JDBCUtils.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			usuarioSvc.editarPerfil(usuarioNuevo);
			usuarioNuevo = usuarioSvc.buscarId(usuarioNuevo.getId());
			System.out.println(usuarioNuevo.toString());
			//System.out.println("____________________________________________");

		} catch (DataException de) {
			de.printStackTrace();
		}
	}

}
