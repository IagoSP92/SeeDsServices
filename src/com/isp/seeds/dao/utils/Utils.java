package com.isp.seeds.dao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Comentario;

public class Utils {
	
	private static Logger logger = LogManager.getLogger(Utils.class);

	
	public static List<Comentario> cargarComentarios(Connection connection, Long idContenido) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					"SELECT UC.COMENTARIO, C.NOMBRE FROM USUARIO_CONTENIDO UC "
					+" INNER JOIN CONTENIDO C ON (C.ID_CONTENIDO = UC.USUARIO_ID_CONTENIDO)"
					+" WHERE CONTENIDO_ID_CONTENIDO = ? AND UC.COMENTARIO != 'Null'");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}	
			resultSet = preparedStatement.executeQuery();
			
			List<Comentario> comentarios = new ArrayList<Comentario>();
			Comentario comentario =null;
			int i=1;
			if (resultSet.next()) {
				do {
					comentario = new Comentario();
					i=1;
					comentario.setTexto(resultSet.getString(i++));
					comentario.setAutor(resultSet.getString(i++));
					
					comentarios.add(comentario); 
				} while (resultSet.next()) ;
			}
			return comentarios;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}

}
