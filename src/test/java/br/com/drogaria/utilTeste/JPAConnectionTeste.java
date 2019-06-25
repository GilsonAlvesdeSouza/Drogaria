package br.com.drogaria.utilTeste;

import javax.persistence.EntityManager;

import org.junit.Test;

import br.com.drogaria.conection.ConnectionFactory;

public class JPAConnectionTeste {
	@SuppressWarnings("static-access")
	@Test
	public void conectar() {
//		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
//		sessao.close();
//		HibernateUtil.getFabricaDeSessoes().close();

		EntityManager em = new ConnectionFactory().getConnection();
		em.close();
	}
}
