package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.Util.PasswordEncryptionUtil;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.dao.utils.UsuarioDAO;
import com.isp.seeds.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public List<Usuario> findByNombre(Connection connection, String nombre) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findByNombre(Connection connection, String nombre, Date fechaAlta) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll(Connection connection) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findById(Connection connection, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario create(Connection connection, Usuario u) throws Exception {


		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			/*
			id_contenido
			private String email = null;
			private String contrasena = null;
			private String descripcion = null;
			private String avatarUrl = null; url_avatar
			private String nombreReal = null; nombre_real
			private String apellidos = null;
			private Pais pais = null; id_pais

			private List<Video> videosSubidos= null;
			private List<Lista> listasSubidas= null;


			private List<Usuario> usuariosSeguidos= null;
			private List<Lista> listasSeguidas= null;

			private List<Video> videosGuardados = null;
			private List<Lista> listasGuardadas = null;    */

			// Creamos el preparedstatement
			String queryString = "INSERT INTO Usuario (id_contenido, email, contrasena, descripcion, url_avatar, nombre_real, apellidos, id_pais) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ? ,?)";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);

			// Rellenamos el "preparedStatement"
			int i = 1;    
			preparedStatement.setLong(i++, u.getIdContenido());
			preparedStatement.setString(i++, u.getEmail());
			preparedStatement.setString(i++, PasswordEncryptionUtil.encryptPassword(u.getContrasena()));
			preparedStatement.setString(i++, u.getDescripcion());
			preparedStatement.setString(i++, u.getAvatarUrl());
			preparedStatement.setString(i++, u.getNombreReal());
			preparedStatement.setString(i++, u.getApellidos());
			
			// DAO PAIS
			
			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Usuario'");
			}

			// Return the DTO
			return u;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Boolean update(Connection connection, Usuario u) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Connection connection, Usuario u) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
