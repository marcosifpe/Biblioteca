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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class Recaptcha {

    public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String ACCEPT_LANGUAGE = "pt-br,pt;en-US,en;q=0.5";
    private static final Logger logger = Logger.getLogger(Recaptcha.class.getName());

    public static boolean verificar(String recaptchaResponse, String secretKey) {
        if (recaptchaResponse == null || "".equals(recaptchaResponse)) {
            return false;
        }

        try {
            URL obj = new URL(URL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) obj.openConnection();

            // add reuqest header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            urlConnection.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);

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

            logger.log(Level.INFO, "Conectando {0}", URL);
            logger.log(Level.INFO, "Parâmetros: {0}", postParams);
            logger.log(Level.INFO, "Código de resposta: {0}", urlConnection.getResponseCode());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            logger.log(Level.INFO, "Resposta: {0}", response.toString());

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
