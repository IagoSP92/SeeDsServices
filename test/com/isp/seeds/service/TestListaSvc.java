package com.isp.seeds.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.model.Video;
import com.isp.seeds.service.spi.ListaService;
import com.isp.seeds.service.spi.UsuarioService;
import com.isp.seeds.service.spi.VideoService;

public class TestListaSvc {

	private static Logger logger = LogManager.getLogger(TestListaSvc.class);
	
	public static void main(String[] args) {
		
		UsuarioService usuarioSvc = new UsuarioServiceImpl();
		ListaService listaSvc  = new ListaServiceImpl();
		VideoService videoSvc = new VideoServiceImpl();
		
		Usuario userUno = new Usuario();
		userUno.setPais("ES");
		userUno.setTipo(1);
		userUno.setFechaAlta(new Date());
		userUno.setFechaMod(new Date());
		userUno.setIdAutor(null);
		
		userUno.setNombreReal("userName");
		userUno.setApellidos("apellidos");
		userUno.setContrasena("abcd");
		userUno.setNombre("userUno");
		userUno.setFechaNac(new Date());
		userUno.setEmail("userUno@gmail.com");
		try {userUno= usuarioSvc.crearCuenta(userUno);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		System.out.println(userUno.toString());
		
		Video videoA = new Video();
		videoA.setFechaAlta(new Date());
		videoA.setFechaMod(new Date());
		videoA.setIdAutor(userUno.getIdContenido());
		videoA.setNombre("videoA");
		videoA.setTipo(2);
		videoA.setUrl("urlideo.avi");
		
		try {videoA = videoSvc.crearVideo(videoA);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		System.out.println(videoA.toString());
		
		
	}

}
