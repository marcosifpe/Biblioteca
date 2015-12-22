package biblioteca;

import com.google.gson.annotations.Expose;
import excecao.ExcecaoSistema;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author MASC
 */
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Entidade implements Serializable {
    @Expose
    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    public Entidade() {
        
    }

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

    public boolean isInativo() {
        return false;
    }

    public void setAtributos(Entidade entidade) {
        try {
            Class classe = this.getClass();
            
            for (Field atributo : classe.getDeclaredFields()) {
                atributo.setAccessible(true);
                if (!Modifier.isFinal(atributo.getModifiers())
                        && !Modifier.isStatic(atributo.getModifiers())) {
                    Object valor = atributo.get(entidade);
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
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            throw new ExcecaoSistema(ex);
        }
    }

}
