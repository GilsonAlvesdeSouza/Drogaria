package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Estado;

/**
 * Classe bean do Estado
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings({ "deprecation", "serial" })
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {

	private Estado estado;
	private EstadoDAO edao;
	private List<Estado> estados;

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
	 * Método para listar o Estado
	 */
	@PostConstruct
	public void listar() {
		edao = new EstadoDAO();
		try {
			estados = edao.listar();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para criar um novo Estado
	 */
	public void novo() {
		estado = new Estado();
	}

	/**
	 * Método para editar um Estado
	 * 
	 * @param evento
	 */
	public void editar(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

	}

	/**
	 * Método para salvar o Estado
	 */
	public void salvar() {
		edao = new EstadoDAO();
		try {
			edao.merge(estado);
			Messages.addGlobalInfo("Dados salvo com sucesso!");
			estados = edao.listarOrdenado("nome");
			novo();
		} catch (javax.persistence.PersistenceException erro) {
			Messages.addGlobalError("O 'Estado' ou A 'Sigla' que você está tentando salvar já existe!");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			e.printStackTrace();
		}
	}

	/**
	 * Método para excluir um Estado
	 */
	public void excluir(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
		edao = new EstadoDAO();
		try {
			edao.excluir(estado.getCodigo());
			Messages.addGlobalInfo("Dados excluidos com sucesso!");
			estados = edao.listarOrdenado("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Excluir os dados!");
			e.printStackTrace();
		}
	}

}
