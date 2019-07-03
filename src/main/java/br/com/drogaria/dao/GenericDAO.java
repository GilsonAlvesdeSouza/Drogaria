package br.com.drogaria.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.drogaria.conection.ConnectionFactory;

/**
 * Classe Generica DAO
 * 
 * @author gilsonalves
 *
 * @param <Entidade>
 */
public class GenericDAO<Entidade> {
	private Class<Entidade> classe;
	String f = null;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Método Salvar Generico
	 * 
	 * @param entidade
	 */
	@SuppressWarnings("static-access")
	public void salvar(Entidade entidade) {
		EntityManager em = new ConnectionFactory().getConnection();

		try {
			em.getTransaction().begin();
			if (entidade != null) {
				em.persist(entidade);
			}
			em.getTransaction().commit();
		} catch (RuntimeException erro) {
			em.getTransaction().rollback();
			throw erro;
		} finally {
			em.close();
		}
	}

	/**
	 * Método que lista a entidade
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<Entidade> listar() {
		EntityManager em = new ConnectionFactory().getConnection();
		List<Entidade> lista = null;
		try {
			lista = em.createQuery("from " + classe.getSimpleName()).getResultList(); // comando "getSimpleName" Pega
																						// apenas o nome da classe em
																						// tempo de execução
			return lista;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			em.close();
		}
	}

	/**
	 * Método que busca uma entidade
	 * 
	 * @param codigo
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Entidade buscar(Long codigo) {
		EntityManager em = new ConnectionFactory().getConnection();
		Entidade entidade = null;

		try {
			entidade = em.find(classe, codigo);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			em.close();
		}
		return entidade;
	}

	/**
	 * Método para remover entidade
	 * 
	 * @param entidade
	 */

	@SuppressWarnings("static-access")
	public void excluir(Long codigo) {
		EntityManager em = new ConnectionFactory().getConnection();
		try {
			em.getTransaction().begin();
			Entidade entidade = em.find(classe, codigo);
			em.remove(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException erro) {
			em.getTransaction().rollback();
			throw erro;
		} finally {
			em.close();
		}
	}

	/**
	 * Método para editar uma entidade
	 * 
	 * @param entidade
	 */
	@SuppressWarnings("static-access")
	public Entidade merge(Entidade entidade) {
		EntityManager em = new ConnectionFactory().getConnection();
		Entidade retorno = null;
		try {
			em.getTransaction().begin();
			if (entidade != null) {
				retorno = (Entidade) em.merge(entidade);
			}
			em.getTransaction().commit();
			return retorno;
		} catch (RuntimeException erro) {
			em.getTransaction().rollback();
			throw erro;
		} finally {
			em.close();
		}
	}

	/**
	 * Método que lista Ordenado a entidade (é preciso que imforme o campo ao qual
	 * deseja ser ordenado)
	 * 
	 * @return
	 */

	@SuppressWarnings({ "static-access", "unchecked" })
	public List<Entidade> listarOrdenado(String campo) {
		EntityManager em = new ConnectionFactory().getConnection();
		List<Entidade> lista = null;
		try {
			String consulta = "FROM " + classe.getSimpleName() + " ORDER BY " + campo;
			lista = em.createQuery(consulta).getResultList();
			return lista;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			em.close();
		}
	}

}
