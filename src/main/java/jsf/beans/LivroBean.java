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
    
    private String editoraId;
    private List<String> autoresIds;
    
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
        if (this.editoras == null)
            this.editoras = editoraService.getEditoras();
        
        return this.editoras;
    }
    
    private Editora getEditora(Long id) {
        for (Editora e : getEditoras()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        
        return null;
    }
    
    private Autor getAutor(Long id) {
        for (Autor a : autores) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        
        return null;
    }
    
    public String getEditoraId() {
        return editoraId;
    }

    public void setEditoraId(String editoraId) {
        this.editoraId = editoraId;
    }

    public List<String> getAutoresIds() {
        return autoresIds;
    }

    public void setAutoresIds(List<String> autoresIds) {
        this.autoresIds = autoresIds;
    }
    
    public void salvar() {
        livro.setEditora(getEditora(Long.valueOf(this.editoraId)));
        
        for (String autorId : autoresIds) {
            Autor autor = getAutor(Long.valueOf(autorId));
            livro.add(autor);
        }
        
        livroService.salvar(livro);
        iniciarCampos();
        JsfUtil.adicionarMessagem("Cadastro do livro realizado com sucesso!");
    }
    
    
}
