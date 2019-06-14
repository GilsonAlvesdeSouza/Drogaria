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
 * Classe Funcionários
 * @author gilsonalves
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "funcionarios")
public class Funcionario extends GenericDomain{

	@Column(length = 15, nullable = false, unique = true)
	private String carteiraTrabalho;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataAdmissao;
	
	@OneToOne
	@JoinColumn(nullable = false, name = "id_pessoa")
	private Pessoa pessoa;
}

















