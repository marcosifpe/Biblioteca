package jsf.beans;

import biblioteca.Autor;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import servico.AutorServico;

/**
 *
 * @author MASC
 */
@ManagedBean
@ViewScoped
public class AutorBean extends Bean<Autor> implements Serializable {

    @EJB
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
