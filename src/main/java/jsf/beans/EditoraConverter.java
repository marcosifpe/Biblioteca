package jsf.beans;

import biblioteca.Editora;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author MASC
 */
@FacesConverter(forClass = Editora.class)
public class EditoraConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Editora) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Editora) {
            component.getAttributes().put(((Editora) entity).getId().toString(), entity);
            return ((Editora) entity).getId().toString();
        }

        return null;
    }
}
