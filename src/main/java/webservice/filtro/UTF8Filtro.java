package webservice.filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *
 * @author MASC
 */
@WebFilter("/webservice/*")
public class UTF8Filtro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            response.setCharacterEncoding(UTF_8.name());
        }
    }

    @Override
    public void destroy() {

    }

}
