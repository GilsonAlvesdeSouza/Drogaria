package br.com.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.drogaria.conection.ConnectionFactory;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.Usuario;

/**
 * Classe FuncionarioDAO responsável pelas transações do fabricante na base de dados
 * @author gilsonalves
 *
 */
public class FuncionarioDAO extends GenericDAO<Funcionario>{

	@Override
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<Funcionario> listarOrdenado(String campoOrdenacao) {
		EntityManager em = new ConnectionFactory().getConnection();
		List<Funcionario> lista = null;
		try {
			String consulta = "select f from Funcionario f join Pessoa p on f.pessoa.codigo = p.codigo order by "
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
