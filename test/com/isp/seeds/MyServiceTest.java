package com.isp.seeds;

import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.utils.UsuarioDAO;

public class MyServiceTest {
	
	public void miMetodo () /* throws Exception */ {
		
		//throw new Exception (" a data ta mal");
		//throw new Exception ();
//		UsuarioDAO dao = new UsuarioDAOImpl();
//		
//		dao.create(new Connection c, null );
		
		
		
		
	}

	public static void main(String[] args) {
		
		MyServiceTest t = new MyServiceTest ();
		
		try {
			
			t.miMetodo ();
			
		} catch (Exception e) {
			//System.out.println("Petou "+e.getMessage());
			e.printStackTrace();;
		}
	}
}
