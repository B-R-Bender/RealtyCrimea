package agency.realtycrimea.vk.model;

import agency.realtycrimea.vk.utility.AppProperty;
import org.primefaces.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает товар для работы с ним через vk.api
 * <br>
 * Created by Bender on 22.11.2016.
 */
public class VkProduct extends VkAbstractObject {

    /**
     * Внутренний идентификатор товара.
     * <br>
     * определяет товар внутри собственной базы
     */
    private Integer innerProductId;

    /**
     * Идентификатор типа использования товара.
     * <br>
     * определяет тип использования товара внутри собственной базы
     */
    private String productType;

    /**
     * Идентификатор типа товара.
     * <br>
     * определяет товар внутри собственной базы
     */
    private String productPropertyType;

    /**
     * Идентификатор товара.
     * <br>
     * Возвращается после успешного добавления товара.
     */
    private Integer productId;

    /**
     * Идентификатор владельца товара.
     * <br>
     * Идентификатор сообщества в параметре owner_id необходимо указывать со знаком "-" — например,
     * owner_id=-1 соответствует идентификатору сообщества ВКонтакте API (club1),
     * целое число, <b>обязательный параметр</b>.
     */
    private Integer ownerId;

    /**
     * Название товара
     * <br>
     * Ограничение по длине считается в кодировке cp1251. строка, <b>обязательный параметр</b>.
     * минимальная длина 4, максимальная длина 100
     */
    private String name;

    /**
     * Описание товара.
     * <br>
     * строка, минимальная длина 10, <b>обязательный параметр</b>.
     */
    private String description;

    /**
     * Идентификатор категории товара.
     * <br>
     * положительное число, обязательный параметр
     */
    private Integer categoryId;

    /**
     * Цена товара.
     * <br>
     * дробное число, обязательный параметр, минимальное значение 0.01
     */
    private Float price;

    /**
     * Статус товара (1 — товар удален, 0 — товар не удален).
     * <br>
     * флаг, может принимать значения 1 или 0
     */
    private Boolean deleted;

    /**
     * Идентификатор фотографии обложки товара.
     * <br>
     * Фотография должна быть загружена с помощью метода photos.getMarketUploadServer, передав параметр main_photo.
     * положительное число, обязательный параметр
     */
    private Integer mainPhotoId;

    /**
     * URI фотографии обложки товара.
     * <br>
     * Фотография должна быть загружена с помощью метода photos.getMarketUploadServer, передав параметр main_photo.
     */
    private String mainPhotoURI;

    /**
     * Идентификаторы дополнительных фотографий товара.
     * <br>
     * Фотография должна быть загружена с помощью метода photos.getMarketUploadServer.
     * список положительных чисел, разделенных запятыми, количество элементов должно составлять не более 4
     */
    private List<Integer> photoIds;

    /**
     * Список URI дополнительных фотографий товара.
     * <br>
     * Фотография должна быть загружена с помощью метода photos.getMarketUploadServer.
     */
    private List<String> photosURI;

    /**
     * Конструктор товара по ID. Создает товар передавая АПИ id товара и получая от vk данные по товару.
     * @param productId id товара
     */
    public VkProduct(Integer productId) {
        this.productId = productId;
    }

    /**
     * Конструктор нового товара c передачей ID уже загруженных фотографий
     *
     * @param name          имя товара
     * @param description   описание
     * @param categoryId    id категории
     * @param price         цена
     * @param mainPhotoId   id основного фото
     */
    @Deprecated
    public VkProduct(Integer innerProductId,
                     String name,
                     String description,
                     Integer categoryId,
                     Float price,
                     Integer mainPhotoId,
                     List<Integer> photoIds) {
        this.innerProductId = innerProductId;
        this.ownerId = -Integer.parseInt(AppProperty.properties.getProperty("vk.group.id"));
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.mainPhotoId = mainPhotoId;
        this.photoIds = photoIds.size() != 0 ? photoIds : null;
    }

    /**
     * Конструктор нового товара.
     *
     * @param name          имя товара
     * @param description   описание
     * @param categoryId    id категории
     * @param price         цена
     * @param mainPhotoURI  URI основного фото
     * @param photosURI     URI дополнительных фото
     */
    public VkProduct(Integer innerProductId,
//                     Integer ownerId,
                     String name,
                     String description,
                     Integer categoryId,
                     Float price,
                     String mainPhotoURI,
                     List<String> photosURI) {
        this.innerProductId = innerProductId;
//        this.ownerId = -ownerId;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.mainPhotoURI= mainPhotoURI;
        if (photosURI.size() != 0) {
            this.photosURI = photosURI;
            this.photoIds = new ArrayList<>();
        }
    }

    /**
     * Заполнить поля объекта из ответа сервера vk
     * @param response ответ сервера в виде JSON объекта
     */
    public void applyServerResponse(JSONObject response) {
        if (response == null) {
            //TODO: сделать нормальную обработку ошибку
            System.out.println("Ошибка при применении ответа сервера");
        } else if (response.has("market_item_id")) {
            this.productId = response.getInt("market_item_id");
        }
    }

    @Override
    public String toString() {
        return "product id = " + productId
                + "\nphotoId: " + mainPhotoId
                + "\nname: " + name
                + "\nprice: " + price
                + "\ndescription: <" + (description.length() >= 50 ? description.substring(0,49) : description) + "...>"
                + "\n";
    }

    //геттеры и сеттеры
    public Integer getProductId() {
        return productId;
    }

    public Integer getInnerProductId() {
        return innerProductId;
    }

    public void setInnerProductId(Integer innerProductId) {
        this.innerProductId = innerProductId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductPropertyType() {
        return productPropertyType;
    }

    public void setProductPropertyType(String productPropertyType) {
        this.productPropertyType = productPropertyType;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = -ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getMainPhotoId() {
        return mainPhotoId;
    }

    public void setMainPhotoId(Integer mainPhotoId) {
        this.mainPhotoId = mainPhotoId;
    }

    public List<Integer> getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(List<Integer> photoIds) {
        this.photoIds = photoIds;
    }

    public String getMainPhotoURI() {
        return mainPhotoURI;
    }

    public void setMainPhotoURI(String mainPhotoURI) {
        this.mainPhotoURI = mainPhotoURI;
    }

    public List<String> getPhotosURI() {
        return photosURI;
    }

    public void setPhotosURI(List<String> photosURI) {
        this.photosURI = photosURI;
    }
}
