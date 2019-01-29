package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.gzone.ecommerce.model.Producto;
import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.Exceptions.InstanceNotFoundException;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Pais;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.UsuarioCriteria;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	@Override
	public Usuario findById(Connection connection, Long id) throws Exception {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
							+ " u.email, u.contrasena, u.descripcion, u.url_avatar, u.nombre_real, u.apellidos, u.id_pais " + 
							"FROM Usuario u INNER JOIN Contenido c ON (c.id_contenido = u.id_contenido ) " +
							"WHERE u.id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);
 
			resultSet = preparedStatement.executeQuery();

			Usuario e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new Exception("\nUser with id " +id+ "not found\n");
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
	public List<Usuario> findByCriteria(Connection connection, UsuarioCriteria usuario) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Usuario> findAll(Connection connection) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Usuario create(Connection connection, Usuario u) throws Exception {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			ContenidoDAO daoc = new ContenidoDAOImpl();
			
			Contenido c= daoc.create(connection, u);
			u.setIdContenido(c.getIdContenido());


			String queryString = "INSERT INTO USUARIO (ID_CONTENIDO, EMAIL, CONTRASENA, DESCRIPCION, URL_AVATAR, NOMBRE_REAL, APELLIDOS, ID_PAIS) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ? ,?)";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);

			// Rellenamos el "preparedStatement"
			
			
			int i = 1;    
			preparedStatement.setLong(i++, u.getIdContenido());
			preparedStatement.setString(i++, u.getEmail());
			//preparedStatement.setString(i++, PasswordEncryptionUtil.encryptPassword(u.getContrasena()));  // PRODUCE EXCEPTION
			preparedStatement.setString(i++, u.getContrasena());
			preparedStatement.setString(i++, u.getDescripcion());
			preparedStatement.setString(i++, u.getAvatarUrl());
			preparedStatement.setString(i++, u.getNombreReal());
			preparedStatement.setString(i++, u.getApellidos());
			preparedStatement.setString(i++, u.getPais().getIdPais()); // NA DB GARDASE O ID DE PAIS
			
									
			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Usuario'");
			}

			// Return the DTO
			return u;
			// EN SERVICIO INICIALIZAMOS :
//			private List<Video> videosSubidos= null;
//			private List<Lista> listasSubidas= null;
//			private List<Usuario> usuariosSeguidos= null;
//			private List<Lista> listasSeguidas= null;
//			private List<Video> videosGuardados = null;
//			private List<Lista> listasGuardadas = null;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Boolean update(Connection connection, Usuario usuario) throws Exception {
		
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE Usuario" 
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
			
			queryString.append("WHERE id_contenido = ?");
			
			preparedStatement = connection.prepareStatement(queryString.toString());
			

			int i = 1;
			if (usuario.getEmail()!=null)
				preparedStatement.setString(i++,usuario.getEmail());
			
			if (usuario.getContrasena()!=null)
				preparedStatement.setString(i++,usuario.getContrasena());
			
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
			

			preparedStatement.setLong(i++, usuario.getIdContenido());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(producto.getIdProducto(), Producto.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						producto.getIdProducto() + "' in table 'Producto'");
			}     
			
		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}    
	}

	@Override
	public long delete (Connection connection, Long id) throws Exception {

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
				throw new Exception("Exception en delete usuario");
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
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}
	
	private Usuario loadNext(Connection connection, ResultSet resultSet)
			throws Exception {

				int i = 1;
				Long idContenido = resultSet.getLong(i++);	
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
				Pais pais = daop.findById(connection, resultSet.getString(i++));
			
				Usuario u = new Usuario();
				u.setIdContenido(idContenido);
				u.setFechaAlta(fechaAlta);
				u.setFechaMod(fechaMod);
				u.setIdAutor(autor);
				
				u.setEmail(correo);
				u.setContrasena(contrasena);
				u.setDescripcion(descripcion);
				u.setAvatarUrl(avatar);
				u.setNombreReal(nombreReal);
				u.setApellidos(apellido);
				u.setPais(pais);

				return u;
			}
}
