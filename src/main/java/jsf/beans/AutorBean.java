package jsf.beans;

import biblioteca.Autor;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.AutorService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "autorBean")
@ViewScoped
public class AutorBean extends Bean<Autor> implements Serializable {
    @EJB
    private AutorService autorService;

    @Override
    protected void iniciarCampos() {
        this.entidade = new Autor();
    }

    @Override
    protected void salvar(Autor entidade) {
        autorService.salvar(entidade);
    }
}
