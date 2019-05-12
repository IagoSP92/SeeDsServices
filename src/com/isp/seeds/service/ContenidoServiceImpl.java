package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.spi.ContenidoService;
import com.isp.seeds.service.util.Results;

public class ContenidoServiceImpl implements ContenidoService {
	
	private static Logger logger = LogManager.getLogger(ContenidoServiceImpl.class);
	
	private ContenidoDAO contenidoDao = null;
	
	public ContenidoServiceImpl() {
		contenidoDao = new ContenidoDAOImpl();
	}
	
	
	@Override
	public Contenido buscarId(Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		Contenido contenido = null;
		Connection connection = null;
		
		if(idContenido != null) {
			try {
				connection = ConnectionManager.getConnection();
				contenido = contenidoDao.findById(connection, idContenido);				

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return contenido;
	}

	

	@Override
	public Results<Contenido> buscarCriteria(ContenidoCriteria contenido, int startIndex, int count, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("ContenidoCriteria= {} ", contenido);
		}

		if(contenido != null) {
			
			Connection connection = null;

			try {
				connection = ConnectionManager.getConnection();
				Results<Contenido> contenidos = 
						contenidoDao.findByCriteria(connection, contenido, startIndex, count, idioma);
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


	@Override
	public void eliminarContenido(Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		if(idContenido != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				if (contenidoDao.exists(connection, idContenido)) {
					contenidoDao.delete(connection, idContenido);
					commit=true;
				}		

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}

	
	
	
	@Override
	public void asignarCategoria(Long idContenido, Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idCategoria= {} ", idContenido, idCategoria);
		}

		if(idContenido != null && idCategoria != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.agignarCategoria(connection, idContenido, idCategoria);	
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}

	
		
	@Override
	public void modificarCategoria(Long idContenido, Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idCategoria= {} ", idContenido, idCategoria);
		}

		if(idContenido != null && idCategoria != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.modificarCategoria(connection, idContenido, idCategoria);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}


	
	@Override
	public Categoria verCategoria(Long idContenido, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idioma= {} ", idContenido, idioma);
		}

		Categoria categoria= null;
		
		if(idContenido != null && idioma != null) {
			Connection connection = null;
			try {				
				categoria = contenidoDao.verCategoria(connection, idContenido, idioma);				

			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return categoria;
	}

	
	
	@Override
	public void seguirContenido(Long idUsuario, Long idContenido, Boolean siguiendo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} siguiendo= {} ", idUsuario, idContenido, siguiendo);
		}

		if(idUsuario != null && idContenido != null && siguiendo != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.seguirContenido(connection, idUsuario, idContenido, siguiendo);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}

	
	@Override
	public void denunciarContenido(Long idUsuario, Long idContenido, Boolean denunciado) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} denunciado= {} ", idUsuario, idContenido, denunciado);
		}

		if(idUsuario != null && idContenido != null && denunciado != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.denunciarContenido(connection, idUsuario, idContenido, denunciado);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}


	@Override
	public void cancelarDenuncia(Long idUsuario, Long idContenido, Boolean denunciado) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} ", idUsuario, idContenido);
		}

		if(idUsuario != null && idContenido != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.denunciarContenido(connection, idUsuario, idContenido, false);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);		
			}
		}
	}

	
	@Override
	public void valorarContenido(Long idUsuario, Long idContenido, Integer valoracion) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} valoracion= {} ", idUsuario, idContenido, valoracion);
		}

		if(idUsuario != null && idContenido != null && valoracion != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.valorarContenido(connection, idUsuario, idContenido, valoracion);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);			
			}
		}
	}
	

	@Override
	public void guardarContenido(Long idUsuario, Long idContenido, Boolean guardado) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} guardado= {} ", idUsuario, idContenido, guardado);
		}

		if(idUsuario != null && idContenido != null && guardado != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.guardarContenido(connection, idUsuario, idContenido, guardado);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}


	@Override
	public void comentarContenido(Long idUsuario, Long idContenido, String comentario) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} comentario= {} ", idUsuario, idContenido, comentario);
		}

		if(idUsuario != null && idContenido != null && comentario != null) {
			Connection connection = null;
			Boolean commit = true;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.comentarContenido(connection, idUsuario, idContenido, comentario);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}

	
	@Override
	public void borrarComentario(Long idUsuario, Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} ", idUsuario, idContenido);
		}

		if(idUsuario != null && idContenido != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.comentarContenido(connection, idUsuario, idContenido, null);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);	
			}
		}
	}


	@Override
	public Results<Contenido> cargarSeguidos(Long idContenido, int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		if(idContenido != null) {
			Connection connection = null;
			try {
				connection = ConnectionManager.getConnection();
				Results<Contenido> contenidos = contenidoDao.cargarSeguidos(connection, idContenido, startIndex, count);
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


	@Override
	public Results<Contenido> cargarGuardados(Long idContenido, int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		if(idContenido != null) {
			Connection connection = null;
			try {
				connection = ConnectionManager.getConnection();
				Results<Contenido> contenidos = contenidoDao.cargarGuardados(connection, idContenido, startIndex, count);
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


	@Override
	public Double getValoracion(Long idContenido) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}
		
		Connection connection = null;
		Double valoracion = null;
		
		if(idContenido!=null) {
			try {
				connection = ConnectionManager.getConnection();
				valoracion = contenidoDao.getValoracion(connection, idContenido);
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return valoracion;
	}



	@Override
	public void update(Contenido contenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Contenido= {} ", contenido);
		}

		if(contenido != null) {
			Connection connection = null;
			Boolean commit = false;
			try {
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(false);
				contenidoDao.update(connection, contenido);
				commit = true;

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection, commit);
			}
		}
	}


	@Override
	public List<Contenido> verVideosAutor(Long idAutor) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idAutor= {} ", idAutor);
		}
		Connection connection = null;
		List<Contenido> resultados = null;
		if(idAutor!=null) {
			try {
				connection = ConnectionManager.getConnection();
				resultados = contenidoDao.verVideosAutor(connection, idAutor);
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				JDBCUtils.closeConnection(connection);
			}
		}
		return resultados;
	}


}
