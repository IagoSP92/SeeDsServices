package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.CategoriaDAOImpl;
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.service.spi.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService{

	private static CategoriaDAO categoriaDao = new CategoriaDAOImpl();


	@Override
	public Categoria findById(Long id, String idioma) throws DataException {
		try {

			if(id != null && idioma != null) {
				Connection connection = ConnectionManager.getConnection();
				Categoria categoria = new Categoria();
				categoria = categoriaDao.findById(connection, id, idioma);

				JDBCUtils.closeConnection(connection);
				return categoria;
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
	public Long findByNombre(String nombreCategoria) throws DataException {
		try {

			if(nombreCategoria != null) {
				Connection connection = ConnectionManager.getConnection();
				Long idCategoria = null;
				idCategoria = categoriaDao.findByNombre(connection, nombreCategoria);

				JDBCUtils.closeConnection(connection);
				return idCategoria;
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
	public List<Categoria> findAll() throws DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Categoria> categorias = new ArrayList<Categoria>();
			categorias = categoriaDao.findAll(connection);

			JDBCUtils.closeConnection(connection);
			return categorias;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			//JDBCUtils.closeConnection(connection);
		}
		return null;
	}

}
