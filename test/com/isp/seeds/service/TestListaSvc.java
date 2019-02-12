package com.isp.seeds.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Lista;
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
		
		Usuario userDos = new Usuario();
		userDos.setPais("ES");
		userDos.setTipo(1);
		userDos.setFechaAlta(new Date());
		userDos.setFechaMod(new Date());
		userDos.setIdAutor(null);
		
		userDos.setNombreReal("userName");
		userDos.setApellidos("apellidos");
		userDos.setContrasena("abcd");
		userDos.setNombre("userDos");
		userDos.setFechaNac(new Date());
		userDos.setEmail("userDos@gmail.com");
		
		try {userDos= usuarioSvc.crearCuenta(userDos);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		System.out.println(userDos.toString());
		
		Video videoA = new Video();
		videoA.setFechaAlta(new Date());
		videoA.setFechaMod(new Date());
		videoA.setIdAutor(userUno.getIdContenido());
		videoA.setNombre("videoA");
		videoA.setTipo(2);
		videoA.setUrl("urlideo.avi");
		
		try {videoA = videoSvc.crearVideo(videoA);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		System.out.println(videoA.toString());
		
		Video videoB = new Video();
		videoB.setFechaAlta(new Date());
		videoB.setFechaMod(new Date());
		videoB.setIdAutor(userUno.getIdContenido());
		videoB.setNombre("videoB");
		videoB.setTipo(2);
		videoB.setUrl("urlideo.avi");
		
		try {videoB = videoSvc.crearVideo(videoB);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		System.out.println(videoB.toString());
		
		Video videoC = new Video();
		videoC.setFechaAlta(new Date());
		videoC.setFechaMod(new Date());
		videoC.setIdAutor(userDos.getIdContenido());
		videoC.setNombre("videoC");
		videoC.setTipo(2);
		videoC.setUrl("urlideo.avi");
		
		try {videoC = videoSvc.crearVideo(videoC);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		System.out.println(videoC.toString());
		
		Lista listaPrimera = new Lista();
		listaPrimera.setFechaAlta(new Date());
		listaPrimera.setFechaMod(new Date());
		listaPrimera.setIdAutor(userUno.getIdContenido());
		listaPrimera.setNombre("listaPrimera");
		listaPrimera.setDescripcion("descripcionLista");
		listaPrimera.setTipo(3);
		listaPrimera.setPublica(true);
		
		try {listaPrimera = listaSvc.crearLista(listaPrimera);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		try {System.out.println(listaSvc.buscarId(listaPrimera.getIdContenido()).toString()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		
		listaPrimera.setDescripcion("descripcionListaModificada");
		listaPrimera.setNombre("listaPrimeraModificada");
		
		try {listaSvc.editarLista(listaPrimera);}catch(DataException e) {logger.warn(e.getMessage(), e);};
		try {System.out.println(listaSvc.buscarId(listaPrimera.getIdContenido()).toString()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		
		try {
		listaSvc.meterVideo(listaPrimera.getIdContenido(), videoA.getIdContenido(), 1);
		listaSvc.meterVideo(listaPrimera.getIdContenido(), videoB.getIdContenido(), 2);
		listaSvc.meterVideo(listaPrimera.getIdContenido(), videoC.getIdContenido(), 3);
		}catch(DataException e) {logger.warn(e.getMessage(), e);};
		
		List<Video> contenidoLista = null;
		
		contenidoLista = new ArrayList<Video>();
		try { contenidoLista = listaSvc.verVideosLista(listaPrimera.getIdContenido()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		for(Video v:contenidoLista) {
			System.out.println(v.toString());
		}
		
		try {
			listaSvc.sacarVideo(listaPrimera.getIdContenido(), videoC.getIdContenido());
			listaSvc.sacarVideo(listaPrimera.getIdContenido(), videoB.getIdContenido());
			listaSvc.sacarVideo(listaPrimera.getIdContenido(), videoA.getIdContenido());
		}catch(DataException e) {logger.warn(e.getMessage(), e);};
		
		contenidoLista = new ArrayList<Video>();
		try { contenidoLista = listaSvc.verVideosLista(listaPrimera.getIdContenido()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		for(Video v:contenidoLista) {
			System.out.println(v.toString());
		}
		
		try { videoSvc.eliminarVideo(videoA.getIdContenido()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		try { videoSvc.eliminarVideo(videoB.getIdContenido()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		try { videoSvc.eliminarVideo(videoC.getIdContenido()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		
		try { usuarioSvc.eliminarCuenta(userUno.getIdContenido()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
		try { usuarioSvc.eliminarCuenta(userDos.getIdContenido()); }catch(DataException e) {logger.warn(e.getMessage(), e);};
			

	}

}
