package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.vk.api.interfaces.VkApiMethod;
import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.AppProperty;
import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Bender on 24.11.2016.
 */
public enum VkProductApiMethods implements VkApiMethod {
    marketAdd {
        @Override
        public String getExactMethod() {
            return String.format(VkApiMethod.BASE_API_METHOD,
                    getMethodName(),
                    getParameterString(),
                    VkAuthMethods.getToken(),
                    AppProperty.properties.getProperty("vk.api.version"));
        }

        @Override
        public String getMethodName() {
            return "market.add";
        }

        @Override
        public SimpleRequest.RequestType getMethodRequestType() {
            return SimpleRequest.RequestType.POST;
        }

        private String getParameterString() {

            methodParameters.put("name", currentProduct.getName());
            methodParameters.put("description", currentProduct.getDescription());

            return "owner_id=" + currentProduct.getOwnerId()
                    + "&category_id=" + currentProduct.getCategoryId()
                    + "&price=" + currentProduct.getPrice()
                    + "&main_photo_id=" + currentProduct.getMainPhotoId()
                    + (currentProduct.isDeleted() != null
                                                  ? "&deleted=" + (currentProduct.isDeleted() ? 1 : 0)
                                                  : "")
                    + (currentProduct.getPhotoIds() != null
                                                    ? "&photo_ids=" + getProductPhotosIds()
                                                    : "");
        }

        private String getProductPhotosIds() {
            String result = "";

            for (Integer photoId : currentProduct.getPhotoIds()) {
                result += photoId + ",";
            }

            if (!result.equals("")) {
                result = result.substring(0, result.length() - 1);
            }

            return result;
        }
    },
    marketDelete {
        @Override
        public String getExactMethod() {
            return String.format(VkApiMethod.BASE_API_METHOD,
                    getMethodName(),
                    getParameterString(),
                    VkAuthMethods.getToken(),
                    AppProperty.properties.getProperty("vk.api.version"));
        }

        @Override
        public String getMethodName() {
            return "market.delete";
        }

        private String getParameterString() {

            return "owner_id=" + currentProduct.getOwnerId()
                    + "&item_id=" + currentProduct.getProductId();
        }
    },
    marketEdit {
        @Override
        public String getExactMethod() {
            return "market.edit";
        }

        @Override
        public String getMethodName() {
            return null;
        }
    };

    private static VkProduct currentProduct;

    public static void setCurrentProduct(VkProduct currentProduct) {
        VkProductApiMethods.currentProduct = currentProduct;
    }

    public static VkProduct getCurrentProduct() {

        return currentProduct;
    }
}
