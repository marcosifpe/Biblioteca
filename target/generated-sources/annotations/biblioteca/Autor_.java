package biblioteca;

import biblioteca.Livro;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-30T19:05:56")
@StaticMetamodel(Autor.class)
public class Autor_ { 

    public static volatile SingularAttribute<Autor, String> ultimoNome;
    public static volatile SingularAttribute<Autor, String> primeiroNome;
    public static volatile ListAttribute<Autor, Livro> livros;
    public static volatile SingularAttribute<Autor, String> cpf;
    public static volatile SingularAttribute<Autor, Long> id;

}