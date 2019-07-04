package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.ClienteDAO;
import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Pessoa;

@SuppressWarnings({ "serial", "deprecation" })
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private List<Cliente> clientes;
	private Cliente cliente;
	private List<Pessoa> pessoas;
	private Pessoa pessoa;

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * Método que lista os clientes
	 */
	@PostConstruct
	public void listar() {
		ClienteDAO cdao = new ClienteDAO();
		try {
			clientes = cdao.listarOrdenado("p.nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			erro.printStackTrace();
		}
	}

	public void novo() {
		cliente = new Cliente();
		PessoaDAO pdao = new PessoaDAO();
		try {
			pessoas = pdao.listarOrdenado("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		ClienteDAO cdao = new ClienteDAO();
		try {
			cdao.merge(cliente);
			novo();
			listar();
			Messages.addFlashGlobalInfo("Dados salvos com sucesso!");
		} catch (javax.persistence.PersistenceException erro) {
			Messages.addGlobalError("A 'Pessoa' que você está tentando salvar já existe!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
		pessoa = cliente.getPessoa();
		PessoaDAO pdao = new PessoaDAO();
		pessoas = pdao.listarOrdenado("nome");
	}

	public void excluir(ActionEvent evento) {
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
		ClienteDAO cdao = new ClienteDAO();
		PessoaDAO pdao = new PessoaDAO();
		try {
			cdao.excluir(cliente.getCodigo());
			listar();
			pessoas = pdao.listarOrdenado("nome");
			Messages.addGlobalInfo("Dados excluidos com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir e Pessoa!");
			erro.printStackTrace();
		}
	}
}
