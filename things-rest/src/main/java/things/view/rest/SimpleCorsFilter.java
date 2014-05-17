package things.view.rest;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 20/04/14
 * Time: 8:14 PM
 */
@Component
public class SimpleCorsFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }
}
