/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import biblioteca.Livro;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.LivroService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "livrosPaginator")
@ViewScoped
public class LivrosPaginator {

    @EJB
    private LivroService livroService;

    private List<Livro> livros;

    public List<Livro> getLivros() {
        if (livros == null) {
            livros = livroService.getLivros();
        }

        return livros;
    }
}
