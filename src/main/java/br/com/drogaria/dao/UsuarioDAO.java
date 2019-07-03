package br.com.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.drogaria.conection.ConnectionFactory;
import br.com.drogaria.domain.Usuario;

/**
 * Classe UsuarioDAO responsável pelas transções da classe Usuario
 * 
 * @author gilsonalves
 *
 */
public class UsuarioDAO extends GenericDAO<Usuario> {

	@Override
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<Usuario> listarOrdenado(String campoOrdenacao) {
		EntityManager em = new ConnectionFactory().getConnection();
		List<Usuario> lista = null;
		try {
			String consulta = "select u from Usuario u join Pessoa p on u.pessoa.codigo = p.codigo order by "
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
