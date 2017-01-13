package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.vk.model.VkImage;
import org.junit.*;
import org.primefaces.json.JSONObject;

import java.util.HashMap;

import static agency.realtycrimea.vk.utility.AppProperty.properties;

/**
 * Created by Bender on 04.12.2016.
 */

public class VkImageApiTest {

    static String photoURI;

    static VkImage vkTestImage;

    @BeforeClass
    public static void init() {
        VkAuthMethods.setToken("4f81ae3bf50b7e1352015486e3fc75d3d7f4298ca15695defb75e68260e2f1f703a5b99821f2023ee6bf5");
        photoURI = "http://images4.fanpop.com/image/photos/24500000/Bender-Wallpaper-bender-24507237-1280-960.jpg";
    }

    @AfterClass
    public static void destroy() {
        VkAuthMethods.setToken(null);
        photoURI = null;
        vkTestImage = null;
    }

    @Test
    public void photoUploadTest() {
        vkTestImage = new VkImage(photoURI, properties.getProperty("vk.group.id"),true, null, null, null);
        VkImageApiMethods.setCurrentImage(vkTestImage);

        SimpleRequest uploadServerRequest = new SimpleRequest(VkImageApiMethods.photosGetMarketUploadServer);

        VkNetworkManager manager = new VkNetworkManager();
        JSONObject uploadResponse = manager.sendRequest(uploadServerRequest);
        vkTestImage.applyServerResponse(uploadResponse);

        SimpleRequest photoUploadRequest = new SimpleRequest(VkImageApiMethods.photoUpload);

        JSONObject fileUploadResponse = manager.sendRequest(photoUploadRequest);
        vkTestImage.applyServerResponse(fileUploadResponse);

        SimpleRequest photoSaveRequest = new SimpleRequest(VkImageApiMethods.photosSaveMarketPhoto);
        JSONObject photoSaveResponse = manager.sendRequest(photoSaveRequest);
        vkTestImage.applyServerResponse(photoSaveResponse);

        Assert.assertTrue(vkTestImage.getVkImageObject() != null);
        Assert.assertTrue(vkTestImage.getVkImageObject().getInt("id") != 0);

        System.out.println(vkTestImage);
/*
        boolean deleteImage = VkMethodsManager.getInstance().deleteImage(vkTestImage);

        Assert.assertTrue(deleteImage);
*/
    }

    @Ignore("do not work yet")
    @Test
    public void photoDeleteTest() {
        VkImageApiMethods.setCurrentImage(vkTestImage);
        SimpleRequest photoDeleteRequest = new SimpleRequest(VkImageApiMethods.photoDelete);
        JSONObject response = new VkNetworkManager().sendRequest(photoDeleteRequest);
        vkTestImage = null;
        Assert.assertTrue(response.getInt("response") == 1);
    }
}
