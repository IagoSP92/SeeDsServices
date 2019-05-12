package com.isp.seeds.service.spi;

import com.isp.seeds.exceptions.MailException;

public interface MailService {
	
	public  void sendMail(String mensajeHTMl, String subject, String... to) throws MailException;

}
