package com.isp.seeds.exceptions;
/* 
 * NON TEN MOITO SENTIDO TELA NO MISMO PAQUETE QUE AS DEMAIS, 
 * DEBERIA ESTAR FORA DESTE POXECTO CONCRETO, PARA SER USADO POR OUTROS PROXECTOS DA EMPRESA.
 * ESTA AQUI POR SIMPLIFICAR O ARBOL DE CARPETAS
 * 
 * PD: A VER SI ME ACORDA SACALA NO ULTIMO MOMENTO
 */

import java.io.PrintStream;
import java.io.PrintWriter;

public class MyCompanyException extends Exception {
	
	
	public MyCompanyException() {
		super();
	}
	
	public MyCompanyException(String message) {
		this(message, null);		
	}
	
	public MyCompanyException(Throwable cause) {
		this(null,cause);		
	}
	
	public MyCompanyException(String message, Throwable cause) {
		super(message,cause);		
	}			
	
	public void printStackTrace() {
		if (getCause()!=null) {
			getCause().printStackTrace();
		} else {
			super.printStackTrace();
		}
	}
	

	public void printStackTrace(PrintStream s) {
		if (getCause()!=null) {
			getCause().printStackTrace(s);
		} else {
			super.printStackTrace(s);
		}
	}	
	
	public void printStackTrace(PrintWriter w) {
		if (getCause()!=null) {
			getCause().printStackTrace(w);
		} else {
			super.printStackTrace(w);
		}
	}	
}
