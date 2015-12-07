/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import com.google.gson.Gson;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author MASC
 */
@Path("teste")
@Stateless
public class TestRest {
    @Context
    private HttpServletRequest servletRequest;
    @Context 
    private HttpHeaders httpHeaders;

    @GET
    @Path("message")
    @Produces("application/json")
    public String message() {
        try {
            Logger.getGlobal().info("Trying Login...");            
            String login = httpHeaders.getHeaderString("login");
            String senha = httpHeaders.getHeaderString("senha");
            Logger.getGlobal().info(login);
            Logger.getGlobal().info(senha);
            servletRequest.login(login, senha);
            Logger.getGlobal().info("Login ok");
        } catch (ServletException ex) {
            Logger.getGlobal().severe(ex.getMessage());
        } finally {
            try {
                servletRequest.logout();
            } catch (ServletException ex) {
                Logger.getGlobal().severe(ex.getMessage());
            }
        }

        Gson gson = new Gson();
        return gson.toJson("Sucesso");                    
    }
}
