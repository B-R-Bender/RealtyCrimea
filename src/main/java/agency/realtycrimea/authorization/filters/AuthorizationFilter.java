package agency.realtycrimea.authorization.filters;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.vk.api.VkAuthMethods;
import org.primefaces.json.JSONObject;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Bender on 02.12.2016.
 */
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //NOP
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getParameter("code") != null) {
            VkAuthMethods.setCode(request.getParameter("code"));
            SimpleRequest getTokenRequest = new SimpleRequest(VkAuthMethods.getAccessToken.getExactMethod(), SimpleRequest.RequestType.GET);
            JSONObject vkApiResponseObject = new VkNetworkManager().sendRequest(getTokenRequest);
            if (vkApiResponseObject.has("error")) {
                //TODO: перевести на страницу с ошибкой
            } else {
                VkAuthMethods.setToken(vkApiResponseObject.getString("access_token"));
                VkAuthMethods.setVkUserId(vkApiResponseObject.getLong("user_id"));
                VkAuthMethods.setTokenExpiresIn(new Date(System.currentTimeMillis() + vkApiResponseObject.getLong("expires_in")*1000));
                //TODO: сообщение об успершной авторизации
                response.sendRedirect(request.getContextPath() + "/main.xhtml");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //NOP
    }
}
