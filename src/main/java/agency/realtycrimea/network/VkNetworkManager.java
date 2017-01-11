package agency.realtycrimea.network;

import agency.realtycrimea.network.interfaces.NetworkManager;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        if (response == null) {
            return null;
        } else if ( response.has("response")) {
            if (response.get("response") instanceof Integer) {
                return response;
            } else if (response.get("response") instanceof JSONObject) {
                return response.optJSONObject("response");
            } else if (response.get("response") instanceof JSONArray) {
                return ((JSONObject) response.getJSONArray("response").get(0));
            } else {
                return null;
            }
        } else if (response.has("error")){
            //TODO: добавить лог и добавить исключение
            String errorMsg = response.getJSONObject("error").getString("error_msg");
            System.out.println(errorMsg);
            return null;
        } else {
            return response;
        }
    }

    private JSONObject sendGetRequest(SimpleRequest request) {
        HttpGet postRequest = new HttpGet(request.getUri());
        JSONObject vkApiJsonResponse = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(postRequest)){

            String jsonString = EntityUtils.toString(response.getEntity());
            if (jsonString.charAt(0) != '{') {
                return null;
            }

            vkApiJsonResponse = new JSONObject(jsonString);

        } catch (IOException e) {
            //TODO: логирование ошибки
            System.out.println(e.toString());
            /*FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", e.getMessage()));*/
        }
        return vkApiJsonResponse;
    }

    private JSONObject sendPostRequest(SimpleRequest request) {
        Map<String, Object> requestParametersMap = request.getRequestParametersMap();
        HttpPost postRequest = new HttpPost(request.getUri());

        JSONObject vkApiJsonResponse = null;
        CloseableHttpResponse response;
        HttpEntity httpEntity = null;

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()){

            if (requestParametersMap != null) {
                if (requestParametersMap.containsKey("file")) {
                    ContentType requestContentType = (ContentType) requestParametersMap.get("contentType");
                    ContentBody requestContent = new FileBody((File) requestParametersMap.get("file"), requestContentType);
                    httpEntity = MultipartEntityBuilder.create()
                            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                            .addPart("file", requestContent).build();
                } else {
                    List<NameValuePair> parameters = new ArrayList<>();
                    for (Map.Entry parameter : requestParametersMap.entrySet()) {
                        parameters.add(new BasicNameValuePair(parameter.getKey().toString(), parameter.getValue().toString()));
                    }
                    httpEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
                }
            }


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
