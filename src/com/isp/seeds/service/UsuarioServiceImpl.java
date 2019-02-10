package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.spi.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {
	
	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);
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
		

		
	}

	public Usuario crearCuenta (Usuario u) {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Usuario= {} ", u);
		}

		try {
			Connection connection = ConnectionManager.getConnection();
			u = usuarioDao.create(connection, u);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException e) { 
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) {  
			logger.warn(e.getMessage(), e);
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
	
	public void eliminarCuenta (Usuario u) {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Usuario= {} ", u);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();  // if usuario not null Excepcion????????????????
			usuarioDao.delete(connection, u.getIdContenido());
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException e) { 
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) { 
			logger.warn(e.getMessage(), e);
		}
		
	}

	@Override
	public Usuario logIn (String email, String contrasena, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} contrasena= {} idioma= {}", email, contrasena==null, idioma);
		}
		
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
		catch (SQLException e) {  
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) {  
			logger.warn(e.getMessage(), e);
		}
		
		return usuario;
	}



	@Override
	public void editarPerfil(Usuario usuario, String idioma) throws DataException { //Podria non necesitar idioma
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Usuario= {} idioma= {} ", usuario, idioma);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();
			usuarioDao.update(connection, usuario, idioma);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException e) {  
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) {  
			e.printStackTrace();
		}
	}

	@Override
	public void cambiarContraseña(String email, String contrasena, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} contrasena= {} idioma= {}", email, contrasena==null, idioma);
		}
		
		try {
			Connection connection = ConnectionManager.getConnection();
			Usuario usuario = buscarEmail(email, idioma);
			usuario.setContrasena(contrasena);
			usuarioDao.update(connection, usuario, idioma);
			JDBCUtils.closeConnection(connection);
		}
		catch (SQLException e) {  
			logger.warn(e.getMessage(), e);
		}
		catch (Exception e) {  
			e.printStackTrace();
		}
		
	}


	@Override
	public Usuario buscarEmail(String email, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} idioma= {}", email, idioma);
		}

		try {
			if(email != null) {
				Connection connection = ConnectionManager.getConnection();

				Usuario usuario = new Usuario();
				usuario = usuarioDao.findByEmail(connection, email, idioma);

				JDBCUtils.closeConnection(connection);

				return usuario;
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

	@Override
	public Usuario buscarId(Long idUsuario, String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idioma= {}", idUsuario, idioma);
		}
		
		try {
			if(idUsuario != null && idioma!=null) {
				Connection connection = ConnectionManager.getConnection();
				Usuario usuario = new Usuario();
				usuario = usuarioDao.findById(connection, idUsuario, idioma);

				JDBCUtils.closeConnection(connection);

				return usuario;
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}
	
	
	@Override
	public List<Usuario> buscarTodos(String idioma) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idioma= {}", idioma);
		}
		
		try {
			if(idioma != null) {
				Connection connection = ConnectionManager.getConnection();

				List<Usuario> usuarios = new ArrayList<Usuario>();
				usuarios = usuarioDao.findAllUsers(connection, idioma);

				JDBCUtils.closeConnection(connection);

				return usuarios;
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}


}
