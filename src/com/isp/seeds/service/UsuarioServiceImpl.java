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
import com.isp.seeds.dao.utils.SingleDAO;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.spi.UsuarioService;
import com.isp.seeds.service.util.Results;

public class UsuarioServiceImpl implements UsuarioService {

	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

	@Override
	public void recuperarContraseņa(String email) throws DataException {
	}

	public Usuario crearCuenta (Usuario u) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Usuario= {} ", u);
		}
		
		Connection connection = null;
		boolean commit = false;

		if(u != null) {
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				u = SingleDAO.usuarioDao.create(connection, u);				
				commit=true;
				return u;
			}
			catch (SQLException e) { 
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				logger.warn(e.getMessage(), e);
			}finally {
				JDBCUtils.closeConnection(connection, commit);
			}
		}			
		return null;
	}

	@Override
	public void editarPerfil(Usuario usuario) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Usuario= {} ", usuario);
		}
		Connection connection = null;
		boolean commit = false;
		if(usuario!=null) {
			try {				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				SingleDAO.usuarioDao.update(connection, usuario);				
				commit=true;
				
			}
			catch (SQLException e) {  
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				logger.warn(e.getMessage(), e);
			} finally {
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}

	public void eliminarCuenta (Long idUsuario) {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} ", idUsuario);
		}

		if(idUsuario!=null) {
			try {
				Connection connection = ConnectionManager.getConnection();
				SingleDAO.usuarioDao.delete(connection, idUsuario);
				JDBCUtils.closeConnection(connection);
			}
			catch (SQLException e) { 
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) { 
				logger.warn(e.getMessage(), e);
			}
		}

	}

	@Override
	public Usuario logIn (String email, String contrasena) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} contrasena= {} ", email, contrasena==null);
		}
		Connection connection = null;
		Usuario usuario = null;
		if(email!=null && contrasena!=null) {
			
			try {
				connection = ConnectionManager.getConnection();
				if(SingleDAO.usuarioDao.verificarContrasena(connection, email, contrasena)) {
					usuario = SingleDAO.usuarioDao.findByEmail(connection, email);					
				}
			}
			catch (SQLException e) {  
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				logger.warn(e.getMessage(), e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
		return usuario;
	}
	

	@Override
	public void cambiarContraseņa(String email, String contrasena) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} contrasena= {} ", email, contrasena==null);
		}

		if(email!=null && contrasena!=null) {
			try {
				Connection connection = ConnectionManager.getConnection();
				Usuario usuario = buscarEmail(email);
				usuario.setContrasena(contrasena);
				SingleDAO.usuarioDao.update(connection, usuario);
				JDBCUtils.closeConnection(connection);
			}
			catch (SQLException e) {  
				logger.warn(e.getMessage(), e);
			}
			catch (Exception e) {  
				e.printStackTrace();
			}
		}

	}


	@Override
	public Usuario buscarEmail(String email) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} ", email);
		}

		Usuario usuario = null;
		if(email != null) {

			try {
				Connection connection = ConnectionManager.getConnection();
				usuario = SingleDAO.usuarioDao.findByEmail(connection, email);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return usuario;
	}

	@Override
	public Usuario buscarId(Long idSesion, Long idUsuario) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} ", idUsuario);
		}
		
		Usuario usuario = null;
		if(idUsuario != null) {			
			try {
				Connection connection = ConnectionManager.getConnection();
				usuario = SingleDAO.usuarioDao.findById(connection, idSesion, idUsuario);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return usuario;
	}

	
	@Override
	public Results<Contenido> cargarSeguidos(Long idSesion, int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion= {} ", idSesion);
		}

		if(idSesion != null) {

			try {
				Connection connection = ConnectionManager.getConnection();

				Results<Contenido> contenidos = SingleDAO.usuarioDao.cargarSeguidos(connection, idSesion, startIndex, count);
				JDBCUtils.closeConnection(connection);

				return contenidos;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return null;
	}

//	@Override
//	public List<Usuario> buscarTodos(int startIndex, int count, String idioma) throws DataException {
//
//		// LOGGER
//		
//		try {
//
//			Connection connection = ConnectionManager.getConnection();
//
//			List<Usuario> usuarios = new ArrayList<Usuario>();
//			usuarios = usuarioDao.findAllUsers(connection, startIndex, count, idioma);
//
//			JDBCUtils.closeConnection(connection);
//
//			return usuarios;
//
//		} catch (SQLException e) {
//			logger.warn(e.getMessage(), e);
//		} catch (DataException e) {
//			logger.warn(e.getMessage(), e);
//		}finally{
//			//JDBCUtils.closeConnection(connection);
//		}
//		return null;
//	}


}
