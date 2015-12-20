package jsf.beans;

import biblioteca.Editora;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.EditoraService;

/**
 *
 * @author MASC
 */
@ManagedBean
@ViewScoped
public class EditoraBean extends Bean<Editora> implements Serializable {
    @EJB
    private EditoraService editoraService;

    @Override
    protected void iniciarCampos() {
        setEntidade(editoraService.criar());
    }

    @Override
    protected boolean salvar(Editora entidade) throws ExcecaoNegocio {
        this.editoraService.salvar(entidade);
        return true;
    }
}
