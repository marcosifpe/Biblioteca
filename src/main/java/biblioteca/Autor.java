package biblioteca;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author MASC
 * Essa classe... bla bla blab de novo
 */
@Entity
@Table(name = "TB_AUTOR",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"TXT_CPF"}, name = "UNIQUE_CPF")})
@NamedQueries(
        {
            @NamedQuery(
                    name = Autor.AUTORES,
                    query = "SELECT a FROM Autor a ORDER BY a.primeiroNome, a.ultimoNome"
            ),
            @NamedQuery(
                    name = Autor.AUTOR_POR_CPF,
                    query = "SELECT a FROM Autor a WHERE a.cpf = ?1"
            )            
        }
)
@Access(AccessType.FIELD)
public class Autor extends Entidade implements Serializable {
    public static final String AUTOR_POR_CPF = "AutorPorCpf";
    public static final String AUTORES = "Autores";
    @Expose
    @Size(max = 20)
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "{biblioteca.Autor.nome}")
    @Column(name = "TXT_PRIMEIRO_NOME", length = 20, nullable = false)
    private String primeiroNome;
    @Expose    
    @Size(max = 20)
    @Pattern(regexp = "[A-Z]{1}[a-z]+", message = "{biblioteca.Autor.nome}")
    @Column(name = "TXT_ULTIMO_NOME", length = 20, nullable = false)
    private String ultimoNome;
    @Expose    
    @CPF
    @Column(name = "TXT_CPF", length = 14, nullable = false, updatable = false)
    private String cpf;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private List<Livro> livros;

    public Autor(String json) {
        super(json);        
        this.livros = new ArrayList<>();
    }
    
    public Autor() {
        this.livros = new ArrayList<>();
    }
    
    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getNome() {
        return this.primeiroNome + " " + this.ultimoNome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public boolean add(Livro livro) {
        return livros.add(livro);
    }
    
    @Override
    public boolean isInativo() {
        return this.livros.isEmpty();
    }
}
