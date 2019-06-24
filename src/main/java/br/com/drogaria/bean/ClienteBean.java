package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

	/**
	 * Método que lista os clientes
	 */
	@PostConstruct
	public void listar() {
		ClienteDAO cdao = new ClienteDAO();
		try {
			clientes = cdao.listarOrdenado("dataCadastro");
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

	}

	public void editar() {

	}

	public void excluir() {

	}

}
