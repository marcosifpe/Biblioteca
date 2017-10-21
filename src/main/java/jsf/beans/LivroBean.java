package jsf.beans;

import biblioteca.ArquivoDigital;
import biblioteca.Autor;
import biblioteca.Editora;
import biblioteca.Livro;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import servico.AutorServico;
import servico.EditoraServico;
import servico.LivroServico;

/**
 *
 * @author MASC
 */
@SessionScoped
@Named
public class LivroBean extends Bean<Livro> implements Serializable {
    @Inject
    private AutorServico autorServico;
    @Inject
    private EditoraServico editoraServico;
    @Inject
    private LivroServico livroServico;

    @Override
    protected void iniciarCampos() {
        setEntidade(livroServico.criar());
    }

    public void upload(FileUploadEvent event) {
        setFile(event.getFile());
    }

    private void setFile(UploadedFile file) {
        ArquivoDigital arquivoDigital = entidade.criarArquivoDigital();
        arquivoDigital.setArquivo(file.getContents());
        arquivoDigital.setExtensao(file.getContentType());
        arquivoDigital.setNome(file.getFileName());
        entidade.setArquivoDigital(arquivoDigital);
    }

    public List<Autor> getAutores() {
        return autorServico.getAutores();
    }

    public List<Editora> getEditoras() {
        return editoraServico.getEditoras();
    }

    @Override
    protected boolean salvar(Livro entidade) throws ExcecaoNegocio {
        this.livroServico.salvar(entidade);
        return true;
    }
}
