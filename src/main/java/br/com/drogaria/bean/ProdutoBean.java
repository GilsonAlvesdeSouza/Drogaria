package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.ReorderEvent;

import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;

@SuppressWarnings({ "serial", "deprecation" })
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {
	private Produto produto;
	private List<Produto> produtos;
	private List<Fabricante> fabricantes;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	/**
	 * Método para listar o Produto
	 */
	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listarOrdenado("descricao");
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
			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarOrdenado("descricao");
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
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarOrdenado("descricao");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um produto");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que salva um novo Produto
	 */
	public void salvar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.merge(produto);
			novo();
			listar();
			Messages.addGlobalInfo("Produto salvo com sucesso");
		} catch (javax.persistence.PersistenceException erro) {
			Messages.addGlobalError("O Produto que você está tentando salvar já existe!");
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
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto.getCodigo());
			listar();
			Messages.addGlobalInfo("Dados excluidos com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir o produto");
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

}
