package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.network.XmlNetworkManager;
import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.AppProperty;
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
        VkAuthMethods.setToken("ff1d5eed82f14ff8326c693d896032a741db87460d172e51869e6dbfa407c0541811005312e3467f6e912");
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
        List<VkProduct> productList;
        productList = productCreator.createProducts(xmlDocument).getProductsFor(null, null);

        Assert.assertTrue(productList.size() != 0);
        System.out.println("(null, null) size = " + productList.size());

        productList = productCreator.createProducts(xmlDocument).getProductsFor("аренда", null);

        Assert.assertTrue(productList.size() != 0);
        System.out.println("(аренда, null) size = " + productList.size());

        productList = productCreator.createProducts(xmlDocument).getProductsFor("аренда", "жилая");

        Assert.assertTrue(productList.size() != 0);
        System.out.println("(аренда, жилая) size = " + productList.size());

        System.out.println("End VkProduct create test.");
    }

    @Test
    @Ignore("move test from here")
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
