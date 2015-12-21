/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

/**
 *
 * @author MASC
 */
public class ContentTypeUtil {
    public void setContentType(HttpHeaders httpHeaders, HttpServletResponse response) {
        String accept = httpHeaders.getHeaderString(HttpHeaders.ACCEPT);
        String contentType = null;

        if (accept == null || accept.equals(APPLICATION_JSON)) {
            contentType = APPLICATION_JSON + ";charset=UTF-8";
        } else if (accept.equals(APPLICATION_XML)) {
            contentType = APPLICATION_XML + ";charset=UTF-8";
        }
        
        response.setContentType(contentType);
    }    
}
