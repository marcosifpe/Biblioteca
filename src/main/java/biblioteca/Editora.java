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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author MASC
 */
@Entity
@Table(name = "tb_editora")
@NamedQueries(
        {
            @NamedQuery(
                    name = Editora.EDITORAS,
                    query = "SELECT e FROM Editora e ORDER BY e.nome"
            ),
            @NamedQuery(
                    name = Editora.EDITORA_POR_NOME,
                    query = "SELECT e FROM Editora e WHERE e.nome = ?1"
            )            
        }
)
@Access(AccessType.FIELD)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Editora extends Entidade implements Serializable {
    public static final String EDITORA_POR_NOME = "EditoraPorNome";
    public static final String EDITORAS = "Editoras";   
    @Expose
    @XmlAttribute(required = true)
    @NotBlank
    @Size(max = 50)
    @Column(name = "TXT_NOME", length = 50, nullable = false, updatable = false)
    private String nome;    
    @XmlTransient
    @OneToMany(mappedBy = "editora", fetch = FetchType.LAZY)    
    private List<Livro> livros;    
    
    public Editora() {
        this.livros = new ArrayList<>();
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionar(Livro livro) {
        livros.add(livro);
    }
    
    public List<Livro> getLivros() {
        return livros;
    }
    
    @Override
    public boolean isInativo() {
        return livros.isEmpty();
    }    
}
