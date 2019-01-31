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
				
				contenidoDao.delete(connection, idContenido);

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
				
				contenidoDao.update(connection, contenido);
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
				contenido= contenidoDao.findById(connection, contenido, idContenido);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asignarEtiqueta(Long idContenido, Long idEtiqueta) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarCategoria(Long idContenido, Long idCategoria) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarEtiqueta(Long idContenido, Long idEtiqueta) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seguirContenido(Long idUsuario, Long idContenido, Boolean siguiendo) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void denunciarContenido(Long idUsuario, Long idContenido, Boolean denunciado) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarDenuncia(Long idUsuario, Long idContenido, Boolean denunciado) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valorarContenido(Long idUsuario, Long idContenido, int valoracion) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardarContenido(Long idUsuario, Long idContenido, Boolean guardado) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void comentarContenido(Long idUsuario, Long idContenido, String comentario) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrarComentario(Long idUsuario, Long idContenido, String comentario) throws DataException {
		// TODO Auto-generated method stub
		
	}




}
