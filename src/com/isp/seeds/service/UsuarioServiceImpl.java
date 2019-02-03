package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.spi.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {
	
	UsuarioDAO usuarioDao= null;
	
	public UsuarioServiceImpl () {
		usuarioDao = new UsuarioDAOImpl();
	}
	
	@Override
	public Boolean logOut(String email, String contrasena) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void recuperarContraseña(String email) throws DataException {
		// TODO Auto-generated method stub
		
	}

	public Usuario crearCuenta (Usuario u) {

		try {
			Connection connection = ConnectionManager.getConnection();
			u = usuarioDao.create(connection, u);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException se) { 
			se.printStackTrace();
		}
		catch (Exception e) {  
			e.printStackTrace();
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
			Connection connection = ConnectionManager.getConnection();
			usuarioDao.delete(connection, u.getIdContenido());
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException se) { 
			se.printStackTrace();
		}
		catch (Exception e) { 
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public Usuario logIn (String email, String contrasena, String idioma) throws DataException {
		Usuario usuario = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			if(email!=null && contrasena!=null && idioma !=null) {
				if(usuarioDao.verificarContrasena(connection, email, contrasena)) {
					usuario = usuarioDao.findByEmail(connection, email, idioma);
				}
			}
			JDBCUtils.closeConnection(connection);
			if (usuario==null) {
				throw new DataException("Contraseña incorrecta");
			}
		}
		catch (SQLException se) {  
			se.printStackTrace();
		}
		catch (Exception e) {  
			e.printStackTrace();
		}
		
		return usuario;
	}



	@Override
	public void editarPerfil(Usuario usuario, String idioma) throws DataException { //Podria non necesitar idioma
		try {
			Connection connection = ConnectionManager.getConnection();
			usuarioDao.update(connection, usuario, idioma);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException se) {  
			se.printStackTrace();
		}
		catch (Exception e) {  
			e.printStackTrace();
		}
	}

	@Override
	public void cambiarContraseña(String email, String contrasena, String idioma) throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			Usuario usuario = buscarEmail(email, idioma);
			usuario.setContrasena(contrasena);
			usuarioDao.update(connection, usuario, idioma);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException se) {  
			se.printStackTrace();
		}
		catch (Exception e) {  
			e.printStackTrace();
		}
		
	}


	@Override
	public Usuario buscarEmail(String email, String idioma) throws DataException {

		try {
			if(email != null) {
				Connection connection = ConnectionManager.getConnection();

				Usuario usuario = new Usuario();
				usuario = usuarioDao.findByEmail(connection, email, idioma);

				JDBCUtils.closeConnection(connection);

				return usuario;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public List<Usuario> buscarTodos(String idioma) throws DataException {
		try {
			if(idioma != null) {
				Connection connection = ConnectionManager.getConnection();

				List<Usuario> usuarios = new ArrayList<Usuario>();
				usuarios = usuarioDao.findAllUsers(connection, idioma);

				JDBCUtils.closeConnection(connection);

				return usuarios;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

}
