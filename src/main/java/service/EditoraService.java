package service;

import biblioteca.Editora;
import dao.DaoGenerico;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@DeclareRoles({"administrador", "usuario"})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EditoraService {

    @EJB
    private DaoGenerico daoGenerico;

    @RolesAllowed({"administrador"})
    public void salvar(Editora editora) {
        daoGenerico.salvar(editora);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public List<Editora> getEditoras() {
        return (List<Editora>) daoGenerico.get("Editoras");
    }
}
