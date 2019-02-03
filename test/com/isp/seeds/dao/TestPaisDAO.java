package com.isp.seeds.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.dao.impl.PaisDAOImpl;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Pais;

public class TestPaisDAO {
	
	public static void main(String[] args) throws Exception {
		
		Connection conexion = ConnectionManager.getConnection();
		
		PaisDAO dao = new PaisDAOImpl();
		System.out.println();
		System.out.println("________________________________  ALGUNOS POR ID EN CADA IDIOMA");
		System.out.println(dao.findById(conexion, "ES", "ESP").toString());
		System.out.println(dao.findById(conexion, "ES", "ENG").toString());
		System.out.println(dao.findById(conexion, "US", "ESP").toString());
		System.out.println(dao.findById(conexion, "US", "ENG").toString());
		
		List<Pais> lista = new ArrayList<Pais>();
		
		lista = dao.findAll(conexion, "ESP");
		System.out.println();
		System.out.println("________________________________  TODOS EN ESPAÑOL");
		for(int i =0; i<lista.size();i++) {
			System.out.println(lista.get(i).toString());
		}
		
		lista = dao.findAll(conexion, "ENG");
		System.out.println();
		System.out.println("________________________________  TODOS EN INGLES");
		for(int i =0; i<lista.size();i++) {
			System.out.println(lista.get(i).toString());
		}
		
		
		
		JDBCUtils.closeConnection(conexion);
	}
}
