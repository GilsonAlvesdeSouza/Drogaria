package br.com.drogaria.utilTeste;

import javax.persistence.EntityManager;

import org.junit.Test;

import br.com.drogaria.conection.ConectionFactory;


public class HibernateUtilTeste {
	@Test
	public void conectar() {
//		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
//		sessao.close();
//		HibernateUtil.getFabricaDeSessoes().close();
		
		EntityManager em = new ConectionFactory().getConnection();
		em.close();
	}
}
