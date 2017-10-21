package jsf.beans;

import biblioteca.Autor;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import servico.AutorServico;

/**
 *
 * @author MASC
 */
@ViewScoped
@Named
public class AutorBean extends Bean<Autor> implements Serializable {

    @Inject
    private AutorServico servicoAutor;

    @Override
    protected void iniciarCampos() {
        setEntidade(servicoAutor.criar());
    }

    @Override
    protected boolean salvar(Autor entidade) throws ExcecaoNegocio {
        servicoAutor.salvar(entidade);
        return true;
    }
}
