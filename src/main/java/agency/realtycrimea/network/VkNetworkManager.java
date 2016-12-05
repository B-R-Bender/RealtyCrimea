package agency.realtycrimea.network;

import agency.realtycrimea.network.interfaces.NetworkManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONObject;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Bender on 22.11.2016.
 */
public class VkNetworkManager implements NetworkManager {

    @Override
    public JSONObject sendRequest(SimpleRequest request) {
        JSONObject response;
        if (request.getType().equals(SimpleRequest.RequestType.GET))
        {
            response = sendGetRequest(request);
        } else {
            response = sendPostRequest(request);
        }
        JSONObject responseObject = response.getJSONObject("response");
        if (responseObject == null) {
            return response;
        } else {
            return responseObject;
        }
    }

    private JSONObject sendGetRequest(SimpleRequest request) {
        HttpGet postRequest = new HttpGet(request.getUri());
        JSONObject vkApiJsonResponse = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(postRequest)){

            String jsonString = EntityUtils.toString(response.getEntity());
            vkApiJsonResponse = new JSONObject(jsonString);

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", e.getMessage()));
        }
        return vkApiJsonResponse;
    }

    private JSONObject sendPostRequest(SimpleRequest request) {
        HttpPost postRequest = new HttpPost(request.getUri());
        JSONObject vkApiJsonResponse = null;
        CloseableHttpResponse response = null;

//        File file = new File(new URI(request.getRequestParametersMap().get("fileURI")));

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()){
            InputStream fileURI = new URL((String) request.getRequestParametersMap().get("fileURI")).openStream();
            ContentBody fileBody = new InputStreamBody(fileURI, ContentType.MULTIPART_FORM_DATA)/*new FileBody(file, ContentType.MULTIPART_FORM_DATA)*/;
            HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("file", fileBody).build();
            postRequest.setEntity(httpEntity);

            response = httpClient.execute(postRequest);

            String jsonString = EntityUtils.toString(response.getEntity());
            vkApiJsonResponse = new JSONObject(jsonString);

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", e.getMessage()));
        }
        return vkApiJsonResponse;
    }
}
