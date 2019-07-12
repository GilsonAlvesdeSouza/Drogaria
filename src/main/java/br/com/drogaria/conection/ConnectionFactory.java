package br.com.drogaria.conection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

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

	public static Connection getConnectionJDBC() {
		EntityManager em = Persistence.createEntityManagerFactory("DrogariaPU").createEntityManager();

		Session sessao = (Session) em.unwrap(Session.class);

		Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection conn) throws SQLException {
				return conn;
			}
		});

		return conexao;

	}
}
