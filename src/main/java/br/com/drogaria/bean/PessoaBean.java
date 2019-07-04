package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.ReorderEvent;

import br.com.drogaria.dao.CidadeDAO;
import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;
import br.com.drogaria.domain.Pessoa;

@SuppressWarnings({ "serial", "deprecation" })
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	private List<Cidade> cidades;
	private Estado estado;
	private List<Estado> estados;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	/**
	 * Método para listar o Produto
	 */
	@PostConstruct
	public void listar() {
		try {
			PessoaDAO pdao = new PessoaDAO();
			pessoas = pdao.listarOrdenado("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que cria um novo produto
	 */
	public void novo() {
		try {
			pessoa = new Pessoa();
			estado = new Estado();
			EstadoDAO edao = new EstadoDAO();
			estados = edao.listarOrdenado("nome");
			cidades = new ArrayList<Cidade>();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar criar um novo produto");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que edita um novo produto pela passagem de um objeto
	 * 
	 * @param evento
	 */
	public void editar(ActionEvent evento) {
		pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
		try {
			EstadoDAO edao = new EstadoDAO();
			estados = edao.listar();
			CidadeDAO cdao = new CidadeDAO();
			cidades = cdao.listar();
			estado = pessoa.getCidade().getEstado();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma Pessoa");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que salva um novo Produto
	 */
	public void salvar() {
		try {
			PessoaDAO pdao = new PessoaDAO();
			pdao.merge(pessoa);
			listar();
			novo();
			Messages.addGlobalInfo("Dados salvo com sucesso!");
		} catch (javax.persistence.PersistenceException erro) {
			Messages.addGlobalError("O 'CPF' que você está tentando salvar já existe!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			erro.printStackTrace();
		}
	}

	/**
	 * Método para excluir um Produto
	 * 
	 * @param evento
	 */
	public void excluir(ActionEvent evento) {
		PessoaDAO pdao = new PessoaDAO();
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			pdao.excluir(pessoa.getCodigo());
			listar();
			EstadoDAO edao = new EstadoDAO();
			estados = edao.listar();
			Messages.addGlobalInfo("Dados excluidos com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir e Pessoa!");
			erro.printStackTrace();
		}
	}

	/**
	 * Método para mover linhas do DataTable
	 * 
	 * @param event
	 */
	public void moverLinha(ReorderEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Linha Movida",
				"From: " + event.getFromIndex() + ", To:" + event.getToIndex());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void popular() {
		CidadeDAO cdao = new CidadeDAO();
		try {
			if (estado != null) {
				cidades = cdao.bucarPorEstado(estado.getCodigo(), "nome");
			}
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um ao tentar listar as cidades!");
			erro.printStackTrace();
		}
	}

}
