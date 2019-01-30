package com.isp.seeds.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.dao.impl.CategoriaDAOImpl;
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.model.Categoria;

public class TestCategoriaDAO {

	public static void main(String[] args) {
		try {
		
		CategoriaDAO dao = new CategoriaDAOImpl();
		Connection connection = ConnectionManager.getConnection();
		
		System.out.println(dao.findById(connection, 1l, "ESP").toString());
		System.out.println(dao.findById(connection, 2l, "ESP").toString());
		System.out.println(dao.findById(connection, 3l, "ESP").toString());
		System.out.println(dao.findById(connection, 4l, "ESP").toString());
		System.out.println(dao.findById(connection, 5l, "ESP").toString());
		System.out.println("--------------------------");
		System.out.println(dao.findById(connection, 1l, "ENG").toString());
		System.out.println(dao.findById(connection, 2l, "ENG").toString());
		System.out.println(dao.findById(connection, 3l, "ENG").toString());
		System.out.println(dao.findById(connection, 4l, "ENG").toString());
		System.out.println(dao.findById(connection, 5l, "ENG").toString());
		
		System.out.println("EMPEZAMOS FINDALL;");
		
		List <Categoria> lista = new ArrayList <Categoria> ();
		lista = dao.findAll(connection);
		
		for(Categoria c: lista) {
			System.out.println(c.toString());
		}
		
		} catch (Exception e) {
			System.out.println("Excepcion en test categoriaDAO");
		}

	}

}
