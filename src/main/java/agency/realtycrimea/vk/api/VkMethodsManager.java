package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.vk.model.VkImage;
import agency.realtycrimea.vk.model.VkProduct;
import org.primefaces.json.JSONObject;

/**
 * Created by Bender on 09.12.2016.
 */
public class VkMethodsManager {

    private static VkMethodsManager managerInstance;

    public static VkMethodsManager getInstance() {
        return managerInstance == null ? managerInstance = new VkMethodsManager() : managerInstance;
    }

    /**
     * Сохранить изображение в VK
     * <br>
     * вызывает: VkImageApiMethods.photosGetMarketUploadServer -> VkImageApiMethods.photoUpload
     * -> VkImageApiMethods.photosSaveMarketPhoto
     * @param image - изображение, которое нужно загрузить
     * @throws IllegalArgumentException в случае если у изображения URL не корректен или null
     */
    public Integer saveImageForProduct(VkImage image) {
        if (image.getImageURL() == null) {
            throw new IllegalArgumentException("Image URL can not be null!");
        }

        VkImageApiMethods.setCurrentImage(image);

        SimpleRequest uploadServerRequest = new SimpleRequest(VkImageApiMethods.photosGetMarketUploadServer);

        VkNetworkManager manager = new VkNetworkManager();
        JSONObject uploadResponse = manager.sendRequest(uploadServerRequest);
        image.applyServerResponse(uploadResponse);

        SimpleRequest photoUploadRequest = new SimpleRequest(VkImageApiMethods.photoUpload);

        JSONObject fileUploadResponse = manager.sendRequest(photoUploadRequest);
        image.applyServerResponse(fileUploadResponse);

        SimpleRequest photoSaveRequest = new SimpleRequest(VkImageApiMethods.photosSaveMarketPhoto);
        JSONObject photoSaveResponse = manager.sendRequest(photoSaveRequest);
        image.applyServerResponse(photoSaveResponse);

        return image.getVkPhotoId();
    }


    /**
     * Удаляет из VK загруженное изображение по его id
     * @param image изображение для удаления
     */
    @Deprecated
    public boolean deleteImage(VkImage image) {
        if (image.getVkImageObject() == null || image.getVkImageObject().get("id") == null) {
            throw new IllegalArgumentException("Image vkImageObject or it's id can not be null!");
        }

        VkImageApiMethods.setCurrentImage(image);
        SimpleRequest photoDeleteRequest = new SimpleRequest(VkImageApiMethods.photoDelete);
        JSONObject responseObject = new VkNetworkManager().sendRequest(photoDeleteRequest);
        Object response = responseObject.opt("response");

        return response != null && (int)response == 1;
    }

    /**
     * Загружает фотографии по URI которые берет из {@link VkProduct#mainPhotoURI} и {@link VkProduct#photosURI}
     * и заполняет загруженными IDшниками {@link VkProduct#mainPhotoId} и {@link VkProduct#photoIds}
     * @param product для которого нужно загрузить фото
     */
    private void loadPhotosForProduct(VkProduct product) {
        VkImage vkProductMainPhoto = new VkImage(product.getMainPhotoURI(),
                                                 product.getOwnerId().toString(),
                                                 true,
                                                 null, null, null);
        Integer vkProductMainPhotoId = VkMethodsManager.getInstance().saveImageForProduct(vkProductMainPhoto);
        product.setMainPhotoId(vkProductMainPhotoId);

        if (product.getPhotosURI() != null) {
            product.getPhotoIds().clear();
            for (String photoURI : product.getPhotosURI()) {
                VkImage vkProductPhoto = new VkImage(photoURI,
                        product.getOwnerId().toString(),
                        false,
                        null, null, null);
                Integer vkProductPhotoId = VkMethodsManager.getInstance().saveImageForProduct(vkProductPhoto);
                product.getPhotoIds().add(vkProductPhotoId);
            }
        }
    }

    /**
     * Добавляет в VK продукт
     * @param product продукт для добавления
     * @return true если после добавления есть market_item_id, false в противном случае
     */
    public boolean addProduct(VkProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("Product can not be null!");
        }
        loadPhotosForProduct(product);
        VkProductApiMethods.setCurrentProduct(product);

        SimpleRequest marketAddRequest = new SimpleRequest(VkProductApiMethods.marketAdd);
        JSONObject marketAddResponse = new VkNetworkManager().sendRequest(marketAddRequest);

        product.applyServerResponse(marketAddResponse);
        return marketAddResponse.has("market_item_id");
    }

    /**
     * Удаляет из VK продукт по его id
     * @param product продукт для удаления
     */
    public boolean deleteProduct(VkProduct product) {
        VkNetworkManager manager = new VkNetworkManager();
        VkProductApiMethods.setCurrentProduct(product);
        SimpleRequest marketDelete = new SimpleRequest(VkProductApiMethods.marketDelete);

        JSONObject marketDeleteResponse = manager.sendRequest(marketDelete);

        return marketDeleteResponse.opt("response") != null && marketDeleteResponse.opt("response").equals(1);
    }

}
