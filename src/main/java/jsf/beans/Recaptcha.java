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
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Recaptcha vazio");
            }
            return false;
        }

        try {
            URL obj = new URL(url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) obj.openConnection();

            // add reuqest header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setRequestProperty("Accept-Language", "pt-br,pt;en-US,en;q=0.5");

            StringBuilder postParams = new StringBuilder("secret=");
            postParams.append(secretKey);
            postParams.append("&response=");
            postParams.append(recaptchaResponse);

            // Send post request
            urlConnection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(postParams.toString());
            outputStream.flush();
            outputStream.close();

            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Conectando {0}", url);
                logger.log(Level.INFO, "Parâmetros: {0}", postParams);
                logger.log(Level.INFO, "Código de resposta: {0}", urlConnection.getResponseCode());
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Resposta: {0}", response.toString());
            }

            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }
}
