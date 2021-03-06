package servico;

import static acesso.Papel.ADMINISTRADOR;
import static acesso.Papel.USUARIO;
import static biblioteca.Editora.EDITORA_POR_NOME;
import static biblioteca.Editora.EDITORAS;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

import biblioteca.Editora;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBAccessException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import javax.ejb.TransactionManagement;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@DeclareRoles({ADMINISTRADOR, USUARIO})
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class EditoraServico extends Servico<Editora> {

    @Resource
    private SessionContext sessionContext;

    public void salvar(Editora editora) throws ExcecaoNegocio {
        if (sessionContext.isCallerInRole(ADMINISTRADOR)) {
            checarExistencia(EDITORA_POR_NOME, editora.getNome());
            entityManager.persist(editora);
        } else {
            throw new EJBAccessException();
        }
    }

    @TransactionAttribute(SUPPORTS)
    public List<Editora> getEditoras() {
        return getEntidades(EDITORAS);
    }
    
    @TransactionAttribute(SUPPORTS)    
    public Editora getEditora(String nome) {
        return super.getEntidade(EDITORA_POR_NOME, new Object[]{nome});
    }
    
    @TransactionAttribute(SUPPORTS)
    public Editora criar() {
        return new Editora();
    }

    public void remover(String nome) throws ExcecaoNegocio {
        Editora editora = getEditora(nome);
        if (editora.isInativo())
            entityManager.remove(editora);
        else
            throw new ExcecaoNegocio(ExcecaoNegocio.REMOVER_EDITORA);
    }
}
