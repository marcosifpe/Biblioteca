package biblioteca;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import json.JsonExclude;
import json.JsonExclusionStrategy;

/**
 *
 * @author MASC
 */
@MappedSuperclass
public abstract class Entidade implements Serializable {

    @JsonExclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Entidade)) {
            return false;
        }

        Entidade other = (Entidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[ id=" + id + " ]";
    }
    
    public String toJson() {
        Gson gson = new GsonBuilder().setExclusionStrategies(new JsonExclusionStrategy()).create();
        return gson.toJson(this);
    }
}
