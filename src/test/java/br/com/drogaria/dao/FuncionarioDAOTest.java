package br.com.drogaria.dao;

import java.util.List;

import org.junit.Test;

import br.com.drogaria.domain.Funcionario;

public class FuncionarioDAOTest {

	@Test
	public void listar() {
		FuncionarioDAO dao = new FuncionarioDAO();
		List<Funcionario> funcionarios;
		funcionarios = dao.listarOrdenado("p.nome");
		for (Funcionario f : funcionarios) {
			System.out.println(f.getPessoa().getNome() + " / " + f.getDataAdmissao() + " / " + f.getPessoa().getCPF());
		}
	}
}
