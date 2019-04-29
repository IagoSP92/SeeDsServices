package com.isp.seeds.dao.impl;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.Exceptions.InstanceNotFoundException;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.util.Results;
import com.isp.seeds.util.PasswordEncryptionUtil;

public class UsuarioDAOImpl extends ContenidoDAOImpl implements UsuarioDAO {
	
	private static Logger logger = LogManager.getLogger(UsuarioDAOImpl.class);


	@Override
	public Usuario findById(Connection connection, Long idSesion, Long idUsuario) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} ", idUsuario);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			StringBuilder queryString =new StringBuilder(
					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+ ", U.EMAIL, U.CONTRASENA, U.DESCRIPCION, U.URL_AVATAR, U.NOMBRE_REAL, U.APELLIDOS, U.ID_PAIS, U.FECHA_NAC "
					+", UC.SIGUIENDO, UC.DENUNCIADO "
					+" FROM USUARIO U INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = U.ID_CONTENIDO ) "
					+" LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO=UC.CONTENIDO_ID_CONTENIDO) ");
					if(idSesion!=null) { queryString.append(" AND (UC.USUARIO_ID_CONTENIDO= ? ) ");}
					queryString.append(" WHERE U.ID_CONTENIDO = ? ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if(idSesion!=null) {
				preparedStatement.setLong(i++, idSesion);
			}			
			preparedStatement.setLong(i++, idUsuario);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			Usuario usuario = null;

			if (resultSet.next()) {
				usuario = loadNext(connection, resultSet);				
			} else {
				logger.debug("Usuario con id= {} no encontrado.", idUsuario );
				throw new DataException("\nUser with id " +idUsuario+ "not found\n");
			}
			return usuario;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}	
	

	@Override
	public Usuario findByEmail(Connection connection, String email)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Email= {} ", email);
		}		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
							+ ", U.EMAIL, U.CONTRASENA, U.DESCRIPCION, U.URL_AVATAR, U.NOMBRE_REAL, U.APELLIDOS, U.ID_PAIS, U.FECHA_NAC "
							+" FROM USUARIO U INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = U.ID_CONTENIDO ) "
							+" LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO=UC.CONTENIDO_ID_CONTENIDO)"
							+" WHERE U.EMAIL = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, email);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			Usuario usuario = null;

			if (resultSet.next()) {
				usuario = loadNext(connection, resultSet);				
			} else {
				logger.debug("Usuario con email:{} no encontrado", email);
				throw new DataException("\nUser with email: " +email+ "not found\n");
			}
			return usuario;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}
	

	@Override
	public Boolean verificarContrasena(Connection connection, String email, String contrasena)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Email= {} Contrasena={} ", email, contrasena==null);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          
			String queryString = "SELECT contrasena FROM Usuario WHERE email = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, email);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}			
			resultSet = preparedStatement.executeQuery();

			String contrasenaGuardada = null;

			if (resultSet.next()) {
				contrasenaGuardada = resultSet.getString(1);				
			} else {
				logger.debug("Usuario con email:{} no encontrado", email);
				throw new DataException("\nUser with email: " +email+ "not found \n");
			}

			return PasswordEncryptionUtil.checkPassword(contrasena, contrasenaGuardada);

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}


	@Override
	public Usuario create(Connection connection, Usuario nuevoUsuario) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Nuevo Usuario={} ", nuevoUsuario);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			nuevoUsuario = (Usuario) super.create(connection, nuevoUsuario);

			String queryString = "INSERT INTO USUARIO (ID_CONTENIDO, EMAIL, CONTRASENA, DESCRIPCION, URL_AVATAR, NOMBRE_REAL, APELLIDOS, ID_PAIS, FECHA_NAC) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;    
			preparedStatement.setLong(i++, nuevoUsuario.getId());
			preparedStatement.setString(i++, nuevoUsuario.getEmail());
			preparedStatement.setString(i++, PasswordEncryptionUtil.encryptPassword(nuevoUsuario.getContrasena()));
			preparedStatement.setString(i++, nuevoUsuario.getDescripcion());
			preparedStatement.setString(i++, nuevoUsuario.getAvatarUrl());
			preparedStatement.setString(i++, nuevoUsuario.getNombreReal());
			preparedStatement.setString(i++, nuevoUsuario.getApellidos());
			preparedStatement.setString(i++, nuevoUsuario.getPais());
			preparedStatement.setDate(i++, new java.sql.Date(nuevoUsuario.getFechaNac().getTime()));

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				logger.debug("No se ha podido insertar en la tabla USUARIO");
				throw new SQLException("Can not add row to table 'Usuario'");
			}

			return nuevoUsuario;

			// EN SERVICIO INICIALIZAMOS :
			//			private List<Video> videosSubidos= null;
			//			private List<Lista> listasSubidas= null;
			//			private List<Usuario> usuariosSeguidos= null;
			//			private List<Lista> listasSeguidas= null;
			//			private List<Video> videosGuardados = null;
			//			private List<Lista> listasGuardadas = null;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}



	@Override
	public void update (Connection connection, Usuario usuario) throws InstanceNotFoundException, DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Usuario= {} ", usuario);
		}
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			super.update(connection,usuario);

			queryString = new StringBuilder(
					" UPDATE Usuario " 
					);

			boolean first = true;

			if (usuario.getEmail()!=null) {
				addUpdate(queryString, first, " email = ? ");
				first = false;
			}

//			if (usuario.getContrasena()!=null) {
//				addUpdate(queryString, first, " contrasena = ? ");
//				first = false;
//			}

			if (usuario.getDescripcion()!=null) {
				addUpdate(queryString, first, " descripcion = ? ");
				first = false;
			}

			if (usuario.getAvatarUrl()!=null) {
				addUpdate(queryString, first, " url_avatar = ? ");
				first = false;
			}

			if (usuario.getNombreReal()!=null) {
				addUpdate(queryString, first, " nombre_real = ? ");
				first = false;
			}

			if (usuario.getApellidos()!=null) {
				addUpdate(queryString, first, " apellidos = ? ");
				first = false;
			}

			if (usuario.getPais()!=null) {
				addUpdate(queryString, first, " id_pais = ? ");
				first = false;
			}

			if (usuario.getFechaNac()!=null) {
				addUpdate(queryString, first, " fecha_nac = ? ");
				first = false;
			}

			queryString.append("WHERE id_contenido = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			if (usuario.getEmail()!=null)
				preparedStatement.setString(i++,usuario.getEmail());

//			if (usuario.getContrasena()!=null)
//				preparedStatement.setString(i++, PasswordEncryptionUtil.encryptPassword(usuario.getContrasena()) );

			if (usuario.getDescripcion()!=null)
				preparedStatement.setString(i++,usuario.getDescripcion());

			if (usuario.getAvatarUrl()!=null)
				preparedStatement.setString(i++,usuario.getAvatarUrl());

			if (usuario.getNombreReal()!=null)
				preparedStatement.setString(i++,usuario.getNombreReal());

			if (usuario.getApellidos()!=null)
				preparedStatement.setString(i++,usuario.getApellidos());

			if (usuario.getPais()!=null) 
				preparedStatement.setString(i++,usuario.getPais());
			if (usuario.getFechaNac()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(usuario.getFechaNac().getTime()));

			preparedStatement.setLong(i++, usuario.getId());

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				logger.debug("No se ha podido eliminar el usuario con id{} de la tabla USUARIO", usuario.getId());
				throw new InstanceNotFoundException(usuario.getId(), Usuario.class.getName());
			}

			if (updatedRows > 1) {
				logger.debug("Filas duplicadas para id={} en tabla USUARIO", usuario.getId());
				throw new SQLException("Duplicate row for id = '" + 
						usuario.getId() + "' in table 'USUARIO'");
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}    
	}


	@Override
	public void delete (Connection connection, Long idUsuario)
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} ", idUsuario);
		}

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM Usuario " 
							+ "WHERE id_contenido = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idUsuario);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				logger.debug("No se ha podido eliminar el usuario con id{} de la tabla USUARIO", idUsuario);
				throw new SQLException("Can not delete row in table 'Usuario'");
			}

			super.delete(connection, idUsuario);	

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	@Override
	public Results<Contenido> cargarSeguidos(Connection connection, Long idSesion, int startIndex, int count)
			throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+" FROM CONTENIDO C INNER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO) "
							+" AND (UC.USUARIO_ID_CONTENIDO= ? ) "
					+" INNER JOIN USUARIO U ON (U.ID_CONTENIDO=C.ID_CONTENIDO)"
					+" WHERE UC.USUARIO_ID_CONTENIDO = ? AND UC.SIGUIENDO= TRUE "
					+" GROUP BY UC.CONTENIDO_ID_CONTENIDO");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i=1;
			preparedStatement.setLong(i++, idSesion);
			preparedStatement.setLong(i++, idSesion);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			Contenido contenido = new Contenido();
			List<Contenido> page = new ArrayList<Contenido>();
			int currentCount = 0;
			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					contenido = ContenidoDAOImpl.loadNext(connection, resultSet);
					page.add(contenido);
					currentCount++;
				} while ((currentCount < count) &&  resultSet.next()) ;
			}
			int totalRows = JDBCUtils.getTotalRows(resultSet);
			Results<Contenido> results = new Results<Contenido>(page, startIndex, totalRows);
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}

	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}

	protected static Usuario loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {
		int i = 1;
		
		Long idContenido = resultSet.getLong(i++);
		String nombre = resultSet.getString(i++);
		Date fechaAlta =  resultSet.getDate(i++);
		Date fechaMod =  resultSet.getDate(i++);
		Integer tipo= resultSet.getInt(i++);

		Integer reproducciones = resultSet.getInt(i++);
		Double valoracion = resultSet.getDouble(i++);
		
		String correo = resultSet.getString(i++);

		String contrasena = resultSet.getString(i++);
		String descripcion = resultSet.getString(i++);
		String avatar = resultSet.getString(i++);
		String nombreReal = resultSet.getString(i++);
		String apellido = resultSet.getString(i++);
		String pais = resultSet.getString(i++);
		Date fechaNac =  resultSet.getDate(i++);

		Usuario u = new Usuario();
		u.setId(idContenido);
		u.setNombre(nombre);
		u.setFechaAlta(fechaAlta);
		u.setFechaMod(fechaMod);
		u.setAutor(null);
		u.setTipo(tipo);		
		
		u.setReproducciones(reproducciones);
		u.setValoracion(valoracion);

		u.setEmail(correo);
		u.setContrasena(contrasena);
		u.setDescripcion(descripcion);
		u.setAvatarUrl(avatar);
		u.setNombreReal(nombreReal);
		u.setApellidos(apellido);
		u.setPais(pais);
		u.setFechaNac(fechaNac);		
		
		if(i<resultSet.getMetaData().getColumnCount()) {
			Boolean siguiendo = resultSet.getBoolean(i++);
			Boolean denunciado = resultSet.getBoolean(i++);
			u.setSiguiendo(siguiendo);
			u.setDenunciado(denunciado);
		}

		return u;
	}
	

//	@Override
//	public List<Usuario> findAllUsers(Connection connection, int startIndex, int count, String idioma) throws DataException {
//		
//		if(logger.isDebugEnabled()) {
//			logger.debug ("startIndex={} count={}", startIndex, count);
//		}
//
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		StringBuilder queryString = null;
//
//		try {
//			
//			queryString = new StringBuilder(
//					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido, c.tipo, "
//							+ " u.email, u.contrasena, u.descripcion, u.url_avatar, u.nombre_real, u.apellidos, u.id_pais , u.fecha_nac " + 
//					" FROM Usuario u INNER JOIN Contenido c ON (c.id_contenido = u.id_contenido ) ORDER BY c.fecha_alta ");
//
//			preparedStatement = connection.prepareStatement(queryString.toString(),
//					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			
//			if(logger.isDebugEnabled()) {
//				logger.debug("QUERY= {}",preparedStatement);
//			}			
//			resultSet = preparedStatement.executeQuery();
//
//			List<Usuario> results = new ArrayList<Usuario>();
//
//			Usuario e = null;
//			int currentCount=0;
//
//			while (startIndex<0 && resultSet.absolute(startIndex)) {
//				do {
//					while (resultSet.next()) {
//						e = loadNext(connection, resultSet);
//						results.add(e);
//					}
//				} while ((currentCount < count) &&  resultSet.next());
//			}
//			return results;
//
//		} catch (SQLException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		} catch (DataException e) {
//			logger.warn(e.getMessage(), e);
//			throw new DataException(e);
//		}  finally {
//			JDBCUtils.closeResultSet(resultSet);
//			JDBCUtils.closeStatement(preparedStatement);
//		}
//	}
	
}
	