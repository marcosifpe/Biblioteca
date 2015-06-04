package jsf.beans;

import biblioteca.Entidade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author MASC
 */
@FacesConverter(value = "entidadeConverter")
public class EntidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Entidade) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Entidade) {
            component.getAttributes().put(((Entidade) entity).getId().toString(), entity);
            return ((Entidade) entity).getId().toString();
        }

        return null;
    }
}
