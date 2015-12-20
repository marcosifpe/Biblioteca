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
import service.AutorService;
import service.EditoraService;
import service.LivroService;

/**
 *
 * @author MASC
 */
@ManagedBean
@SessionScoped
public class LivroBean extends Bean<Livro> implements Serializable {
    @EJB
    private AutorService autorService;
    @EJB
    private EditoraService editoraService;
    @EJB
    private LivroService livroService;

    @Override
    protected void iniciarCampos() {
        setEntidade(livroService.criar());
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
        return autorService.getAutores();
    }

    public List<Editora> getEditoras() {
        return editoraService.getEditoras();
    }

    @Override
    protected boolean salvar(Livro entidade) throws ExcecaoNegocio {
        this.livroService.salvar(entidade);
        return true;
    }
}
