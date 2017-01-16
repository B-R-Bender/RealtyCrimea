package agency.realtycrimea.vk.utility;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.XmlNetworkManager;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by Bender on 14.01.2017.
 */
public class ResourceCreator {

    public Document createXMLResource() {
        String xmlURI = AppProperty.properties.getProperty("xml.accessRUI");
        SimpleRequest xmlRequest = new SimpleRequest(xmlURI, SimpleRequest.RequestType.GET);

        XmlNetworkManager xmlNetworkManager = new XmlNetworkManager();
        return xmlNetworkManager.sendRequest(xmlRequest);
    }

    @Deprecated
    public Integer getVkProductID() {
        return null;
    }

    @Deprecated
    public List<Integer> getVkProductsID() {
        return null;
    }

}
