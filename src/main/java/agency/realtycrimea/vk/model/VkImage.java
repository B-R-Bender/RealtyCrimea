package agency.realtycrimea.vk.model;

/**
 * Класс описывает изображение для работы с ним через vk.api
 * <br>
 * Ограничения: минимальный размер фото — 400x400px, сумма высоты и ширины не более 14000px. <br>
 * файл объемом не более 50 МБ. <br>
 * Поле POST-запроса: file.
 * <br>
 * Created by Bender on 24.11.2016.
 */
public class VkImage extends VkAbstractObjectRename {

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
     * Идентификатор группы фотографии
     * <br>
     * <b>обязательный параметр для:</b> - photos.getMarketUploadServer
     */
    private Integer groupId;

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
    private Integer server;

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
     * Конструктор создаёт объект, который необходим для вызова photos.getMarketUploadServer
     *
     * @param groupId идентификатор группы
     * @param mainPhoto флаг - основное фото
     * @param cropX координата для обрезки по X
     * @param cropY координата для обрезки по Y
     * @param cropWidth желаемая ширина фотографии после обрезки
     */
    public VkImage(Integer groupId, boolean mainPhoto, Character cropX, Character cropY, Character cropWidth) {
        this.groupId = groupId;
        this.mainPhoto = mainPhoto;
        this.cropX = cropX;
        this.cropY = cropY;
        this.cropWidth = cropWidth;
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
     * @return переданный объект изображения с обновлёнными полями
     */
    public VkImage addUploadData(VkImage image, String photo, Integer server, String hash, String cropData, String cropHash) {
        image.photo = photo;
        image.server = server;
        image.hash = hash;
        image.cropData = cropData;
        image.cropHash = cropHash;
        return image;
    }

    //геттеры и сеттеры
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCropData() {
        return cropData;
    }

    public void setCropData(String cropData) {
        this.cropData = cropData;
    }

    public String getCropHash() {
        return cropHash;
    }

    public void setCropHash(String cropHash) {
        this.cropHash = cropHash;
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

    public void setCropX(Character cropX) {
        this.cropX = cropX;
    }

    public Character getCropY() {
        return cropY;
    }

    public void setCropY(Character cropY) {
        this.cropY = cropY;
    }

    public Character getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(Character cropWidth) {
        this.cropWidth = cropWidth;
    }
}
