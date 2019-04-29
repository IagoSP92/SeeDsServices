package com.isp.seeds.util;

import com.isp.seeds.service.CategoriaServiceImpl;
import com.isp.seeds.service.ContenidoServiceImpl;
import com.isp.seeds.service.ListaServiceImpl;
import com.isp.seeds.service.PaisServiceImpl;
import com.isp.seeds.service.UsuarioServiceImpl;
import com.isp.seeds.service.VideoServiceImpl;
import com.isp.seeds.service.spi.CategoriaService;
import com.isp.seeds.service.spi.ContenidoService;
import com.isp.seeds.service.spi.ListaService;
import com.isp.seeds.service.spi.PaisService;
import com.isp.seeds.service.spi.UsuarioService;
import com.isp.seeds.service.spi.VideoService;

public class SingleSvc {
	
	public static ContenidoService contenidoSvc = null;
	public static ListaService listaSvc = null;
	public static VideoService videoSvc = null;
	public static UsuarioService usuarioSvc = null;
	public static CategoriaService categoriaSvc = null;
	public static PaisService paisSvc = null;
	
	static {
		contenidoSvc = new ContenidoServiceImpl();
		videoSvc = new VideoServiceImpl();
		listaSvc = new ListaServiceImpl();
		usuarioSvc = new UsuarioServiceImpl();
		categoriaSvc = new CategoriaServiceImpl();
		paisSvc = new PaisServiceImpl();		
	}
	
}
