package agency.realtycrimea;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.vkNetworkManager;
import agency.realtycrimea.network.xmlNetworkManager;
import agency.realtycrimea.vk.api.VkProductApiMethods;
import agency.realtycrimea.vk.model.vkProduct;
import agency.realtycrimea.vk.utility.vkProductCreator;
import agency.realtycrimea.vk.utility.VkSimpleRequestCreator;

import java.io.InputStream;

/**
 * Created by Bender on 24.11.2016.
 */
public class structureTest {

    public static void main(String[] args) {

        String url = "http://blabla.com";
        SimpleRequest xmlRequest = new SimpleRequest(url);

        xmlNetworkManager xmlManager = new xmlNetworkManager();
        InputStream is = (InputStream) xmlManager.sendGetRequest(xmlRequest);

        vkProductCreator productCreator = new vkProductCreator();
        vkProduct vkProduct = productCreator.fabricMethod(is);

        VkSimpleRequestCreator requestCreator = new VkSimpleRequestCreator();
        SimpleRequest addRequest = requestCreator.fabricMethod(vkProduct, VkProductApiMethods.marketAdd);

        vkNetworkManager vkManager = new vkNetworkManager();
        Integer serverResponse = (Integer) vkManager.sendGetRequest(addRequest);
    }

}
