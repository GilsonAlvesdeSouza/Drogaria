import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@SuppressWarnings("deprecation")
@ManagedBean
@RequestScoped
public class ImagemBean {
	@ManagedProperty("#{param.caminho}")
	private String caminho;

	private StreamedContent imagem;

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public StreamedContent getImagem() throws IOException {
		if (caminho == null || caminho.isEmpty()) {
			Path patch = Paths.get("/imagensDrogaria/branco.png");
			InputStream strean = Files.newInputStream(patch);
			imagem = new DefaultStreamedContent(strean);
		} else {
			Path patch = Paths.get(caminho);
			InputStream strean = Files.newInputStream(patch);
			imagem = new DefaultStreamedContent(strean);
		}
		return imagem;
	}

	public void setImagem(StreamedContent imagem) throws IOException {

		this.imagem = imagem;
	}

}
