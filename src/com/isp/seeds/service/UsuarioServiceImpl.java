package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.model.Usuario;

public class UsuarioService {

	public Usuario registro (Usuario u) {

		try {
			Connection conexion = ConnectionManager.getConnection();
			UsuarioDAO dao = new UsuarioDAOImpl();
			u = dao.create(conexion, u);
		}
		catch (SQLException se) {  
			System.out.println("SQLException");
		}
		catch (Exception e) {  
			System.out.println("Exception");
		}
		// EN SERVICIO :
//		private List<Video> videosSubidos= null;
//		private List<Lista> listasSubidas= null;
//		private List<Usuario> usuariosSeguidos= null;
//		private List<Lista> listasSeguidas= null;
//		private List<Video> videosGuardados = null;
//		private List<Lista> listasGuardadas = null;

		return u;
	}
	
	public Usuario eliminarCuenta (Usuario u) {

		try {

			Connection conexion = ConnectionManager.getConnection();
			UsuarioDAO dao = new UsuarioDAOImpl();
			dao.delete(conexion, u.getIdContenido());

		}
		catch (SQLException se) {  
			System.out.println("SQLException");
		}
		catch (Exception e) {  
			System.out.println("Exception");
		}

		return u;
	}

}
