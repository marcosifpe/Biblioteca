package biblioteca;

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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author MASC
 */
@Entity
@Table(name = "TB_LIVRO",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"TXT_ISBN"}, name = "UNIQUE_ISBN")})
@NamedQueries(
        {
            @NamedQuery(
                    name = Livro.LIVROS,
                    query = "SELECT l FROM Livro l ORDER BY l.titulo"
            ),
            @NamedQuery (
                    name = "LivroPorIsbn",
                    query = "SELECT l FROM Livro l WHERE l.isbn = ?1"
            )
        }
)
@Access(AccessType.FIELD)
public class Livro extends Entidade implements Serializable {
    public static final String LIVROS = "Livros";
    public static final String LIVRO_POR_ISBN = "LivroPorIsbn";
    @NotBlank
    @Size(max = 17)
    @Pattern(regexp = "[0-9]{3}-[0-9]{2}-[0-9]{4}-[0-9]{3}-[0-9]{1}")
    @Column(name = "TXT_ISBN", length = 17, nullable = false)
    private String isbn;
    @NotBlank
    @Size(max = 100)
    @Column(name = "TXT_TITULO", length = 100, nullable = false)
    private String titulo;
    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_LANCAMENTO", nullable = false)
    protected Date dataLancamento;
    @NotNull    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CRIACAO", nullable = false)
    protected Date dataCriacao;    
    @Valid
    @Embedded
    private ArquivoDigital arquivoDigital;
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_LIVRO_AUTOR", joinColumns = {
        @JoinColumn(name = "ID_LIVRO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_AUTOR")})
    private Collection<Autor> autores;    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EDITORA", referencedColumnName = "ID", nullable = false)
    private Editora editora;

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
