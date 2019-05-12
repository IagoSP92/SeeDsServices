package com.isp.seeds.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.exceptions.MailException;
import com.isp.seeds.service.spi.MailService;

public class MailServiceImpl implements MailService {
	
	private static Logger logger = LogManager.getLogger(MailServiceImpl.class);
	

	@Override	
	public void sendMail(String mensajeHTMl, String subject, String... to) throws MailException{
		
		if(logger.isDebugEnabled()) {
			logger.debug("Mensaje = {}, subject = {}, to = {}", mensajeHTMl, subject, to);
		}
		
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("seedssvcteam@gmail.com", PASSWORD));
			email.setSSLOnConnect(true);
			email.setFrom("seedssvcteam@gmail.com");
			email.setSubject(subject);
			email.setHtmlMsg(mensajeHTMl);
			email.addTo(to);
			email.send();
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new MailException ("Trying to send email from SeeDsService to "+ to, e);
		}
	}
	

	
	
	
	
	
	public static final String PASSWORD ="seedspass";

}
