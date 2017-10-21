package jsf.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

@RequestScoped
@Named("recaptcha")
public class Recaptcha {

    private String recaptchaResponse;
    private String secretKey;
    private String url;
    @Inject
    private FacesContext facesContext;

    @PostConstruct
    public void init() {
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
        Form form = new Form();
        form = form.param("secret", secretKey);
        form = form.param("response", recaptchaResponse);
        JsonObject jsonObject = webTarget.request().post(Entity.form(form), JsonObject.class);
        return jsonObject.getBoolean("success");
    }
}
