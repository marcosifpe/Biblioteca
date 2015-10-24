/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityExistsException;

/**
 *
 * @author MASC
 */
public class EntityExistsInterceptor {
    private Properties properties;
    
    public EntityExistsInterceptor() {
        properties = new Properties();
        InputStream is = EntityExistsInterceptor.class.getClassLoader().getResourceAsStream("ValidationMessages_pt_BR.properties");
        if (is != null) {
            try {
                properties.load(is);
            } catch (IOException ex) {
                properties.put("entidade.existente", null);
            }
        }
    }

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        try {
            return context.proceed();
        } catch (EntityExistsException ex) {
            StringBuilder str = new StringBuilder();
            str.append(properties.get("entidade.existente"));
            str.append(": ");
            str.append(ex.getMessage());
            return new String[]{Boolean.FALSE.toString(), str.toString()};
        }
    }
}
