package com.isp.seeds.service;

import com.isp.seeds.model.Usuario;

public class MailServiceTest {

	public static void main(String args[]) {/*
		Usuario u1 = new Usuario();
		u1.setNick("Xose");
		u1.setEmail("joseantoniolp.teacher@gmail.com");	

		Usuario u2 = new Usuario();
		u2.setNick("Fran");
		u2.setEmail("franguimil98@gmail.com");	

		Usuario u3 = new Usuario();
		u3.setNick("Pablo");
		u3.setEmail("varelavazquez.pablo@gmail.com");	

		Usuario u4 = new Usuario();
		u4.setNick("Iago");
		u4.setEmail("seijasiago@gmail.com");	

		Usuario u5 = new Usuario();
		u5.setNick("Alejandro");
		u5.setEmail("acorralfdez@gmail.com");	

		Usuario u6 = new Usuario();
		u6.setNick("David");
		u6.setEmail("dmendez1038@gmail.com");	

		Usuario u7 = new Usuario();
		u7.setNick("Eddie");
		u7.setEmail("eddietuenti@gmail.com");	

		Usuario u8 = new Usuario();
		u8.setNick("Rafa");
		u8.setEmail("rafacervelo@gmail.com");	

		Usuario u9 = new Usuario();
		u9.setNick("Héctor");
		u9.setEmail("hector.modino.otero@gmail.com");	

		// Usuarios de BD
		Usuario[] usuarios = new Usuario[9];
		usuarios[0] = u1;
		usuarios[1] = u2;
		usuarios[2] = u3;
		usuarios[3] = u4;
		usuarios[4] = u5;
		usuarios[5] = u6;
		usuarios[6] = u7;
		usuarios[7] = u8;
		usuarios[8] = u9;

		
		Video video1 = new Video();

		video1.setTitulo("titulo video1");
		video1.setIdContenido(32321423l);
		video1.setDescripcion("Este video tatata tititi tototo");
		video1.setDenuncias(0);
		video1.setReproducciones(3);
		video1.setValoracion(7d);
		video1.setFechaSubida(new Date());
*/

		MailService mailService = new MailService();
		
		Usuario u= new Usuario();
		u.setNombre("Iago");
		u.setEmail("seijasiago@gmail.com");

		String htmlMessage;
		htmlMessage = "<html>";
		htmlMessage += "<body><p>Hola "+u.getNombre()+"!</p>";
		htmlMessage += "<p>Quieres conseguir aprobar?</p>";
		htmlMessage += "<p><b>Por solo una vida de sufrimiento </b></p>";
		htmlMessage += "</body>";
		htmlMessage += "</html>";

		mailService.sendHTMLMail("Mail de prueba ", htmlMessage, u.getEmail());
		System.out.println("Mail enviado a "+u.getEmail());
/*
		String htmlMessage;
		for (Usuario u: usuarios) {
			htmlMessage = "<html>";
			htmlMessage += "<body><p>Hola "+u.getNick()+"!</p>";
			htmlMessage += "<p>Quieres conseguir "+video1.getTitulo()+"?</p>";
			htmlMessage += "<p><b>Por solo "+video1.getDescripcion()+"</b></p>";
			htmlMessage += "<p>Aprovecha nuestra <a href=\"www.jaltraining.com/oferta/id-articulo="+video1.getIdContenido()+"\">oferta.</a>";
			htmlMessage += "</body>";
			htmlMessage += "</html>";

			mailService.sendMail("Oferta "+video1.getTitulo(), htmlMessage, new String[] {u.getEmail()});
			System.out.println("Mail enviado a "+u.getEmail());
		}
	}


*/
}
}
