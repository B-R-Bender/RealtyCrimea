package agency.realtycrimea.network;

import agency.realtycrimea.network.interfaces.NetworkManager;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONObject;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by Bender on 22.11.2016.
 */
public class VkNetworkManager implements NetworkManager {

    @Override
    public Object sendRequest(SimpleRequest request) {
        return request.getType().equals(SimpleRequest.RequestType.GET)
                ? sendGetRequest(request)
                : sendPostRequest(request) ;
    }

    private Object sendGetRequest(SimpleRequest request) {
        HttpGet postRequest = new HttpGet(request.getUri());
        JSONObject vkApiJsonResponse = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(postRequest)){

            String json_string = EntityUtils.toString(response.getEntity());
            vkApiJsonResponse = new JSONObject(json_string);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", e.getMessage()));
        }
        return vkApiJsonResponse;
    }

    private Object sendPostRequest(SimpleRequest request) {
        return null;
    }
}
