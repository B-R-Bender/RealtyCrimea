package agency.realtycrimea.vk.api;

import agency.realtycrimea.vk.api.interfaces.VkApiMethod;

/**
 * Created by Bender on 24.11.2016.
 */
public enum VkImageApiMethods implements VkApiMethod {
    photosGetMarketUploadServer {
        @Override
        public String getExactMethod() {
            return "photos.getMarketUploadServer";
        }
    },
    photosUpload {
        @Override
        public String getExactMethod() {
            return null;
        }
    },
    photosSaveMarketPhoto {
        @Override
        public String getExactMethod() {
            return "photos.saveMarketPhoto";
        }
    };

}
