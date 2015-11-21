package jsf.beans;

import biblioteca.ArquivoDigital;
import biblioteca.Autor;
import biblioteca.Editora;
import biblioteca.Livro;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
public class LivroBean implements Serializable {

    private Livro livro;
    @EJB
    private AutorService autorService;
    @EJB
    private EditoraService editoraService;
    @EJB
    private LivroService livroService;

    private List<Editora> editoras;
    private List<Autor> autores;

    /**
     * Creates a new instance of LivroBean
     */
    public LivroBean() {
        iniciarCampos();
    }

    private void iniciarCampos() {
        this.livro = new Livro();
    }

    public void upload(FileUploadEvent event) {
        setFile(event.getFile());
    }

    private void setFile(UploadedFile file) {
        ArquivoDigital arquivoDigital = this.livro.criarArquivoDigital();
        arquivoDigital.setArquivo(file.getContents());
        arquivoDigital.setExtensao(file.getContentType());
        arquivoDigital.setNome(file.getFileName());
        this.livro.setArquivoDigital(arquivoDigital);
    }

    public Livro getLivro() {
        return livro;
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

    public void salvar() {
        try {
            livroService.salvar(livro);
            JsfUtil.adicionarMessagem("Cadastro do livro realizado com sucesso!");
        } catch (EJBException ex) {
            if (JsfUtil.entidadeExistente(ex)) {
                return;
            }

            throw ex;
        } finally {
            iniciarCampos();
        }
    }

}
