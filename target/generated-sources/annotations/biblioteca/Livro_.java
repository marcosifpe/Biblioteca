package biblioteca;

import biblioteca.ArquivoDigital;
import biblioteca.Autor;
import biblioteca.Editora;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-30T19:05:56")
@StaticMetamodel(Livro.class)
public class Livro_ { 

    public static volatile SingularAttribute<Livro, String> isbn;
    public static volatile SingularAttribute<Livro, String> titulo;
    public static volatile SingularAttribute<Livro, Long> id;
    public static volatile SingularAttribute<Livro, ArquivoDigital> arquivoDigital;
    public static volatile CollectionAttribute<Livro, Autor> autores;
    public static volatile SingularAttribute<Livro, Editora> editora;
    public static volatile SingularAttribute<Livro, Date> dataCriacao;
    public static volatile SingularAttribute<Livro, Date> dataLancamento;

}