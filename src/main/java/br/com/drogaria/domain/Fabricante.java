package br.com.drogaria.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "fabricantes")
public class Fabricante extends GenericDomain{
	
	@Column(name = "fabricantes", length = 80, nullable = false, unique = true)
	private String fabricante;

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
}
