package br.com.drogaria.conection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Essa classe cria uma fabrica de sessão
 * 
 * @author gilsonalves
 *
 */
public class ConnectionFactory {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("DrogariaPU");

	/**
	 * Método que captura uma sessão
	 * 
	 * @return
	 */
	public static EntityManager getConnection() {

		return emf.createEntityManager();
	}
}
