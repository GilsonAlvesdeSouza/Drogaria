package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.dao.UsuarioDAO;
import br.com.drogaria.domain.Pessoa;
import br.com.drogaria.domain.Usuario;

/**
 * classe bean da Cidade
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings({ "deprecation", "serial" })
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Pessoa> pessoas;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	/**
	 * Método que cria um novo Usuário
	 */
	public void novo() {
		usuario = new Usuario();
		PessoaDAO pdao = new PessoaDAO();
		try {
			pessoas = pdao.listarOrdenado("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que lista os Usuários
	 */
	@PostConstruct
	public void listar() {
		UsuarioDAO udao = new UsuarioDAO();
		try {
			usuarios = udao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			erro.printStackTrace();
		}
	}

	/**
	 * Método para salvar o Usuário
	 */
	public void salvar() {
		PessoaDAO pdao = new PessoaDAO();
		UsuarioDAO udao = new UsuarioDAO();
		try {
			udao.merge(usuario);
			pessoas = pdao.listarOrdenado("nome");
			Messages.addGlobalInfo("Dados salvo com sucesso!");
			novo();
			listar();
		} catch (javax.persistence.PersistenceException erro) {
			Messages.addGlobalError("O Usuario que você está tentando salvar já existe!");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para excluir um Usuario
	 */
	public void excluir(ActionEvent evento) {
		usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
		UsuarioDAO udao = new UsuarioDAO();
		try {
			udao.excluir(usuario.getCodigo());
			Messages.addGlobalInfo("Dados excluidos com sucesso!");
			listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Excluir os dados!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para editar um Usuario
	 * 
	 * @param evento
	 */
	public void editar(ActionEvent evento) {
		usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
		try {
			listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma  Pessoa!");
			e.printStackTrace();
		}

	}
}
