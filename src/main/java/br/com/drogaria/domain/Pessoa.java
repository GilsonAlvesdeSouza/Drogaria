package br.com.drogaria.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe Pessoa
 * 
 * @author gilsonalves
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pessoas")
public class Pessoa extends GenericDomain {

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 18, nullable = false, unique = true)
	private String CPF;

	@Column(length = 15, nullable = false, unique = true)
	private String RG;

	@Column(length = 50, nullable = false)
	private String rua;

	@Column
	private short numero;

	@Column(length = 50, nullable = false)
	private String bairro;

	@Column(length = 10)
	private String CEP;

	@Column(length = 100)
	private String complemento;

	@Column(length = 15)
	private String telefone;

	@Column(length = 15, nullable = false)
	private String celular;

	@Column(length = 100)
	private String email;

	@ManyToOne
	@JoinColumn(name = "id_cidade", nullable = false)
	private Cidade cidade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public short getNumero() {
		return numero;
	}

	public void setNumero(short numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
