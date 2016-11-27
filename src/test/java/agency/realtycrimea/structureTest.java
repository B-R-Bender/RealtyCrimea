package agency.realtycrimea;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.network.XmlNetworkManager;
import agency.realtycrimea.vk.api.VkProductApiMethods;
import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.VkProductCreator;
import agency.realtycrimea.vk.utility.VkSimpleRequestCreator;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Bender on 24.11.2016.
 */
public class structureTest {

    public static void main(String[] args) {

        String url = "http://www.realtycrimea.agency/exchange/export/yandex.php";
        SimpleRequest xmlRequest = new SimpleRequest(url, SimpleRequest.RequestType.GET);

        XmlNetworkManager xmlNetworkManager = new XmlNetworkManager();
        Document xmlDocument = xmlNetworkManager.sendRequest(xmlRequest);

        VkProductCreator productCreator = new VkProductCreator();
        List<VkProduct> vkProduct = productCreator.fabricMethod(xmlDocument);
//
//        VkSimpleRequestCreator requestCreator = new VkSimpleRequestCreator();
//        SimpleRequest addRequest = requestCreator.fabricMethod(vkProduct, VkProductApiMethods.marketAdd);
//
//        VkNetworkManager vkManager = new VkNetworkManager();
//        Integer serverResponse = (Integer) vkManager.sendGetRequest(addRequest);
    }

}
