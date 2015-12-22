package webservice.util;

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
    private HttpHeaders httpHeaders;
    
    public ContentTypeUtil(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }
    
    public String getContentType() {
        String accept = httpHeaders.getHeaderString(HttpHeaders.ACCEPT);
        if (accept == null || accept.equals("")) {
            accept = APPLICATION_XML;
        }
        
        return accept;
    }
    
    public boolean isContentType(String contentType) {
        return getContentType().equals(contentType);       
    }
    
    /**
     * Determina o valor do ContentType diretamente no HttpServletResponse.
     * @param response Resposta HTTP
     */
    public void setContentType(HttpServletResponse response) {
        String accept = getContentType();
        String contentType = null;

        if (APPLICATION_JSON.equals(accept)) {
            contentType = APPLICATION_JSON + ";charset=UTF-8";
        } else if (APPLICATION_XML.equals(accept)) {
            contentType = APPLICATION_XML + ";charset=UTF-8";
        }
        
        response.setContentType(contentType);
    }    
}
