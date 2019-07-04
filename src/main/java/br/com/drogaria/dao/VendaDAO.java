package br.com.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.drogaria.conection.ConnectionFactory;
import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Venda;

/**
 * Classe VendaDAO responsável pelas transações da classe Vendas
 * 
 * @author gilsonalves
 *
 */
public class VendaDAO extends GenericDAO<Venda> {

	@SuppressWarnings("static-access")
	public void salvar(Venda venda, List<ItemVenda> itensVendas) {
		EntityManager em = new ConnectionFactory().getConnection();

		try {
			em.getTransaction().begin();
			if (venda != null) {
				em.persist(venda);
				for (int i = 0; i < itensVendas.size(); i++) {
					ItemVenda itemVenda = itensVendas.get(i);
					itemVenda.setVenda(venda);
					em.persist(itemVenda);
				}
			}
			em.getTransaction().commit();
		} catch (RuntimeException erro) {
			em.getTransaction().rollback();
			throw erro;
		} finally {
			em.close();
		}
	}
}
