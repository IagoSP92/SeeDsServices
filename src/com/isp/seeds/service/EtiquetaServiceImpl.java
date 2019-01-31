package com.isp.seeds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.EtiquetaDAOImpl;
import com.isp.seeds.dao.spi.EtiquetaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Etiqueta;
import com.isp.seeds.service.spi.EtiquetaService;

public class EtiquetaServiceImpl implements EtiquetaService {
	
	private static EtiquetaDAO etiquetaDao = new EtiquetaDAOImpl();


	@Override
	public Etiqueta findById(Long id, String idioma) throws SQLException, DataException {
		try {

			if(id != null && idioma != null) {
				Connection connection = ConnectionManager.getConnection();
				Etiqueta etiqueta = new Etiqueta();
				etiqueta = etiquetaDao.findById(connection, id, idioma);

				JDBCUtils.closeConnection(connection);
				return etiqueta;
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
	public Long findByNombre(String nombreEtiqueta, String idioma) throws SQLException, DataException {
		try {

			if(nombreEtiqueta != null) {
				Connection connection = ConnectionManager.getConnection();
				Long idEtiqueta = null;
				idEtiqueta = etiquetaDao.findByNombre(connection, nombreEtiqueta, idioma);

				JDBCUtils.closeConnection(connection);
				return idEtiqueta;
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
	public List<Etiqueta> findAll() throws SQLException, DataException {
		try {
			Connection connection = ConnectionManager.getConnection();
			List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
			etiquetas = etiquetaDao.findAll(connection);

			JDBCUtils.closeConnection(connection);
			return etiquetas;

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
	public Etiqueta create(Etiqueta etiqueta, String idioma) throws SQLException, DataException {
		try {

			if(etiqueta != null) {
				Connection connection = ConnectionManager.getConnection();

				etiquetaDao.create(connection, etiqueta, idioma);
				
				JDBCUtils.closeConnection(connection);

				return etiqueta;
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
	public void delete(Long idEtiqueta, String idioma) throws SQLException, DataException {
		try {

			if(idEtiqueta != null) {
				Connection connection = ConnectionManager.getConnection();
				
				if (!etiquetaDao.exists(connection, idEtiqueta, idioma)) {
					etiquetaDao.delete(connection, idEtiqueta, idioma);
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

}
