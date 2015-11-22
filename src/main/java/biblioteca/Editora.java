package biblioteca;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author MASC
 */
@Entity
@Table(name = "TB_EDITORA")
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
public class Editora extends Entidade implements Serializable {
    public static final String EDITORA_POR_NOME = "EditoraPorNome";
    public static final String EDITORAS = "Editoras";    
    @NotBlank
    @Size(max = 50)
    @Column(name = "TXT_NOME", length = 50, nullable = false)
    private String nome;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
