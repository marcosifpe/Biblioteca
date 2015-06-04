package jsf.beans;

import biblioteca.Autor;
import biblioteca.Editora;
import biblioteca.Livro;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import service.AutorService;
import service.EditoraService;
import service.LivroService;

/**
 *
 * @author MASC
 */
@Named(value = "livroBean")
@ManagedBean
@SessionScoped
public class LivroBean {

    private Livro livro;
    @EJB
    private AutorService autorService;
    @EJB
    private EditoraService editoraService;
    @EJB
    private LivroService livroService;

    private List<Editora> editoras;
    private List<Autor> autores;
    private Editora editora;

    /**
     * Creates a new instance of LivroBean
     */
    public LivroBean() {
        iniciarCampos();
    }

    private void iniciarCampos() {
        this.livro = new Livro();
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

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public void salvar() {
        livroService.salvar(livro);
        iniciarCampos();
        JsfUtil.adicionarMessagem("Cadastro do livro realizado com sucesso!");
    }

}
