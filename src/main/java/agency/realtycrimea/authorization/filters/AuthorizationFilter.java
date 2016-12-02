package agency.realtycrimea.authorization.filters;

import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.vk.api.VkAuthMethods;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Bender on 02.12.2016.
 */
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getParameter("code") != null) {
            VkAuthMethods.setCode(request.getParameter("code"));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //NOP
    }
}
