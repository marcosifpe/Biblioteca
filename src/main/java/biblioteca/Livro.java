package biblioteca;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import xml.AdaptadorData;

/**
 *
 * @author MASC
 */
@Entity
@Table(name = "tb_livro",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"TXT_ISBN"}, name = "UNIQUE_ISBN")})
@NamedQueries(
        {
            @NamedQuery(
                    name = Livro.LIVROS,
                    query = "SELECT l FROM Livro l ORDER BY l.titulo"
            ),
            @NamedQuery (
                    name = Livro.LIVRO_POR_ISBN,
                    query = "SELECT l FROM Livro l JOIN FETCH l.editora WHERE l.isbn = ?1"
            ),
            @NamedQuery (
                    name = Livro.LIVRO_POR_ISBN_COM_ARQUIVO,
                    query = "SELECT l FROM Livro l WHERE l.isbn = ?1 AND l.arquivoDigital != null"
            )
            
        }
)
@Access(AccessType.FIELD)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Livro extends Entidade implements Serializable {
    public static final String LIVROS = "Livros";
    public static final String LIVRO_POR_ISBN = "LivroPorIsbn";
    public static final String LIVRO_POR_ISBN_COM_ARQUIVO = "LivroPorIsbnComArquivo";
    @Expose    
    @XmlAttribute(required = true)
    @ISBN
    @Column(name = "TXT_ISBN", length = 17, nullable = false, updatable = false)
    private String isbn;
    @Expose    
    @XmlElement(required = true)
    @NotBlank
    @Size(max = 50)
    @Column(name = "TXT_TITULO", length = 50, nullable = false)
    private String titulo; 
    @Expose    
    @XmlElement(required = true)    
    @XmlJavaTypeAdapter(AdaptadorData.class)    
    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_LANCAMENTO", nullable = false)
    protected Date dataLancamento; 
    @Expose    
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(AdaptadorData.class)
    @NotNull    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CRIACAO", nullable = false)
    protected Date dataCriacao;  
    @Expose    
    @Valid
    @Embedded
    private ArquivoDigital arquivoDigital;  
    @Expose    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EDITORA", referencedColumnName = "ID", nullable = false)
    private Editora editora;   
    @Expose    
    @XmlElement(required = true)  
    @XmlElementWrapper(name="autores")
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_livro_autor", joinColumns = {
        @JoinColumn(name = "ID_LIVRO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_AUTOR")})
    private Collection<Autor> autores;    

    public Livro() {
        this.autores = new ArrayList<>();
        this.dataCriacao = new Date();
    }
    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public ArquivoDigital getArquivoDigital() {
        return arquivoDigital;
    }

    public void setArquivoDigital(ArquivoDigital arquivoDigital) {
        this.arquivoDigital = arquivoDigital;
    }

    public ArquivoDigital criarArquivoDigital() {
        return new ArquivoDigital();
    }
    
    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
        this.editora.adicionar(this);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean add(Autor autor) {
        autor.add(this);
        return autores.add(autor);
    }

    public Collection<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Collection<Autor> autores) {
        for (Autor autor : autores) {
            add(autor); //Caso haja alguma regra de negócio ela será implementada no add
        }
    }
}
