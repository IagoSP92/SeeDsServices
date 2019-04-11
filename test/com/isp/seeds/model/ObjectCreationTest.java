package com.isp.seeds.model;

public class ObjectCreationTest {

	public static void usuarioCompareTo() {

		Usuario user1 = new Usuario();
		Usuario user2 = new Usuario();

		user1.setId(1l);
		user2.setId(2l);

		user1.setNombre("user1");
		user2.setNombre("user2");

		user1.setNombreReal("Paco");
		user2.setNombreReal("Pepon");

		//

		System.out.println("An object: " + user1.toString());

		if(user1.compareTo(user2)==0) {
			System.out.println("Son iguales");
		}
		else {
			if(user1.compareTo(user2)<0) {
				System.out.println("user 1 es menor");
			}
			else{
				System.out.println("user1 es mayor");
			}
		}

	}
	public static void videoCompareTo() {

		Video video1 = new Video();
		Video video2 = new Video();

		video1.setId(1l);
		video2.setId(2l);

		video1.setNombre("video1");
		video2.setNombre("video2");

		video1.setValoracion(5d);
		video2.setValoracion(10d);

		//

		System.out.println("An object: " + video1.toString());

		if(video1.compareTo(video2)==0) {
			System.out.println("Son iguales");
		}
		else {
			if(video1.compareTo(video2)<0) {
				System.out.println("video 1 es menor");
			}
			else{
				System.out.println("video es mayor");
			}
		}

	}

	public static void main(String[] args) {

		usuarioCompareTo();
		videoCompareTo();

		/*  DEPRECATED  
		Video v = new Video();
		v.setTitulo("aefsdag");
		System.out.println("Esta reproducioendo: "+ v.getTitulo());

		Video video1 = new Video();


		video1.setTitulo("titulo video1");
		video1.setIdContenido(32321423l);
		video1.setDescripcion("Este video tatata tititi tototo");
		video1.setDenuncias(0);
		video1.setReproducciones(3);
		video1.setValoracion(7d);
		video1.setFechaSubida(new Date());

		video1.setEtiquetas(null);
		video1.setCategoria(null);
		video1.setAutor(null);
		video1.setUrl(null);


		Video[] arr = new Video [] {v, video1};

		System.out.println("------------- Imprimir array --------------");
		for(Video i:arr) {
			System.out.println("Esta reproducioendo: "+ i.getTitulo());
		}
		System.out.println("----------  TOSTRING Override WITH ToStringBuilder -------");
		Usuario usuario2= new Usuario();
		usuario2.setNick("nickusuario2");

		System.out.println("Usuario;" + usuario2);
		System.out.println("video :"+ video1);

		System.out.println("------------ STRINGBUILDER -------------");
		System.out.println("An object: " + ToStringBuilder.reflectionToString(usuario2));		

		 */
	}
}
