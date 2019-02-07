package com.isp.seeds.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;

public class TestContenidoDAO {

	public static void main(String[] args) throws DataException, SQLException {


		ContenidoDAO dao = new ContenidoDAOImpl();
		
		Connection connection = ConnectionManager.getConnection();
		
		dao.exists(connection, 2l);
		
		JDBCUtils.closeConnection(connection);

	}

}
