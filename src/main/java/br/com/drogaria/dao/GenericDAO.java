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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Método Salvar Generico
	 * 
	 * @param entidade
	 */
	public void salvar(Entidade entidade) {
		EntityManager em = new ConnectionFactory().getConnection();

		try {
			em.getTransaction().begin();
			em.persist(entidade);
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
	 * @return
	 */
	public List<Entidade> listar(){
		EntityManager em = new ConnectionFactory().getConnection();
		List<Entidade> lista = null; 
		try {
			lista = em.createQuery("from "+ classe.getSimpleName()).getResultList(); //comando "getSimpleName" Pega apenas o nome da classe em tempo de execução
			return lista;
		} catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}
	}
	
	/**
	 * Método que busca uma entidade
	 * @param codigo
	 * @return
	 */
	public Entidade buscar(Long codigo) {
		EntityManager em = new ConnectionFactory().getConnection();
		Entidade entidade = null;
		
		try {
			entidade = em.find(classe, codigo);
		} catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}
		return entidade;
	}
	
	/**
	 * Método para remover entidade
	 * @param entidade
	 */
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
	 * @param entidade
	 */
	public void editar(Entidade entidade) {
		EntityManager em = new ConnectionFactory().getConnection();

		try {
			em.getTransaction().begin();
			em.merge(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException erro) {
			em.getTransaction().rollback();
			throw erro;
		} finally {
			em.close();
		}
	}
	
}





