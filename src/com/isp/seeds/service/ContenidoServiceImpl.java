package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.spi.ContenidoService;

public class ContenidoServiceImpl implements ContenidoService {

	private ContenidoDAO contenidoDao = new ContenidoDAOImpl();

	@Override
	public Contenido verContenido(Long idContenido) {

		try {

			if(idContenido != null) {
				Connection connection = ConnectionManager.getConnection();

				Contenido contenido = new Contenido();
				contenido = contenidoDao.findById(connection, contenido, idContenido);

				JDBCUtils.closeConnection(connection);

				return contenido;
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
	public Contenido crearContenido(Contenido contenido) throws DataException {
		try {

			if(contenido != null) {
				Connection connection = ConnectionManager.getConnection();

				contenidoDao.create(connection, contenido);
				
				JDBCUtils.closeConnection(connection);

				return contenido;
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
	public void eliminarContenido(Long idContenido) throws DataException {
		try {

			if(idContenido != null) {
				Connection connection = ConnectionManager.getConnection();
				
				if (!contenidoDao.exists(connection, idContenido)) {
					contenidoDao.delete(connection, idContenido);
				}

				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Contenido cambiarNombre(Contenido contenido) throws DataException {
		try {

			if(contenido != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.update(connection, contenido); // SI EL CONTENIDO NO EXISTE SE PERMITE QUE FALLE ¿?
				contenido= contenidoDao.findById(connection, contenido, contenido.getIdContenido());
				
				JDBCUtils.closeConnection(connection);
				return contenido;
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
	public void cambiarNombre(Long idContenido, String nuevo) throws DataException {
		try {

			if(idContenido != null && nuevo != null) {
				Connection connection = ConnectionManager.getConnection();
				
				Contenido contenido = new Contenido();
				contenido= contenidoDao.findById(connection, contenido, idContenido); // SI EL CONTENIDO NO EXISTE SE PERMITE QUE FALLE ¿?
				contenidoDao.update(connection, contenido);
				
				JDBCUtils.closeConnection(connection);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public void asignarCategoria(Long idContenido, Long idCategoria) throws DataException {
		try {
			
			if(idContenido != null && idCategoria != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.agignarCategoria(connection, idContenido, idCategoria);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void asignarEtiqueta(Long idContenido, Long idEtiqueta) throws DataException {
		try {
			
			if(idContenido != null && idEtiqueta != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.asignarEtiqueta(connection, idContenido, idEtiqueta);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void modificarCategoria(Long idContenido, Long idCategoria) throws DataException {
		try {
			
			if(idContenido != null && idCategoria != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.modificarCategoria(connection, idContenido, idCategoria);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void eliminarEtiqueta(Long idContenido, Long idEtiqueta) throws DataException {
		try {
			
			if(idContenido != null && idEtiqueta != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.eliminarEtiqueta(connection, idContenido, idEtiqueta);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void seguirContenido(Long idUsuario, Long idContenido, Boolean siguiendo) throws DataException {
		try {
			
			if(idUsuario != null && idContenido != null && siguiendo != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.seguirContenido(connection, idUsuario, idContenido, siguiendo);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void denunciarContenido(Long idUsuario, Long idContenido, String denunciado) throws DataException {
		try {
			
			if(idUsuario != null && idContenido != null && denunciado != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.denunciarContenido(connection, idUsuario, idContenido, denunciado);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void cancelarDenuncia(Long idUsuario, Long idContenido) throws DataException {
		try {
			
			if(idUsuario != null && idContenido != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.denunciarContenido(connection, idUsuario, idContenido, null);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void valorarContenido(Long idUsuario, Long idContenido, Integer valoracion) throws DataException {
		try {
			
			if(idUsuario != null && idContenido != null && valoracion != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.valorarContenido(connection, idUsuario, idContenido, valoracion);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void guardarContenido(Long idUsuario, Long idContenido, Boolean guardado) throws DataException {
		try {
			
			if(idUsuario != null && idContenido != null && guardado != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.guardarContenido(connection, idUsuario, idContenido, guardado);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	
	@Override
	public void comentarContenido(Long idUsuario, Long idContenido, String comentario) throws DataException {
		try {
			
			if(idUsuario != null && idContenido != null && comentario != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.comentarContenido(connection, idUsuario, idContenido, comentario);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}

	@Override
	public void borrarComentario(Long idUsuario, Long idContenido) throws DataException {
		try {
			
			if(idUsuario != null && idContenido != null) {
				Connection connection = ConnectionManager.getConnection();
				
				contenidoDao.comentarContenido(connection, idUsuario, idContenido, null);
				
				JDBCUtils.closeConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		
	}




}
