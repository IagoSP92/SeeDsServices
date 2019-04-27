package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Etiqueta;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.spi.ContenidoService;
import com.isp.seeds.service.util.Results;

public class ContenidoServiceImpl implements ContenidoService {
	
	private static Logger logger = LogManager.getLogger(ContenidoServiceImpl.class);
	private static ContenidoDAO contenidoDao = new ContenidoDAOImpl();

	
	@Override
	public Contenido buscarId(Long idContenido) {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		Contenido contenido = null;
		if(idContenido != null) {

			try {
				Connection connection = ConnectionManager.getConnection();
				contenido = contenidoDao.findById(connection, idContenido);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return contenido;
	}


	@Override
	public Contenido buscarNombre(String nombreContenido, int startIndex, int count, String idioma) {

		if(logger.isDebugEnabled()) {
			logger.debug ("nombreContenido= {} ", nombreContenido);
		}

		Contenido contenido = null;
		if(nombreContenido != null) {

			try {

				Connection connection = ConnectionManager.getConnection();				
				contenido = contenidoDao.findByNombre(connection, nombreContenido, startIndex, count, idioma);
				JDBCUtils.closeConnection(connection);			

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
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

			try {
				Connection connection = ConnectionManager.getConnection();

				Results<Contenido> contenidos = contenidoDao.findByCriteria(connection, contenido, startIndex, count, idioma);
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


	@Override
	public Results<Contenido> verTodos(int startIndex, int count, String idioma) {
		
		// LOGGER
		
		try {

			Connection connection = ConnectionManager.getConnection();

			Results<Contenido> contenido = contenidoDao.findAll(connection, startIndex, count, idioma);

			JDBCUtils.closeConnection(connection);
			return contenido;

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
	public Contenido crearContenido(Contenido contenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Contenido= {} ", contenido);
		}

		if(contenido != null) {

			try {

				Connection connection = ConnectionManager.getConnection();

				contenidoDao.create(connection, contenido);

				JDBCUtils.closeConnection(connection);

				return contenido;			

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

	
	@Override
	public void eliminarContenido(Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		if(idContenido != null) {

			try {

				Connection connection = ConnectionManager.getConnection();

				if (contenidoDao.exists(connection, idContenido)) {
					contenidoDao.delete(connection, idContenido);				}

				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}

	/*
	@Override
	public Contenido cambiarNombre(Contenido contenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Contenido= {} ", contenido);
		}

		if(contenido != null) {

			try {

				Connection connection = ConnectionManager.getConnection();

				contenidoDao.update(connection, contenido); // SI EL CONTENIDO NO EXISTE SE PERMITE QUE FALLE ¿?
				contenido= contenidoDao.findById(connection, contenido.getIdContenido());

				JDBCUtils.closeConnection(connection);

				return contenido;


			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return null;
	}*/

	
	@Override
	public Contenido cambiarNombre(Long idContenido, String nuevo, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} Nuevo Nombre= {} ", idContenido, nuevo);
		}

		Contenido contenido = null;
		if(idContenido != null && nuevo != null) {

			try {

				Connection connection = ConnectionManager.getConnection();

				contenido = new Contenido();
				contenido= contenidoDao.findById(connection, idContenido); // SI EL CONTENIDO NO EXISTE SE PERMITE QUE FALLE ¿? 
				contenido.setNombre(nuevo);
				contenidoDao.update(connection, contenido);
				contenido= contenidoDao.findById(connection, idContenido);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return contenido;
	}

	
	@Override
	public void asignarCategoria(Long idContenido, Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idCategoria= {} ", idContenido, idCategoria);
		}

		if(idContenido != null && idCategoria != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				contenidoDao.agignarCategoria(connection, idContenido, idCategoria);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}

	}

	
	@Override
	public void asignarEtiqueta(Long idContenido, Long idEtiqueta) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idEtiqueta= {} ", idContenido, idEtiqueta);
		}

		if(idContenido != null && idEtiqueta != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				contenidoDao.asignarEtiqueta(connection, idContenido, idEtiqueta);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}

	}

	
	@Override
	public void modificarCategoria(Long idContenido, Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idCategoria= {} ", idContenido, idCategoria);
		}

		if(idContenido != null && idCategoria != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				contenidoDao.modificarCategoria(connection, idContenido, idCategoria);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}

	}

	
	@Override
	public void eliminarEtiqueta(Long idContenido, Long idEtiqueta) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idEtiqueta= {} ", idContenido, idEtiqueta);
		}

		if(idContenido != null && idEtiqueta != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				contenidoDao.eliminarEtiqueta(connection, idContenido, idEtiqueta);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
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

			try {

				Connection connection = ConnectionManager.getConnection();				
				categoria = contenidoDao.verCategoria(connection, idContenido, idioma);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
		return categoria;
	}

	
	@Override
	public List<Etiqueta> verEtiquetas(Long idContenido, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idioma= {} ", idContenido, idioma);
		}

		if(idContenido != null && idioma != null) {

			try {

				Connection connection = ConnectionManager.getConnection();

				List<Etiqueta> etiquetas= new ArrayList<Etiqueta>();
				etiquetas = contenidoDao.verEtiquetas(connection, idContenido, idioma);

				JDBCUtils.closeConnection(connection);
				return etiquetas;

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

	
	@Override
	public void seguirContenido(Long idUsuario, Long idContenido, Boolean siguiendo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} siguiendo= {} ", idUsuario, idContenido, siguiendo);
		}

		if(idUsuario != null && idContenido != null && siguiendo != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				contenidoDao.seguirContenido(connection, idUsuario, idContenido, siguiendo);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}

	
	@Override
	public void denunciarContenido(Long idUsuario, Long idContenido, String denunciado) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} denunciado= {} ", idUsuario, idContenido, denunciado);
		}

		if(idUsuario != null && idContenido != null && denunciado != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				contenidoDao.denunciarContenido(connection, idUsuario, idContenido, denunciado);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}


	@Override
	public void cancelarDenuncia(Long idUsuario, Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} ", idUsuario, idContenido);
		}

		if(idUsuario != null && idContenido != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				contenidoDao.denunciarContenido(connection, idUsuario, idContenido, null);
				JDBCUtils.closeConnection(connection);			

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}

	
	@Override
	public void valorarContenido(Long idUsuario, Long idContenido, Integer valoracion) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} valoracion= {} ", idUsuario, idContenido, valoracion);
		}

		if(idUsuario != null && idContenido != null && valoracion != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				contenidoDao.valorarContenido(connection, idUsuario, idContenido, valoracion);
				JDBCUtils.closeConnection(connection);			

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}
	

	@Override
	public void guardarContenido(Long idUsuario, Long idContenido, Boolean guardado) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} guardado= {} ", idUsuario, idContenido, guardado);
		}

		if(idUsuario != null && idContenido != null && guardado != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				contenidoDao.guardarContenido(connection, idUsuario, idContenido, guardado);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}


	@Override
	public void comentarContenido(Long idUsuario, Long idContenido, String comentario) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} comentario= {} ", idUsuario, idContenido, comentario);
		}

		if(idUsuario != null && idContenido != null && comentario != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				contenidoDao.comentarContenido(connection, idUsuario, idContenido, comentario);
				JDBCUtils.closeConnection(connection);

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}

	@Override
	public void borrarComentario(Long idUsuario, Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} ", idUsuario, idContenido);
		}

		if(idUsuario != null && idContenido != null) {

			try {

				Connection connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				contenidoDao.comentarContenido(connection, idUsuario, idContenido, null);
				JDBCUtils.closeConnection(connection);			

			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}finally{
				//JDBCUtils.closeConnection(connection);
			}
		}
	}


	@Override
	public Results<Contenido> cargarSeguidos(Long idContenido, int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		if(idContenido != null) {

			try {
				Connection connection = ConnectionManager.getConnection();

				Results<Contenido> contenidos = contenidoDao.cargarSeguidos(connection, idContenido, startIndex, count);
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


	@Override
	public Results<Contenido> cargarGuardados(Long idContenido, int startIndex, int count) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} ", idContenido);
		}

		if(idContenido != null) {

			try {
				Connection connection = ConnectionManager.getConnection();

				Results<Contenido> contenidos = contenidoDao.cargarGuardados(connection, idContenido, startIndex, count);
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


	@Override
	public Integer getValoracion(Long idSesion, Long idContenido) throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug (" dSesion={} idContenido= {} ", idSesion, idContenido);
		}
		Connection connection = null;
		Integer valoracion = null;
		if(idContenido!=null && idSesion!=null) {
			try {
				connection = ConnectionManager.getConnection();
				valoracion = contenidoDao.getValoracion(connection, idSesion, idContenido);
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

}
