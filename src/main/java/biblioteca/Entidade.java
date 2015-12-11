package biblioteca;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author MASC
 */
@MappedSuperclass
public abstract class Entidade implements Serializable {

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
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("dd/MM/yyyy hh:mm:ss").create();
        return gson.toJson(this);
    }

    public boolean isInativo() {
        return false;
    }

    protected static Entidade criar(String json, Class clazz) {
        Entidade entidade = null;
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            Reader reader = new StringReader(json);
            entidade = (Entidade) gson.fromJson(reader, clazz);
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return entidade;
    }

    public void set(Entidade outra) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Class classe = this.getClass();
        for (Field atributo : classe.getDeclaredFields()) {
            atributo.setAccessible(true);
            if (!Modifier.isFinal(atributo.getModifiers())
                    && !Modifier.isStatic(atributo.getModifiers())) {
                Object valor = atributo.get(outra);
                if (valor != null && !(valor instanceof Collection)) {
                    StringBuilder nomeMetodo = new StringBuilder("set");
                    nomeMetodo.append(atributo.getName().substring(0, 1).toUpperCase());
                    nomeMetodo.append(atributo.getName().substring(1));
                    
                    Method metodo = classe.getDeclaredMethod(nomeMetodo.toString(), atributo.getType());
                    metodo.setAccessible(true);
                    metodo.invoke(this, valor);                           
                }
            }
        }
    }

}
