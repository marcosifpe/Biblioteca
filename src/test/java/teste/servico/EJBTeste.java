/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.servico;

import biblioteca.Autor;
import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import servico.AutorServico;

/**
 *
 * @author marcos
 */
public class EJBTeste {

    private static EJBContainer container;
    private AutorServico autorServico;

    public EJBTeste() {
    }

    @BeforeClass
    public static void setUpClass() {
        /**
         * Properties props = new Properties();
         * props.put(Context.INITIAL_CONTEXT_FACTORY,
         * "com.sun.enterprise.naming.SerialInitContextFactory");
         * props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
         * props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
         */
        container = EJBContainer.createEJBContainer();

    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void consultarAutor() throws NamingException {
        autorServico = (AutorServico) container.getContext().lookup("java:global/classes/AutorServico!servico.AutorServico");
        assertNotNull(autorServico);
        Autor autor = autorServico.getAutor("132.016.176-90");
        assertNotNull(autor);
    }
}
