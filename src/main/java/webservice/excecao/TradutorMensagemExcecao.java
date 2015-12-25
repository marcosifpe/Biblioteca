package webservice.excecao;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author MASC
 */
public class TradutorMensagemExcecao {

    private String URI = "http://mymemory.translated.net";

    public String getTraducao(String textoOriginal) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(URI);
        webTarget = webTarget.path("api");
        webTarget = webTarget.path("get");
        webTarget = webTarget.queryParam("q", textoOriginal);
        webTarget = webTarget.queryParam("langpair", "en|pt-BR");
        JsonObject jsonObject = webTarget.request().get(JsonObject.class);
        jsonObject = jsonObject.getJsonObject("responseData");
        return jsonObject.getString("translatedText");
    }
}
