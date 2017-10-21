package jsf.beans;

import biblioteca.Editora;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import servico.EditoraServico;

/**
 *
 * @author MASC
 */
@ViewScoped
@Named
public class EditoraBean extends Bean<Editora> implements Serializable {
    @Inject 
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
