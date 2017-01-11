package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.network.XmlNetworkManager;
import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.VkProductCreator;
import org.junit.*;
import org.primefaces.json.JSONObject;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by Bender on 12.12.2016.
 */
public class VkProductCreatorTest {

    static String xmlURI;

    static List<VkProduct> vkProduct;

    VkNetworkManager manager;

    @BeforeClass
    public static void init() {
        VkAuthMethods.setToken("6e2b7011f2e30a63b0d0db1c443f690782ec4e11dda956a0f736527ffb436db95bd4a31de569712b5ad57");
        xmlURI = "http://www.realtycrimea.agency/exchange/export/vk-group-export.php";
    }

    @AfterClass
    public static void destroy() {
        VkAuthMethods.setToken(null);
        xmlURI = null;
    }

    @Before
    public void before() {
        manager = new VkNetworkManager();
    }

    @Test
    public void createTest() {
        SimpleRequest xmlRequest = new SimpleRequest(xmlURI, SimpleRequest.RequestType.GET);

        XmlNetworkManager xmlNetworkManager = new XmlNetworkManager();
        Document xmlDocument = xmlNetworkManager.sendRequest(xmlRequest);

        VkProductCreator productCreator = new VkProductCreator();
        vkProduct = productCreator.fabricMethod(xmlDocument);

        Assert.assertTrue(vkProduct.size() != 0);

        VkProduct product = VkProductCreatorTest.vkProduct.get(0);

        VkProductApiMethods.setCurrentProduct(product);
        SimpleRequest marketAddRequest = new SimpleRequest(VkProductApiMethods.marketAdd);

        JSONObject marketAddResponse = manager.sendRequest(marketAddRequest);
        product.applyServerResponse(marketAddResponse);

        System.out.println(product);

        System.out.println("End VkProduct create test.");
    }

    @Test
    public void deleteTest() {

        for (VkProduct product : vkProduct) {
            VkProductApiMethods.setCurrentProduct(product);
            SimpleRequest marketDelete = new SimpleRequest(VkProductApiMethods.marketDelete);

            JSONObject marketDeleteResponse = manager.sendRequest(marketDelete);
            System.out.println(marketDeleteResponse);
        }

        System.out.println("End VkProduct delete test.");
    }
}
