package br.com.drogaria.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Classe Usuario
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "usuarios")
public class Usuario extends GenericDomain {

	@Column(length = 32, nullable = false)
	private String senha;

	@Column(nullable = false)
	private Character tipo;

	@Column(nullable = false)
	private Boolean ativo;

	@OneToOne
	@JoinColumn(name = "id_pessoa", nullable = false)
	private Pessoa pessoa;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método que pega o Tipo no banco e transforma em texto para ser exibido no
	 * datatable
	 * 
	 * @return
	 */
	public String getTipoFormatado() {
		String tipo = null;
		if (getTipo() == 'A') {
			tipo = "Administrador";
		} else if (getTipo() == 'G') {
			tipo = "Gerente";
		} else if (getTipo() == 'B') {
			tipo = "Balconista";
		}
		return tipo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	/**
	 * Método que pega o status no banco e transforma em texto para ser exibido no
	 * datatable
	 * 
	 * @return
	 */
	public String getAtivoFormatado() {
		String status = null;

		if (getAtivo() == false) {
			status = "Inativo";
		} else {
			status = "Ativo";
		}

		return status;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
