package jsf.beans;

import biblioteca.ArquivoDigital;
import biblioteca.Autor;
import biblioteca.Editora;
import biblioteca.Livro;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import servico.AutorServico;
import servico.EditoraServico;
import servico.LivroServico;

/**
 *
 * @author MASC
 */
@ManagedBean
@SessionScoped
public class LivroBean extends Bean<Livro> implements Serializable {
    @EJB
    private AutorServico autorServico;
    @EJB
    private EditoraServico editoraServico;
    @EJB
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
