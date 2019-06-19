package br.com.drogaria.conection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Classe Para carregar o hibernate Junto com o tomcat
 * 
 * @author gilsonalves
 *
 */
public class HibernateContexto implements ServletContextListener {

	/**
	 * Método para iniciar o tomcat
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ConnectionFactory.getConnection();

	}

	/**
	 * Método para finalizar o tomcat
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		ConnectionFactory.getConnection().close();
	}

}
