package jsf.beans;

import biblioteca.ArquivoDigital;
import biblioteca.Autor;
import biblioteca.Editora;
import biblioteca.Livro;
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
@ManagedBean(name = "livroBean")
@SessionScoped
public class LivroBean extends Bean<Livro> implements Serializable {
    @EJB
    private AutorService autorService;
    @EJB
    private EditoraService editoraService;
    @EJB
    private LivroService livroService;

    private List<Editora> editoras;
    private List<Autor> autores;

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
        if (this.autores == null) {
            this.autores = autorService.getAutores();
        }

        return this.autores;
    }

    public List<Editora> getEditoras() {
        if (this.editoras == null) {
            this.editoras = editoraService.getEditoras();
        }

        return this.editoras;
    }

    @Override
    protected boolean salvar(Livro entidade) {
        this.livroService.salvar(entidade);
        return true;
    }
}
