package br.com.drogaria.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Classe Fabricantes
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "fabricantes")
public class Fabricante extends GenericDomain {

	@Column(length = 80, nullable = false, unique = true)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String fabricante) {
		this.descricao = fabricante;
	}

}
