package com.isp.seeds.dao.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionManager	 {


/*	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
//	static final String DB_URL = "jdbc:mysql://10.53.124.231:3306/hr?"
//			+ "useUnicode=true&useJDBCCompliantTimezoneShift=true"
//			+ "&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	
	//CASA
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/seeds?"
			+ "useUnicode=true&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	//CLASE
//	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/seeds?"
//			+ "useUnicode=true&useJDBCCompliantTimezoneShift=true"
//			+ "&useLegacyDatetimeCode=false&serverTimezone=UTC";

	//  Database credentials
	static final String USER = "iago";
	static final String PASS = "seijas";*/
	
	private static Logger logger = LogManager.getLogger(ConnectionManager.class.getName());
	
	private static ResourceBundle dbConfiguration = ResourceBundle.getBundle("DBConfiguration");

	private static final String DRIVER_CLASS_NAME_PARAMETER = "jdbc.driver.classname";
	private static final String URL_PARAMETER = "jdbc.url";
	private static final String USER_PARAMETER = "jdbc.user";
	private static final String PASSWORD_PARAMETER = "jdbc.password";
	private static final String MAX_SIZE= "jdbc.pool.maxsize";

	private static String url;
	private static String user;
	private static String password;
	private static Integer maxpoolsize;

	private static ComboPooledDataSource dataSource = null;

	static {

		try {
			// Carga el driver directamente, sin pool 
			// Class.forName(JDBC_DRIVER);
			
			String driverClassName = dbConfiguration.getString(DRIVER_CLASS_NAME_PARAMETER);
			url = dbConfiguration.getString(URL_PARAMETER);
			user = dbConfiguration.getString(USER_PARAMETER);
			password = dbConfiguration.getString(PASSWORD_PARAMETER);
			maxpoolsize = Integer.parseInt(dbConfiguration.getString(MAX_SIZE));

			// Pool (Aunque la clase se apellide DataSource no es un java.sql.DataSource)
			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass(driverClassName); //loads the jdbc driver            
			dataSource.setJdbcUrl(url);
			dataSource.setUser(user);                                  
			dataSource.setPassword(password);  
			dataSource.setMaxPoolSize(maxpoolsize);
			
			
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e); 
		}

	}

	private ConnectionManager() {}

	public final static Connection getConnection() throws SQLException {
		//return DriverManager.getConnection(DB_URL, USER, PASS);
		return dataSource.getConnection();
	}
	
}