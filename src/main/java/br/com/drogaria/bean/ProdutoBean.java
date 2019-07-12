package br.com.drogaria.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.UploadedFile;

import br.com.drogaria.conection.ConnectionFactory;
import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings({ "serial", "deprecation" })
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {
	private Produto produto;
	private List<Produto> produtos;
	private List<Fabricante> fabricantes;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	/**
	 * Método para listar o Produto
	 */
	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listarOrdenado("descricao");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados!");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que cria um novo produto
	 */
	public void novo() {
		try {
			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarOrdenado("descricao");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar criar um novo produto");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que edita um novo produto pela passagem de um objeto
	 * 
	 * @param evento
	 */
	public void editar(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			produto.setCaminho("/imagensDrogaria/" + produto.getCodigo() + ".png");
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listarOrdenado("descricao");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um produto");
			erro.printStackTrace();
		}
	}

	/**
	 * Método que salva um novo Produto
	 */
	public void salvar() {
		try {
			if (produto.getCaminho() == null) {
				Messages.addGlobalError("O campo foto é obrigatório");
				return;
			}
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto retorno = produtoDAO.merge(produto);
			Path origem = Paths.get(produto.getCaminho());
			Path destino = Paths.get("/imagensDrogaria/" + retorno.getCodigo() + ".png");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			novo();
			listar();
			Messages.addGlobalInfo("Produto salvo com sucesso");
		} catch (javax.persistence.PersistenceException erro) {
			Messages.addGlobalError("O Produto que você está tentando salvar já existe!");
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar os dados!");
			erro.printStackTrace();
		}
	}

	/**
	 * Método para excluir um Produto
	 * 
	 * @param evento
	 */
	public void excluir(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto.getCodigo());
			listar();
			Path destino = Paths.get("/imagensDrogaria/" + produto.getCodigo() + ".png");
			Files.deleteIfExists(destino);
			Messages.addGlobalInfo("Dados excluidos com sucesso");
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir o produto");
			erro.printStackTrace();
		}
	}

	/**
	 * Método para mover linhas do DataTable
	 * 
	 * @param event
	 */
	public void moverLinha(ReorderEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Linha Movida",
				"From: " + event.getFromIndex() + ", To:" + event.getToIndex());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void imagemUpload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUP = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUP.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			produto.setCaminho(arquivoTemp.toString());
			Messages.addFlashGlobalInfo("Imagem Carregada com sucesso!");
		} catch (IOException erro) {
			Messages.addFlashGlobalInfo("Ocorreu um erro ao carregar a imagem!");
		}
	}

	public void imprimir() throws SQLException {
		String imagePath = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/resources/imagens/banner.jpg");
		String caminho = Faces.getRealPath("/reports/produto.jasper");
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("LOGO", imagePath);
		parametros.put("PRODUTO_DESCRICAO", "%%");
		parametros.put("FABRICANTE_DESCRICAO", "%%");
		try {
			Connection conexao = ConnectionFactory.getConnectionJDBC();
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException e) {
			Messages.addFlashGlobalInfo("Ocorreu um erro ao gerar o Relatório");
			e.printStackTrace();
		}
	}

}
