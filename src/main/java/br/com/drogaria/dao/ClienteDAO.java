package br.com.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.drogaria.conection.ConnectionFactory;
import br.com.drogaria.domain.Cliente;

/**
 * Classe ClienteDAO responável pelas transações da classe cliente na base de
 * dados
 * 
 * @author gilsonalves
 *
 */
public class ClienteDAO extends GenericDAO<Cliente> {

	@Override
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<Cliente> listarOrdenado(String campoOrdenacao) {
		EntityManager em = new ConnectionFactory().getConnection();
		List<Cliente> lista = null;
		try {
			String consulta = "select c from Cliente c join Pessoa p on c.pessoa.codigo = p.codigo order by "
					+ campoOrdenacao;
			lista = em.createQuery(consulta).getResultList();
			return lista;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			em.close();
		}
	}
}
