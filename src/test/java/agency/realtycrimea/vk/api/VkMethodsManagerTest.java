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
        VkAuthMethods.setToken("ff1d5eed82f14ff8326c693d896032a741db87460d172e51869e6dbfa407c0541811005312e3467f6e912");
        String xmlURI = "http://www.realtycrimea.agency/exchange/export/vk-group-export.php";
        SimpleRequest xmlRequest = new SimpleRequest(xmlURI, SimpleRequest.RequestType.GET);
        XmlNetworkManager xmlNetworkManager = new XmlNetworkManager();
        Document xmlDocument = xmlNetworkManager.sendRequest(xmlRequest);

        VkProductCreator productCreator = new VkProductCreator();
        List<VkProduct> productList = productCreator.createProducts(xmlDocument).getProductsFor(null, null);

        Assert.assertTrue(productList.size() != 0);

        vkProduct = productList;
    }

    @Test
    public void productAddTest() {
        Assert.assertTrue(vkProduct != null && vkProduct.size() > 0);

        boolean addProduct = VkMethodsManager.getInstance().addProduct(vkProduct.get(0));

        System.out.println("Product:\n" + vkProduct.get(0) + "\nadded successful? - " + addProduct);
    }

    @Test
    public void productDeleteTest() {
        Assert.assertTrue(vkProduct != null && vkProduct.size() > 0 && vkProduct.get(0).getProductId() != null);

        boolean deleteProduct = VkMethodsManager.getInstance().deleteProduct(vkProduct.get(0));

        System.out.println("Product:\n" + vkProduct.get(0) + "\ndeleted successful? - " + deleteProduct);
    }
}
