/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import biblioteca.Livro;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.LivroServico;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "livrosPaginator")
@ViewScoped
public class LivrosPaginator implements Serializable {

    @EJB
    private LivroServico livroServico;

    private List<Livro> livros;

    public List<Livro> getLivros() {
        if (livros == null) {
            livros = livroServico.getLivros();
        }

        return livros;
    }
}
