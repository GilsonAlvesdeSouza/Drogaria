package br.com.drogaria.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe Produto
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "produtos")
public class Produto extends GenericDomain {
	@Column(length = 80, nullable = false)
	private String descricao;

	@Column(nullable = false)
	private Short quantidade;

	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal preco;

	@ManyToOne
	@JoinColumn(name = "id_fabricante", nullable = false)
	private Fabricante fabricante;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quntidade) {
		this.quantidade = quntidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

}
