package jsf.beans;

import biblioteca.Autor;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.AutorService;

/**
 *
 * @author MASC
 */
@ManagedBean
@ViewScoped
public class AutorBean extends Bean<Autor> implements Serializable {

    @EJB
    private AutorService autorService;

    @Override
    protected void iniciarCampos() {
        setEntidade(autorService.criar());
    }

    @Override
    protected boolean salvar(Autor entidade) throws ExcecaoNegocio {
        autorService.salvar(entidade);
        return true;
    }
}
