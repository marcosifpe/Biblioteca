package biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
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
@Access(AccessType.FIELD)
public class Livro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Size(max = 4)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TB_LIVRO_AUTOR", joinColumns = {
        @JoinColumn(name = "ID_LIVRO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_AUTOR")})
    private Collection<Autor> autores;    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_EDITORA", referencedColumnName = "ID", nullable = false)
    private Editora editora;

    public Livro() {
        this.autores = new ArrayList<>();
    }
    
    @PrePersist
    public void prePersist() {
        this.dataCriacao = new Date();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prova.Livro[ id=" + id + " ]";
    }

}
