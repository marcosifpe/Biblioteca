package jsf.beans;

import biblioteca.Editora;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.EditoraServico;

/**
 *
 * @author MASC
 */
@ManagedBean
@ViewScoped
public class EditoraBean extends Bean<Editora> implements Serializable {
    @EJB
    private EditoraServico editoraServico;

    @Override
    protected void iniciarCampos() {
        setEntidade(editoraServico.criar());
    }

    @Override
    protected boolean salvar(Editora entidade) throws ExcecaoNegocio {
        this.editoraServico.salvar(entidade);
        return true;
    }
}
