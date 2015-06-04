package biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 */
@Entity
@Table(name = "TB_AUTOR",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"TXT_CPF"}, name = "UNIQUE_CPF")})
@NamedQueries(
        {
            @NamedQuery(
                    name = "Autores",
                    query = "SELECT a FROM Autor a ORDER BY a.primeiroNome, a.ultimoNome"
            )
        }
)
@Access(AccessType.FIELD)
public class Autor extends Entidade implements Serializable {
    @Size(max = 20)
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "{biblioteca.Autor.nome}")
    @Column(name = "TXT_PRIMEIRO_NOME", length = 20, nullable = false)
    private String primeiroNome;
    @Size(max = 20)
    @Pattern(regexp = "[A-Z]{1}[a-z]+", message = "{biblioteca.Autor.nome}")    
    @Column(name = "TXT_ULTIMO_NOME", length = 20, nullable = false)
    private String ultimoNome;
    @CPF
    @Column(name = "TXT_CPF", length = 14, nullable = false)
    private String cpf;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private List<Livro> livros;
    
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
}
