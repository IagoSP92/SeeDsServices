package com.isp.seeds.dao.utils;

import com.isp.seeds.dao.impl.CategoriaDAOImpl;
import com.isp.seeds.dao.impl.ContenidoDAOImpl;
import com.isp.seeds.dao.impl.ListaDAOImpl;
import com.isp.seeds.dao.impl.PaisDAOImpl;
import com.isp.seeds.dao.impl.UsuarioDAOImpl;
import com.isp.seeds.dao.impl.VideoDAOImpl;
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.ListaDAO;
import com.isp.seeds.dao.spi.PaisDAO;
import com.isp.seeds.dao.spi.UsuarioDAO;
import com.isp.seeds.dao.spi.VideoDAO;

public class SingleDAO {
	
	public static ContenidoDAO contenidoDao = null;
	public static ListaDAO listaDao = null;
	public static VideoDAO videoDao = null;
	public static UsuarioDAO usuarioDao = null;
	public static CategoriaDAO categoriaDao = null;
	public static PaisDAO paisDao = null;
	
	static {
		contenidoDao = new ContenidoDAOImpl();
		videoDao = new VideoDAOImpl();
		listaDao = new ListaDAOImpl();
		usuarioDao = new UsuarioDAOImpl();
		categoriaDao = new CategoriaDAOImpl();
		paisDao = new PaisDAOImpl();
		
	}
	

}
