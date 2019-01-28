package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.UsuarioDAO;
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
