package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;

public class CidadeDAOTeste {

	@Test
	@Ignore
	public void salvar() {
		EstadoDAO edao = new EstadoDAO();
		Estado e = edao.buscar(1l);
		Cidade c = new Cidade();
		CidadeDAO cdao = new CidadeDAO();
		c.setNome("Goiania");
		c.setEstado(e);

		cdao.salvar(c);
	}

	@Test
	public void listar() {
		List<Cidade> cds;
		CidadeDAO cdao = new CidadeDAO();
		cds = cdao.listar();

		for (Cidade c : cds) {
			System.out.println(c.getNome());
			System.out.println(c.getEstado().getNome());
			System.out.println();
		}
	}
}
