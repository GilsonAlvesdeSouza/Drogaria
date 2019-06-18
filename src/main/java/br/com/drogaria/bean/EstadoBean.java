package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Estado;

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

	public void novo() {
		estado = new Estado();
	}

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

	public void salvar() {
		edao = new EstadoDAO();
		try {
			edao.salvar(estado);
			Messages.addGlobalInfo("Dados salvo com sucesso!");
			novo();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			e.printStackTrace();
		}
	}
}
