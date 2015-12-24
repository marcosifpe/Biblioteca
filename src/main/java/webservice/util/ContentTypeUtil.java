package webservice.util;

import javax.ws.rs.core.HttpHeaders;
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
            accept = APPLICATION_XML + ";charset=UTF-8";
        } else {
            accept = accept + ";charset=UTF-8";
        }
        
        return accept;
    } 
}
