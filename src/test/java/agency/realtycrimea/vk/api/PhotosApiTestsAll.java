package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.vk.model.VkImage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.primefaces.json.JSONObject;

import java.util.HashMap;

import static agency.realtycrimea.vk.utility.AppProperty.properties;

/**
 * Created by Bender on 04.12.2016.
 */

public class PhotosApiTestsAll {

    String photoURI;

    @Before
    public void init() {
        VkAuthMethods.setToken("a44e541d363a90df9e11b3b327382186104c277c83e23b33cd167bc57c595e1261bbf0346b05fd768ca6c");
        photoURI = "http://images4.fanpop.com/image/photos/24500000/Bender-Wallpaper-bender-24507237-1280-960.jpg";
    }

    @Test
    public void photoUploadTest() {
        VkImage testImage = new VkImage(properties.getProperty("vk.group.id"),true, null, null, null);
        VkImageApiMethods.setCurrentImage(testImage);

        SimpleRequest uploadServerRequest = new SimpleRequest(VkImageApiMethods.photosGetMarketUploadServer);

        VkNetworkManager manager = new VkNetworkManager();
        String uploadUrl = manager.sendRequest(uploadServerRequest).getString("upload_url");
        testImage.addUploadServer(uploadUrl);

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("fileURI", photoURI);
        SimpleRequest photoUploadRequest = new SimpleRequest(VkImageApiMethods.photoUpload);
        photoUploadRequest.setRequestParametersMap(paramMap);

        JSONObject jsonObject = manager.sendRequest(photoUploadRequest);
        System.out.println(jsonObject);
    }


}
