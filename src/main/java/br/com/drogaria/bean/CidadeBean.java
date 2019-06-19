package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.CidadeDAO;
import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;

/**
 * classe bean da Cidade
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings({ "deprecation", "serial" })
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {

	private Cidade cidade;
	private CidadeDAO cdao;
	private EstadoDAO edao;
	private List<Cidade> cidades;
	private List<Estado> estados;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public CidadeDAO getCdao() {
		return cdao;
	}

	public void setCdao(CidadeDAO cdao) {
		this.cdao = cdao;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public void novo() {
		cidade = new Cidade();
		edao = new EstadoDAO();
		try {
			estados = edao.listarOrdenado("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Criar uma nova Cidade!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para listar o Estado
	 */
	@PostConstruct
	public void listar() {
		cdao = new CidadeDAO();
		try {
			cidades = cdao.listarOrdenado("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para salvar o Estado
	 */
	public void salvar() {
		cdao = new CidadeDAO();
		edao = new EstadoDAO();
		try {
			cdao.merge(cidade);
			estados = edao.listarOrdenado("nome");
			cidades = cdao.listarOrdenado("nome");
			Messages.addGlobalInfo("Dados salvo com sucesso!");
			novo();
		} catch (javax.persistence.PersistenceException erro) {
			Messages.addGlobalError("A cidade que você está tentando salvar já existe!");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para excluir um Estado
	 */
	public void excluir(ActionEvent evento) {
		cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
		cdao = new CidadeDAO();
		try {
			cdao.excluir(cidade.getCodigo());
			Messages.addGlobalInfo("Dados excluidos com sucesso!");
			cidades = cdao.listarOrdenado("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Excluir os dados!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para editar um Estado
	 * 
	 * @param evento
	 */
	public void editar(ActionEvent evento) {
		cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
		edao = new EstadoDAO();
		try {
			estados = edao.listarOrdenado("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma  Cidade!");
			e.printStackTrace();
		}

	}
}
