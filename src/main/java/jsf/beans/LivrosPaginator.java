/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import biblioteca.Livro;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import servico.LivroServico;

/**
 *
 * @author MASC
 */
@RequestScoped
@Named("livrosPaginator")
public class LivrosPaginator implements Serializable {

    @Inject
    private LivroServico livroServico;

    private List<Livro> livros;

    public List<Livro> getLivros() {
        if (livros == null) {
            livros = livroServico.getLivros();
        }

        return livros;
    }
}
