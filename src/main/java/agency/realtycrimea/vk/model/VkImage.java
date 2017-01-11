package agency.realtycrimea.vk.model;

import org.primefaces.json.JSONObject;

import static agency.realtycrimea.vk.utility.AppProperty.properties;

/**
 * Класс описывает изображение для работы с ним через vk.api
 * <br>
 * Ограничения: минимальный размер фото — 400x400px, сумма высоты и ширины не более 14000px. <br>
 * файл объемом не более 50 МБ. <br>
 * Поле POST-запроса: file.
 * <br>
 * Created by Bender on 24.11.2016.
 */
public class VkImage extends VkAbstractObject {

    /**
     * Доступные для работы с vk.api форматы изображений:
     * <br>
     * <b> .jpg .png .gif </b>
     */
    public enum availableFormats {
        jpg,
        png,
        gif;

        availableFormats() {
        }
    }

    /**
     * Урл изображения с которого нужно загрузить картинку
     */
    private String imageURL;

    /**
     * Идентификатор группы фотографии
     * <br>
     * <b>обязательный параметр для:</b> - photos.getMarketUploadServer
     */
    private String groupId;

    /**
     * Является ли фотография обложкой товара (1 — фотография для обложки, 0 — дополнительная фотография)
     */
    private boolean mainPhoto;

    /**
     * Координата x для обрезки фотографии (верхний правый угол)
     */
    private Character cropX;

    /**
     * Координата y для обрезки фотографии (верхний правый угол)
     */
    private Character cropY;

    /**
     * Ширина фотографии после обрезки в пикселях.
     * <br>
     * положительное число, <b>минимальное значение 400</b>
     */
    private Character cropWidth;

    /**
     * Адрес сервера для загрузки фотографии товара полученный в ответ на photos.getMarketUploadServer
     */
    private String uploadServerURI;

    /**
     * Параметр, возвращаемый в результате загрузки фотографии на сервер
     * <br>
     * <b>обязательный параметр для:</b> - photos.saveMarketPhoto
     */
    private Integer server;

    /**
     * Параметр, возвращаемый в результате загрузки фотографии на сервер
     * <br>
     * <b>обязательный параметр для:</b> - photos.saveMarketPhoto
     */
    private String photo;

    /**
     * Параметр, возвращаемый в результате загрузки фотографии на сервер
     * <br>
     * <b>обязательный параметр для:</b> - photos.saveMarketPhoto
     */
    private String hash;

    /**
     * Параметр, возвращаемый в результате загрузки фотографии на сервер
     */
    private String cropData;

    /**
     * Параметр, возвращаемый в результате загрузки фотографии на сервер
     */
    private String cropHash;

    /**
     * Объект загруженного изображения в формате vk - представляет собой JSON объект.
     * <br>
     * основные наименования полей:
     * <ul>
     *     <li>id</li>
     *     <li>album_id</li>
     *     <li>owner_id</li>
     *     <li>user_id</li>
     *     <li>text</li>
     *     <li>date</li>
     * </ul>
     */
    private JSONObject vkImageObject;

    /**
     * Конструктор создаёт объект, который необходим для вызова photos.getMarketUploadServer
     *
     * @param imageURL URL изображения откуда его нужно загрузить
     * @param groupId идентификатор группы (если передан null - по умолачнию установит vk.group.id из файла свойств)
     * @param mainPhoto флаг - основное фото (если передан null - по умолачнию установит false)
     * @param cropX координата для обрезки по X (необязательный параметр)
     * @param cropY координата для обрезки по Y (необязательный параметр)
     * @param cropWidth желаемая ширина фотографии после обрезки (необязательный параметр)
     */
    public VkImage(String imageURL, String groupId, Boolean mainPhoto, Character cropX, Character cropY, Character cropWidth) {
        this.imageURL = imageURL;
        this.groupId = groupId == null ? properties.getProperty("vk.group.id") : groupId;
        this.mainPhoto = mainPhoto == null ? false : mainPhoto;
        this.cropX = cropX;
        this.cropY = cropY;
        this.cropWidth = cropWidth;
    }

    /**
     * Заполнить поля объекта из ответа сервера vk
     * @param response ответ сервера в виде JSON объекта
     */
    public void applyServerResponse(JSONObject response) {
        if (response.has("upload_url")) {
            addUploadServer(response.getString("upload_url"));
        } else if (response.has("server")) {
            int server = response.getInt("server");
            String photo = response.getString("photo");
            String hash = response.getString("hash");
            String cropData = response.getString("crop_data");
            String cropHash = response.getString("crop_hash");
            addUploadData(server, photo, hash, cropData, cropHash);
        } else if (response.has("id")) {
            this.vkImageObject = response;
        }
    }

    /**
     * Добавить адрес сервера для загрузки изображения.
     * <br>
     * обязательно выполнить перед вызовом VkImageApiMethods.photoUpload
     * @param uploadServerURI URI сервера vk на который нужно загрузить изображение
     */
    private void addUploadServer(String uploadServerURI) {
        this.uploadServerURI = uploadServerURI;
    }

    /**
     * Метод добавляющий данные, полученные от vk после загрузки фото на сервер
     * <br>
     * Для дополнительной фотографии поля crop_data и crop_hash не возвращаются
     *
     * @param photo строка, описывающая изображение
     * @param server видимо идентификатор сервера
     * @param hash хэш
     * @param cropData данные обрезки
     * @param cropHash хэш обрезки
     */
    private void addUploadData(Integer server, String photo, String hash, String cropData, String cropHash) {
        this.photo = photo;
        this.server = server;
        this.hash = hash;
        this.cropData = cropData;
        this.cropHash = cropHash;
    }

    /**
     * Переопреденённый toString покажет
     *  <ui>
     *     <li>id</li>
     *     <li>album_id</li>
     *     <li>owner_id</li>
     *     <li>user_id</li>
     *  </ui>
     * @return
     */
    @Override
    public String toString() {
        return "VK ID: " + vkImageObject.get("id")
                + "; VK ALBUM ID: " + vkImageObject.get("album_id")
                + "; VK OWNER ID: " + vkImageObject.get("owner_id")
                + "; VK USER ID: " + vkImageObject.get("user_id");
    }

    //геттеры и сеттеры

    public Integer getVkPhotoId() {
        return vkImageObject != null ? vkImageObject.getInt("id") : null;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUploadServerURI() {
        return uploadServerURI;
    }

    public Integer getServer() {
        return server;
    }

    public String getHash() {
        return hash;
    }

    public String getCropData() {
        return cropData;
    }

    public String getCropHash() {
        return cropHash;
    }

    public boolean isMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(boolean mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public Character getCropX() {
        return cropX;
    }

    public Character getCropY() {
        return cropY;
    }

    public Character getCropWidth() {
        return cropWidth;
    }

    public JSONObject getVkImageObject() {
        return vkImageObject;
    }
}
