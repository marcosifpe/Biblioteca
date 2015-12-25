package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author MASC
 */
public class Translator {
    private String URI = "http://mymemory.translated.net/api/get";
    private static final Logger logger = Logger.getGlobal();
    
    public String getTraducao(String textoOriginal) {
        try {
            URL obj = new URL(URI);
            HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();

            // add request header
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setRequestProperty("Accept-Language", "pt-br,pt;en-US,en;q=0.5");

            StringBuilder params = new StringBuilder("q=");
            params.append(textoOriginal);
            params.append("&langpair=");
            params.append("en|pt-BR");

            // Send post request
            urlConnection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(params.toString());
            outputStream.flush();
            outputStream.close();

            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Conectando {0}", URI);
                logger.log(Level.INFO, "Parâmetros: {0}", params);
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
            
            jsonObject = jsonObject.getJsonObject("responseData");
            return jsonObject.getString("translatedText");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return "null";
        }
    }
}
