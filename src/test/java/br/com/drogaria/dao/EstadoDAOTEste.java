package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Estado;

public class EstadoDAOTEste {

	@Test
	@Ignore
	public void salvar() {

		Estado e = new Estado();
		EstadoDAO eDAO = new EstadoDAO();
		e.setSigla("RJ");
		e.setNome("Rio De Janeiro");
		try {
			eDAO.salvar(e);
			System.out.println("Dados Salvos com sucesso!");

		} catch (Exception e2) {
			System.out.println("Erro ao salvar os dados! " + e2);
		}
	}

	@Test
	@Ignore
	public void listar() {
		EstadoDAO eDAO = new EstadoDAO();
		List<Estado> resultado = eDAO.listar();
		for (Estado estado : resultado) {
			System.out.println(estado.getCodigo());
			System.out.println(estado.getNome());
			System.out.println(estado.getSigla());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Long codigo = 1l;
		EstadoDAO dao = new EstadoDAO();
		Estado es = dao.buscar(codigo);
		System.out.println(es.getNome());
	}

	@Test
	@Ignore
	public void editar() {
		Long codigo = 5l;
		EstadoDAO dao = new EstadoDAO();
		Estado es = dao.buscar(codigo);
		es.setNome("Brasilia");
		es.setSigla("DF");
		dao.editar(es);
	}

	@Test
//	@Ignore
	public void excluir() {
		Long codigo = 2l;
		EstadoDAO dao = new EstadoDAO();
		try {
			dao.excluir(codigo);
		} catch (Exception e) {
			System.out.println("Erro:" + e);
		}
	}
}
