package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.vk.api.interfaces.VkApiMethod;
import agency.realtycrimea.vk.model.VkImage;
import agency.realtycrimea.vk.utility.AppProperty;

/**
 * Created by Bender on 24.11.2016.
 */
public enum VkImageApiMethods implements VkApiMethod {

    /**
     * Метод для получения адреса сервера для загркзкифотографий товара
     */
    photosGetMarketUploadServer() {
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
            return "photos.getMarketUploadServer";
        }

        private String getParameterString() {
            return "group_id=" + AppProperty.properties.getProperty("vk.group.id")
                    + "&main_photo=" + (currentImage.isMainPhoto() ? 1 : 0)
                    + (currentImage.getCropX() != null ? "&crop_x=" + currentImage.getCropX() : "")
                    + (currentImage.getCropY() != null ? "&crop_y=" + currentImage.getCropY() : "")
                    + (currentImage.getCropWidth() != null ? "&crop_width=" + currentImage.getCropWidth() : "");
        }
    },
    /**
     * Метод для получения URI сервера vk для загрузки фото
     */
    photoUpload {
        @Override
        public String getExactMethod() {
            return getMethodName();
        }

        @Override
        public String getMethodName() {
            return currentImage.getUploadServerURI();
        }

        @Override
        public SimpleRequest.RequestType getMethodRequestType() {
            return SimpleRequest.RequestType.POST;
        }
    },
    /**
     * Метод для загрузки изображения на сервер vk
     */
    photosSaveMarketPhoto {
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
            return "photos.saveMarketPhoto";
        }

        private String getParameterString() {
            return "group_id=" + AppProperty.properties.getProperty("vk.group.id")
                    + "&photo=" + currentImage.getPhoto()
                    + "&server=" + currentImage.getServer()
                    + "&hash=" + currentImage.getHash()
                    + (currentImage.getCropData() != null ? "&crop_data=" + currentImage.getCropData() : "")
                    + (currentImage.getCropHash() != null ? "&crop_hash=" + currentImage.getCropHash() : "");
        }
    };

    private static VkImage currentImage;

    public static void setCurrentImage(VkImage currentImage) {
        VkImageApiMethods.currentImage = currentImage;
    }

    public VkImage getCurrentImage() {
        return currentImage;
    }
}
