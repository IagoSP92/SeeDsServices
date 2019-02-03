package com.isp.seeds.dao.impl;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.Exceptions.InstanceNotFoundException;
import com.isp.seeds.Util.PasswordEncryptionUtil;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Pais;
import com.isp.seeds.model.Usuario;

public class UsuarioDAOImpl extends ContenidoDAOImpl implements UsuarioDAO {

	//private static Logger logger = LogManager.getLogger(UsuarioDAOImpl.class.getName());   // ESTO QUE E???

	public UsuarioDAOImpl () {
	}

	@Override
	public Usuario findById(Connection connection, Long id, String idioma) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
							+ " u.email, u.contrasena, u.descripcion, u.url_avatar, u.nombre_real, u.apellidos, u.id_pais, u.fecha_nac " + 
							"FROM Usuario u INNER JOIN Contenido c ON (c.id_contenido = u.id_contenido ) " +
							"WHERE u.id_contenido = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Usuario e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet, idioma);				
			} else {
				throw new DataException("\nUser with id " +id+ "not found\n");
			}
			return e;
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}


	@Override
	public List<Usuario> findAllUsers(Connection connection, String idioma) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
							+ " u.email, u.contrasena, u.descripcion, u.url_avatar, u.nombre_real, u.apellidos, u.id_pais , u.fecha_nac " + 
					" FROM Usuario u INNER JOIN Contenido c ON (c.id_contenido = u.id_contenido ) ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Usuario> results = new ArrayList<Usuario>(); 

			Usuario e = null;

			while (resultSet.next()) {
				e = loadNext(connection, resultSet, idioma);
				results.add(e);               	
			} 
			return results;

		} catch (SQLException e) {
			//logger.error("Error",e);
			throw new DataException(e);
		} catch (DataException de) {
			//logger.error("Error",e);
			throw new DataException(de);
		}  finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public Usuario create(Connection connection, Usuario nuevoUsuario) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			nuevoUsuario = (Usuario) super.create(connection, nuevoUsuario);

			String queryString = "INSERT INTO USUARIO (ID_CONTENIDO, EMAIL, CONTRASENA, DESCRIPCION, URL_AVATAR, NOMBRE_REAL, APELLIDOS, ID_PAIS, FECHA_NAC) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;    
			preparedStatement.setLong(i++, nuevoUsuario.getIdContenido());
			preparedStatement.setString(i++, nuevoUsuario.getEmail());
			preparedStatement.setString(i++, PasswordEncryptionUtil.encryptPassword(nuevoUsuario.getContrasena()));
			preparedStatement.setString(i++, nuevoUsuario.getDescripcion());
			preparedStatement.setString(i++, nuevoUsuario.getAvatarUrl());
			preparedStatement.setString(i++, nuevoUsuario.getNombreReal());
			preparedStatement.setString(i++, nuevoUsuario.getApellidos());
			preparedStatement.setString(i++, nuevoUsuario.getPais().getIdPais()); // NA DB GARDASE O ID DE PAIS
			preparedStatement.setDate(i++, new java.sql.Date(nuevoUsuario.getFechaNac().getTime()));

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
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
			e.printStackTrace();
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}



	@Override
	public void update (Connection connection, Usuario usuario, String idioma) throws InstanceNotFoundException, DataException {

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

			if (usuario.getContrasena()!=null) {
				addUpdate(queryString, first, " contrasena = ? ");
				first = false;
			}

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

			if (usuario.getPais().getIdPais()!=null) {
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

			if (usuario.getContrasena()!=null)
				preparedStatement.setString(i++, PasswordEncryptionUtil.encryptPassword(usuario.getContrasena()) );

			if (usuario.getDescripcion()!=null)
				preparedStatement.setString(i++,usuario.getDescripcion());

			if (usuario.getAvatarUrl()!=null)
				preparedStatement.setString(i++,usuario.getAvatarUrl());

			if (usuario.getNombreReal()!=null)
				preparedStatement.setString(i++,usuario.getNombreReal());

			if (usuario.getApellidos()!=null)
				preparedStatement.setString(i++,usuario.getApellidos());

			if (usuario.getPais().getIdPais()!=null) 
				preparedStatement.setString(i++,usuario.getPais().getIdPais());
			if (usuario.getFechaNac()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(usuario.getFechaNac().getTime()));

			preparedStatement.setLong(i++, usuario.getIdContenido());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(usuario.getIdContenido(), Usuario.class.getName()); //Esto ultimo pa mostrar o nome da clase??
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						usuario.getIdContenido() + "' in table 'USUARIO'");
			}

		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}    
	}


	@Override
	public long delete (Connection connection, Long id) throws DataException {

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM Usuario " 
							+ "WHERE id_contenido = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new SQLException("Can not delete row in table 'Usuario'");
			}

			ContenidoDAO daoc = new ContenidoDAOImpl();
			daoc.delete(connection, id);			

			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}


	@Override
	public Usuario findByEmail(Connection connection, String email, String idioma) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
							+ " u.email, u.contrasena, u.descripcion, u.url_avatar, u.nombre_real, u.apellidos, u.id_pais, u.fecha_nac " + 
							"FROM Usuario u INNER JOIN Contenido c ON (c.id_contenido = u.id_contenido ) " +
							"WHERE u.email = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, email);

			resultSet = preparedStatement.executeQuery();

			Usuario e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet, idioma);				
			} else {
				throw new DataException("\nUser with email: " +email+ "not found\n");
			}

			return e;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}

	@Override
	public Boolean verificarContrasena(Connection connection, String email, String contrasena) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          
			String queryString = "SELECT contrasena FROM Usuario WHERE email = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, email);

			resultSet = preparedStatement.executeQuery();

			String contrasenaGuardada = null;

			if (resultSet.next()) {
				contrasenaGuardada = resultSet.getString(1);				
			} else {
				throw new DataException("\nUser with email: " +email+ "not found\n");
			}

			return PasswordEncryptionUtil.checkPassword(contrasena, contrasenaGuardada);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}		
	}


	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}

	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}

	private Usuario loadNext(Connection connection, ResultSet resultSet, String idioma)
			throws SQLException, DataException {

		int i = 1;
		Long idContenido = resultSet.getLong(i++);
		String nombre = resultSet.getString(i++);
		Date fechaAlta =  resultSet.getDate(i++);
		Date fechaMod =  resultSet.getDate(i++);
		Long autor = resultSet.getLong(i++);	

		String correo = resultSet.getString(i++);
		String contrasena = resultSet.getString(i++);
		String descripcion = resultSet.getString(i++);
		String avatar = resultSet.getString(i++);
		String nombreReal = resultSet.getString(i++);	                
		String apellido = resultSet.getString(i++);

		PaisDAO daop = new PaisDAOImpl();
		Pais pais = daop.findById(connection, resultSet.getString(i++), idioma);
		Date fechaNac =  resultSet.getDate(i++);

		Usuario u = new Usuario();
		u.setIdContenido(idContenido);
		u.setNombre(nombre);
		u.setFechaAlta(fechaAlta);
		u.setFechaMod(fechaMod);
		u.setIdAutor(null);

		u.setEmail(correo);
		u.setContrasena(contrasena);
		u.setDescripcion(descripcion);
		u.setAvatarUrl(avatar);
		u.setNombreReal(nombreReal);
		u.setApellidos(apellido);
		u.setPais(pais);
		u.setFechaNac(fechaNac);

		return u;
	}


}
