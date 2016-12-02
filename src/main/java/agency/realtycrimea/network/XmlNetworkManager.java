package agency.realtycrimea.network;

import agency.realtycrimea.network.interfaces.NetworkManager;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * Created by Bender on 22.11.2016.
 */
public class XmlNetworkManager implements NetworkManager {

    public Document sendRequest (SimpleRequest request) throws IllegalArgumentException {
        switch (Enum.valueOf(SimpleRequest.RequestType.class, request.getType().name())) {
            case POST:
                return sendPostRequest(request);
            case GET:
                return sendGetRequest(request);
        }
        throw new IllegalArgumentException("Bad request: unknown request type");
    }

    private Document sendGetRequest(SimpleRequest request) {
        HttpGet getRequest = new HttpGet(request.getUri());
        Document document = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(getRequest);
             InputStream content = response.getEntity().getContent();){

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(content);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", e.getMessage()));
        }

        return document;
    }

    //TODO: переписать под пост
    private Document sendPostRequest(SimpleRequest request) {
        return sendGetRequest(request);
    }

}
