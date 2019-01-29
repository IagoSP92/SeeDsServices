package com.isp.seeds.service.spi;


import com.isp.seeds.model.Usuario;

public interface UsuarioService {
	
	public Usuario crearCuenta (Usuario u) 
			throws Exception;
	
	public Usuario eliminarCuenta (Usuario u) 
			throws Exception;
}
