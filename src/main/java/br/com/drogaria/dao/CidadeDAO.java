package br.com.drogaria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.drogaria.conection.ConnectionFactory;
import br.com.drogaria.domain.Cidade;

/**
 * Calsse CidadeDAO responsável pelas transações de cidade na base de dados
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings("serial")
public class CidadeDAO extends GenericDAO<Cidade> implements Serializable {

	/**
	 * Método para listar todas as cidades de acordo com o estado escolhido
	 * 
	 * @param idEstado
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<Cidade> bucarPorEstado(Long idEstado, String campoOrdenacao) {
		EntityManager em = new ConnectionFactory().getConnection();
		List<Cidade> lista = null;
		try {
			String consulta = "from Cidade where id_estado = " + idEstado + " Order By " + campoOrdenacao;
			lista = em.createQuery(consulta).getResultList();
			return lista;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			em.close();
		}
	}
}
