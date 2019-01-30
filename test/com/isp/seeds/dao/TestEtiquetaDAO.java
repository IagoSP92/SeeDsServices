package com.isp.seeds.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.dao.impl.EtiquetaDAOImpl;
import com.isp.seeds.dao.spi.EtiquetaDAO;
import com.isp.seeds.dao.utils.ConnectionManager;
import com.isp.seeds.model.Etiqueta;

public class TestEtiquetaDAO {

	public static void main(String[] args){

		try {
			Connection conexion = ConnectionManager.getConnection(); 

			EtiquetaDAO dao = new EtiquetaDAOImpl();
			
			System.out.println(dao.findById(conexion, 1l, "ESP").toString());
			System.out.println();
			System.out.println();

			List<Etiqueta> lista = new ArrayList<Etiqueta>();
			lista = dao.findAll(conexion);

			for(Etiqueta e: lista) {
				System.out.println(e.toString());
			}
			System.out.println();System.out.println();System.out.println();

			Etiqueta uno = new Etiqueta();
			Etiqueta dos = new Etiqueta();
			Etiqueta tres = new Etiqueta();

			uno.setNombreEtiqueta("aaaa");
			dos.setNombreEtiqueta("bbbb");
			tres.setNombreEtiqueta("cccc");

			System.out.println("CREATES:");
			uno = dao.create(conexion, uno, "ESP");
			System.out.println(uno.toString());

			dos = dao.create(conexion, dos, "ESP");
			System.out.println(dos.toString());

			tres = dao.create(conexion, tres, "ESP");
			System.out.println(tres.toString());

			System.out.println("FIND BY ID");

			System.out.println(dao.findById(conexion, uno.getIdEtiqueta(), "ESP").toString());
			System.out.println(dao.findById(conexion, dos.getIdEtiqueta(), "ESP").toString());
			System.out.println(dao.findById(conexion, tres.getIdEtiqueta(), "ESP").toString());

			System.out.println(" FIND ALL");

			lista  = null;

			lista = dao.findAll(conexion);

			for(Etiqueta e: lista) {
				System.out.println(e.toString());
			}

		} catch (SQLException e) {
			System.out.println("SQLException"+e+" en TestEtiquetaDAO");
		} catch (DataException de) {
			System.out.println("DataException"+de+" en TestEtiquetaDAO");
		}

	}
}
