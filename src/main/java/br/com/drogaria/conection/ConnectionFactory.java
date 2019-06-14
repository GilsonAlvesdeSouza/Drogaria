package br.com.drogaria.conection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("DrogariaPU");

	public EntityManager getConnection() {

		return emf.createEntityManager();
	}
}
