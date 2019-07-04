package br.com.drogaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.CellEditEvent;

import br.com.drogaria.dao.ClienteDAO;
import br.com.drogaria.dao.FuncionarioDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.dao.VendaDAO;
import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;

@SuppressWarnings({ "serial", "deprecation" })
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private ItemVenda itemVenda;
	private Venda venda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private int totalProdutos;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public ItemVenda getItemVenda() {
		return itemVenda;
	}

	public void setItemVenda(ItemVenda itemVenda) {
		this.itemVenda = itemVenda;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public int getTotalProdutos() {
		return totalProdutos;
	}

	public void setTotalProdutos(int totalProdutos) {
		this.totalProdutos = totalProdutos;
	}

	/**
	 * Método para atualizar o preço parcial na cesta de compras
	 */
	public void atualizaCesta() {
		itensVenda = getItensVenda();
	}

	@PostConstruct
	public void novo() {
		venda = new Venda();
		venda.setPrecoTotal(new BigDecimal("0.00"));
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listarOrdenado("descricao");
			itensVenda = new ArrayList<>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			erro.printStackTrace();
		}
	}

	/**
	 * Método para adicionar um Produto na cesta de compras
	 * 
	 * @param evento
	 */
	public void add(ActionEvent evento) {

		if (evento == null) {

		} else {
			Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			int achou = -1;

			for (int posicao = 0; posicao < itensVenda.size(); posicao++) {

				if (itensVenda.get(posicao).getProduto().equals(produto)) {

					achou = posicao;
				}

			}
			if (achou < 0) {
				itemVenda = new ItemVenda();
				itemVenda.setValorParcial(produto.getPreco());
				itemVenda.setProduto(produto);
				itemVenda.setQuantidade(new Short("1"));
				itensVenda.add(itemVenda);
				setTotalProdutos(itensVenda.size());
			} else {
				itemVenda = itensVenda.get(achou);
				itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
				itemVenda.setValorParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
				setTotalProdutos(itensVenda.size());
			}
			calculoTotal();
		}
	}

	/**
	 * Método para temover um produto da cesta de compras
	 */
	public void remover(ActionEvent evento) {
		itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensVenda.remove(achou);
			setTotalProdutos(itensVenda.size());
			setItensVenda(itensVenda);
		}
		calculoTotal();
	}

	public void calculoTotal() {
		venda.setPrecoTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			itemVenda = itensVenda.get(posicao);
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getValorParcial()));
		}

	}

	public void onCellEdit(CellEditEvent event) {

		Short oldValue = (Short) event.getOldValue();
		Short newValue = (Short) event.getNewValue();
		int index = (int) event.getRowIndex();

		if (newValue != null && !newValue.equals(oldValue)) {
			itemVenda = itensVenda.get(index);
			itemVenda.setValorParcial(itemVenda.getProduto().getPreco().multiply(new BigDecimal(newValue)));
			Messages.addGlobalInfo("Quantidade alterada: " + newValue + " / Novo Preço Parcial: "
					+ itensVenda.get(index).getValorParcial());
			calculoTotal();

		}
	}

	public void finalizar() {
		venda.setHorario(new Date());
		FuncionarioDAO fdao = new FuncionarioDAO();
		ClienteDAO cdao = new ClienteDAO();
		try {
			clientes = cdao.listarOrdenado("p.nome");
			funcionarios = fdao.listarOrdenado("p.nome");
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocoreeu um erro ao tentar listar o Funcionário venda!");
			e.printStackTrace();
		}
	}

	public void salvar() {
		VendaDAO vdao = new VendaDAO();
		if (venda.getPrecoTotal().signum() == 0) {
			Messages.addFlashGlobalInfo("Informe algum Item para a venda. O valor da venda não pode ser Zero!");
			return;
		}
		try {
			vdao.salvar(venda, itensVenda);
			Messages.addFlashGlobalInfo("Dados Salvos com Sucesso!");

		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			e.printStackTrace();
		}

	}

}
