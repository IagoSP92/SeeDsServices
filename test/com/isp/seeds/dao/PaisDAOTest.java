package com.isp.seeds.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.dao.impl.PaisDAOImpl;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.model.Pais;

public class PaisDAOTest {
	
	public static void main(String[] args) throws Exception {
		
		PaisDAO dao = new PaisDAOImpl();
		
		List<Pais> lista = new ArrayList<Pais>();
		
		Connection conexion = ConnectionManager.getConnection();
		lista = dao.findAll(conexion);
		
		for(int i =0; i<lista.size();i++) {
			System.out.println(lista.get(i).toString());
		}		
	}
	
	
}
