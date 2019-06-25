package br.com.drogaria.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * classe Cliente
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "clientes")
public class Cliente extends GenericDomain {

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(nullable = false)
	private Boolean liberado;

	@OneToOne
	@JoinColumn(name = "id_pessoa", nullable = false, unique = true)
	private Pessoa pessoa;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getLiberado() {
		return liberado;
	}

	/**
	 * Método que pega o status no banco e transforma em texto para ser exibido no
	 * datatable
	 * 
	 * @return
	 */
	public String getLiberadoFormatado() {
		String status = null;

		if (getLiberado() == false) {
			status = "Bloqueado";
		} else {
			status = "Liberado";
		}

		return status;
	}

	public void setLiberado(Boolean liberado) {
		this.liberado = liberado;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
