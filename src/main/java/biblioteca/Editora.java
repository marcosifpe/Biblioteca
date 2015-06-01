package biblioteca;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
                    name = "Editoras",
                    query = "SELECT e FROM Editora e ORDER BY e.nome"
            )
        }
)
@Access(AccessType.FIELD)
public class Editora implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 50)
    @Column(name = "TXT_NOME", length = 50, nullable = false)
    private String nome;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Editora)) {
            return false;
        }
        Editora other = (Editora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
