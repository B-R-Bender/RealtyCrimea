package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.XmlNetworkManager;
import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.VkProductCreator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by Bender on 11.01.2017.
 */
public class VkMethodsManagerTest {

    static List<VkProduct> vkProduct;

    @BeforeClass
    public static void init() {
        VkAuthMethods.setToken("6e2b7011f2e30a63b0d0db1c443f690782ec4e11dda956a0f736527ffb436db95bd4a31de569712b5ad57");
        String xmlURI = "http://www.realtycrimea.agency/exchange/export/vk-group-export.php";
        SimpleRequest xmlRequest = new SimpleRequest(xmlURI, SimpleRequest.RequestType.GET);
        XmlNetworkManager xmlNetworkManager = new XmlNetworkManager();
        Document xmlDocument = xmlNetworkManager.sendRequest(xmlRequest);

        VkProductCreator productCreator = new VkProductCreator();
        vkProduct = productCreator.fabricMethod(xmlDocument);
    }

    @Test
    public void productAddTest() {
        VkMethodsManager manager = VkMethodsManager.getInstance();

        Assert.assertTrue(vkProduct.size() > 0);

        boolean addProduct = manager.addProduct(vkProduct.get(0));

        System.out.println("Product:\n" + vkProduct.get(0) + "\n added successful: " + addProduct);
    }
}
