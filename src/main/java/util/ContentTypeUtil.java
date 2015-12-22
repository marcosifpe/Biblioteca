package util;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

/**
 * @author MASC
 * Classe necessária para "setar" o charset=UTF-8. Caso contrário, caracteres 
 * especiais, como os de acentuação, não seriam exibidos corretamente.
 */
public class ContentTypeUtil {
    /**
     * Determina o valor do ContentType diretamente no HttpServletResponse.
     * @param httpHeaders Cabeçalho HTTP
     * @param response Resposta HTTP
     */
    public void setContentType(HttpHeaders httpHeaders, HttpServletResponse response) {
        String accept = httpHeaders.getHeaderString(HttpHeaders.ACCEPT);
        String contentType = null;

        if (APPLICATION_JSON.equals(accept)) {
            contentType = APPLICATION_JSON + ";charset=UTF-8";
        } else if (APPLICATION_XML.equals(accept) || accept == null) {
            contentType = APPLICATION_XML + ";charset=UTF-8";
        }
        
        response.setContentType(contentType);
    }    
}
