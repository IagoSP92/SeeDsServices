package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.spi.MailService;
import com.isp.seeds.service.spi.UsuarioService;
import com.isp.seeds.service.util.Results;

public class UsuarioServiceImpl implements UsuarioService {

	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);
	
	private UsuarioDAO usuarioDao = null;
	
	public UsuarioServiceImpl() {
		usuarioDao = new UsuarioDAOImpl();		
	}
	
	
	public Usuario crearCuenta (Usuario u) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Usuario= {} ", u);
		}
		
		Connection connection = null;
		MailService mailService = null;
		boolean commit = false;

		if(u != null) {
			try {				
				connection = ConnectionManager.getConnection();
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				connection.setAutoCommit(false);				
				u = usuarioDao.create(connection, u);
				
				mailService = new MailServiceImpl();
				mailService.sendMail("Bienvenido, "+u.getNombre()
						+"!. Nos complace tenerle con nosotros en SeeDs."
						+" ¡Ya puede empezar a disfrutar de nuestros servicios!"
						,"Bienvenido a SeeDs",u.getEmail());			
				
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
				usuarioDao.update(connection, usuario);				
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

	public void eliminarCuenta (Long idUsuario) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} ", idUsuario);
		}
		
		Connection connection = null;
		Boolean commit = false;

		if(idUsuario!=null) {
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				usuarioDao.delete(connection, idUsuario);
				commit = true;
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
				if(usuarioDao.verificarContrasena(connection, email, contrasena)) {
					usuario = usuarioDao.findByEmail(connection, email);					
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
	public void cambiarContraseña(String email, String contrasena) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} contrasena= {} ", email, contrasena==null);
		}
		
		Connection connection = null;
		Boolean commit=false;

		if(email!=null && contrasena!=null) {
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				
				Usuario usuario = buscarEmail(email);
				usuario.setContrasena(contrasena);
				usuarioDao.update(connection, usuario);
				commit= true;				
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


	@Override
	public Usuario buscarEmail(String email) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("email= {} ", email);
		}

		Usuario usuario = null;
		Connection connection = null;
		
		if(email != null) {
			try {
				connection = ConnectionManager.getConnection();
				usuario = usuarioDao.findByEmail(connection, email);				

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
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
		Connection connection = null;
		
		if(idUsuario != null) {
			try {
				connection = ConnectionManager.getConnection();
				usuario = usuarioDao.findById(connection, idSesion, idUsuario);
				
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return usuario;
	}

	
	@Override
	public Results<Contenido> cargarSeguidos(Long idSesion, int startIndex, int count)
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idSesion= {} ", idSesion);
		}
		
		Connection connection = null;

		if(idSesion != null) {
			try {
				connection = ConnectionManager.getConnection();
				Results<Contenido> contenidos = usuarioDao.cargarSeguidos(connection, idSesion, startIndex, count);
				return contenidos;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return null;
	}

}
