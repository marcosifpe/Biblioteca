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
@FacesConverter(value="editoraConverter")
public class EditoraConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LivroBean livroBean = (LivroBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "livroBean");        
        //Editora editora = livroBean.getEditora(Long.valueOf(value));
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
