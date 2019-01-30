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
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.ListaDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.criteria.ListaCriteria;

public class ListaDAOImpl implements ListaDAO {

	@Override
	public Lista findById(Connection connection, Long id) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
							+ " l.descripcion, l.publica " + 
							"FROM Lista l INNER JOIN Contenido c ON (c.id_contenido = l.id_contenido ) " +
							"WHERE l.id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);
 
			resultSet = preparedStatement.executeQuery();

			Lista e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new DataException("\n Lista with id " +id+ "not found\n");
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
	public List<Lista> findByCriteria(Connection connection, ListaCriteria lista) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {	
			queryString = new StringBuilder(
					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido,"
							+ " l.descripcion, l.publica " + 
							" FROM Lista l INNER JOIN Contenido c ON (c.id_contenido = l.id_contenido ) ");
			
			boolean first = true;

			if (lista.getIdContenido()!=null) {
				addClause(queryString, first, " l.ID_CONTENIDO LIKE ? ");
				first = false;
			}
			
			if (lista.getDescripcion()!=null) {
				addClause(queryString, first, " l.descripcion LIKE ? ");
				first = false;
			}

			if (lista.getPublica()!=null) {
				addClause(queryString, first, " l.publica LIKE ? ");
				first = false;
			}			
			/*
			if (idioma!=null) {
				addClause(queryString, first, " pi.id_idioma LIKE ? ");
				first = false;
			}*/

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i = 1;
			if (lista.getIdContenido()!=null)
				preparedStatement.setString(i++, "%" + lista.getIdContenido() + "%");
			if (lista.getDescripcion()!=null) 
				preparedStatement.setString(i++, "%" + lista.getDescripcion() + "%");
			if (lista.getPublica()!=null)
				preparedStatement.setString(i++, "%" + lista.getPublica() + "%");
			/*
			if (idioma!=null) 
				preparedStatement.setString(i++,idioma);
				*/			
			resultSet = preparedStatement.executeQuery();

			List<Lista> results = new ArrayList<Lista>(); 

			Lista e = null;
			//int currentCount = 0;

			//if ((startIndex >=1) && resultSet.absolute(startIndex)) {
			if(resultSet.next()) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);               	
					//currentCount++;                	
				} while (/*(currentCount < count) && */ resultSet.next()) ;
			}
			//}

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
	public List<Lista> findAll(Connection connection) throws DataException {
		ListaDAO dao= new ListaDAOImpl();
		ListaCriteria lista= new ListaCriteria();
		return dao.findByCriteria(connection, lista);
	}

	@Override
	public Lista create(Connection connection, Lista lista) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			ContenidoDAO daoc = new ContenidoDAOImpl();
			
			Contenido c= daoc.create(connection, lista);
			lista.setIdContenido(c.getIdContenido());


			String queryString = "INSERT INTO Lista (ID_CONTENIDO, USUARIO_ID_CONTENIDO, DESCRIPCION, publica ) "
					+ "VALUES (?, ?, ?, ? )";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);

			// Rellenamos el "preparedStatement"
			
			int i = 1;    
			preparedStatement.setLong(i++, lista.getIdContenido());
			preparedStatement.setLong(i++, lista.getIdAutor());
			preparedStatement.setString(i++, lista.getDescripcion());
			preparedStatement.setBoolean(i++, lista.getPublica());

			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Lista'");
			}

			// Return the DTO
			return lista;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Lista lista) throws DataException {

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	
			
			queryString = new StringBuilder(
					" UPDATE Lista " 
					);
			
			boolean first = true;

			if (lista.getDescripcion()!=null) {
				addUpdate(queryString, first, " descripcion = ? ");
				first = false;
			}
			
			if (lista.getPublica()!=null) {
				addUpdate(queryString, first, " publica = ? ");
				first = false;
			}
			
			queryString.append("WHERE id_contenido = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			
			if (lista.getDescripcion()!=null)
				preparedStatement.setString(i++,lista.getDescripcion());
			
			if (lista.getPublica()!=null)
				preparedStatement.setBoolean(i++,lista.getPublica());
			
			preparedStatement.setLong(i++, lista.getIdContenido());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(lista.getIdContenido(), Lista.class.getName()); //Esto ultimo pa mostrar o nome da clase??
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						lista.getIdContenido() + "' in table 'Lista'");
			}     
			
		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}    
		
	}

	@Override
	public long delete(Connection connection, Long id) throws DataException {
		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					  "DELETE FROM Lista " 
					+ "WHERE id_contenido = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new SQLException("Can not delete row in table 'Lista'");
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
	
	private Lista loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				Long idContenido = resultSet.getLong(i++);
				String nombre = resultSet.getString(i++);
				Date fechaAlta =  resultSet.getDate(i++);
				Date fechaMod =  resultSet.getDate(i++);
				Long autor = resultSet.getLong(i++);	
				
				String descripcion = resultSet.getString(i++);
				Boolean publica = resultSet.getBoolean(i++);
			
				Lista l = new Lista();
				l.setIdContenido(idContenido);
				l.setNombre(nombre);
				l.setFechaAlta(fechaAlta);
				l.setFechaMod(fechaMod);
				l.setIdAutor(autor);

				l.setDescripcion(descripcion);
				l.setPublica(publica);

				return l;
			}

}
