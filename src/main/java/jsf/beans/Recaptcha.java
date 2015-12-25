/*
 * To change this license header, choose License Headers bufferedReader Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template bufferedReader the editor.
 */
package jsf.beans;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class Recaptcha {

    private static final Logger logger = Logger.getLogger(Recaptcha.class.getName());
    private String recaptchaResponse;
    private String secretKey;
    private String url;

    public Recaptcha(FacesContext facesContext) {
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        this.recaptchaResponse = request.getParameter("g-recaptcha-response");
        this.secretKey = facesContext.getExternalContext().getInitParameter("PRIVATE_CAPTCHA_KEY");
        this.url = facesContext.getExternalContext().getInitParameter("CAPTCHA_URL");
    }

    public boolean validar() {
        if (recaptchaResponse == null || "".equals(recaptchaResponse)) {
            return false;
        }
        
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        webTarget = webTarget.path("api");
        webTarget = webTarget.path("siteverify");    
        webTarget = webTarget.queryParam("secret", secretKey);
        webTarget = webTarget.queryParam("response", recaptchaResponse);
        Response response = webTarget.request().post(Entity.json(""));
        JsonObject jsonObject = response.readEntity(JsonObject.class);
        return jsonObject.getBoolean("success");
    }
}
