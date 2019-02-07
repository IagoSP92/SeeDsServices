package com.isp.seeds.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailService {
	
	private static Logger logger = LogManager.getLogger(MailService.class);
	
	public MailService() {
		
	}
	
	public boolean sendHTMLMail(String asunto, String mensajeHTML, String... para) {
		
		if(logger.isDebugEnabled()) {
			logger.debug ("Asunto= {} Mensaje HTML= {} Para:{}", asunto, mensajeHTML, para);
		}
		
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("seijasiago@gmail.com", "5Mailsous"));
			email.setSSLOnConnect(true);
			email.setFrom("seijasiago@gmail.com");
			email.setSubject(asunto);
			email.setHtmlMsg(mensajeHTML);			
			email.addTo(para);
			email.send();
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}
	

	
	
	
	/*
	 * //public boolean sendMultiMail(String Asunto, String mensaje, String... to);
	
	public static boolean sendEmail(String to,String subject, String message) {
		try  {
			
			//HtmlEmail email = new HtmlEmail();
			Email email = new SimpleEmail();

			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("seijasiago@gmail.com", PASS));
			email.setSSLOnConnect(true);
			email.setFrom("user@gmail.com");
			email.setSubject(subject);
			email.setMsg(message);
			//email.setHtmlMsg("<html></html>");
			email.addTo(to);
			email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String PASS= "5Mailsous";


}
