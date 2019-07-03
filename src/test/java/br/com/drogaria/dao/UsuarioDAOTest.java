package br.com.drogaria.dao;

import java.util.List;

import org.junit.Test;

import br.com.drogaria.domain.Usuario;

public class UsuarioDAOTest {

	@Test
	public void listar() {
		UsuarioDAO udao = new UsuarioDAO();
		List<Usuario> usuarios;
		usuarios = udao.listarOrdenado("p.nome");
		System.out.println(usuarios.size());

		for (Usuario u : usuarios) {
			System.out.println(u.getPessoa().getNome() + " / " + u.getPessoa().getCPF() + " / " + u.getSenha());
		}
	}
}
