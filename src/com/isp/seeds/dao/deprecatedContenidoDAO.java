package com.isp.seeds.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;


public class deprecatedContenidoDAO {
	

	public Contenido findById(Integer id)
			throws Exception  {

		Contenido c= null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();

			String sql;
			sql = "SELECT ID_CONTENIDO, TITULO"
					+"FROM CONTENIDO"
					+"WHERE ID_CONTENIDO = ?";

			/*Prepara query*/
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			/* Establece Parametros*/
			int i=1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Long idContenido= null;
			String tituloContenido= null;

			if (resultSet.next()) {
				i=1;
				idContenido= resultSet.getLong(i++);
				tituloContenido= resultSet.getString(i++);

				c= new Contenido();
				c.setIdContenido(idContenido);
				c.setTitulo(tituloContenido);


			}
			else {
				throw new Exception("Non se encontrou o contido "+id);
			}
			if (resultSet.next()) {
				throw new Exception("Contido "+id+" duplicado");
			}

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
			JDBCUtils.closeConnection(connection);
		}  	

		return c;

	}




public Contenido[] findByTitulo(String titulo) throws Exception {

	Contenido[] conts= null;
	Contenido c = null;

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	try {
		connection = ConnectionManager.getConnection();

		String sql;
		sql = "SELECT ID_CONTENIDO, TITULO"
				+"FROM CONTENIDO"
				+"WHERE TITULO IS LIKE ?";

		/*Prepara query*/
		System.out.println("Creating statement...");
		preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		/* Establece Parametros*/
		int i=1;
		preparedStatement.setString(i++, titulo);

		resultSet = preparedStatement.executeQuery();

		Long idContenido= null;
		String tituloContenido= null;
		
		i=1;

		while (resultSet.next()) {
			idContenido= resultSet.getLong(i++);
			tituloContenido= resultSet.getString(i++);

			conts= new Contenido[];
			c= new Contenido();
			c.setIdContenido(idContenido);
			c.setTitulo(tituloContenido);


		}
		else {
			throw new Exception("Non se encontrou o contido "+titulo);
		}
	} catch (SQLException ex) {
		throw new DataException(ex);
	} finally {            
		JDBCUtils.closeResultSet(resultSet);
		JDBCUtils.closeStatement(preparedStatement);
		JDBCUtils.closeConnection(connection);
	}  	

	return conts[];
}

public Contenido create (Contenido c)  throws Exception {

	return null;
}

public Boolean update(Contenido c) throws Exception  {
	return null;
}

public void delete(Contenido c) throws Exception  {

}

}
