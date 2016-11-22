package agency.realtycrimea.vk.api;

import java.util.List;

/**
 * Created by Bender on 22.11.2016.
 */
public class vkProduct {

    /**
     * Идентификатор товара.
     *
     * <br>
     * Возвращается послу успешного добавления товара.
     */
    private Integer productId;

    /**
     * Идентификатор владельца товара.
     *
     * <br>
     * Идентификатор сообщества в параметре owner_id необходимо указывать со знаком "-" — например,
     * owner_id=-1 соответствует идентификатору сообщества ВКонтакте API (club1),
     * целое число, <b>обязательный параметр</b>.
     */
    private Integer ownerId;

    /**
     * Название товара
     *
     * <br>
     * Ограничение по длине считается в кодировке cp1251. строка, <b>обязательный параметр</b>.
     * минимальная длина 4, максимальная длина 100
     */
    private String name;

    /**
     * Описание товара.
     *
     * <br>
     * строка, минимальная длина 10, <b>обязательный параметр</b>.
     */
    private String description;

    /**
     * Идентификатор категории товара.
     *
     * <br>
     * положительное число, обязательный параметр
     */
    private Integer categoryId;

    /**
     * Цена товара.
     *
     * <br>
     * дробное число, обязательный параметр, минимальное значение 0.01
     */
    private Float price;

    /**
     * Статус товара (1 — товар удален, 0 — товар не удален).
     *
     * <br>
     * флаг, может принимать значения 1 или 0
     */
    private boolean deleted;

    /**
     * Идентификатор фотографии обложки товара.
     *
     * <br>
     * Фотография должна быть загружена с помощью метода photos.getMarketUploadServer, передав параметр main_photo.
     * положительное число, обязательный параметр
     */
    private Integer mainPhotoId;

    /**
     * Идентификаторы дополнительных фотографий товара.
     *
     * <br>
     * Фотография должна быть загружена с помощью метода photos.getMarketUploadServer.
     * список положительных чисел, разделенных запятыми, количество элементов должно составлять не более 4
     */
    private List<Integer> photoIds;

    /**
     * Конструктор товара по ID. Создает товар передавая АПИ id товара и получая от vk данные по товару.
     * @param productId id товара
     */
    public vkProduct(Integer productId) {
        this.productId = productId;
    }

    /**
     * Конструктор нового товара.
     *
     * @param ownerId       id владельца товара
     * @param name          имя товара
     * @param description   описание
     * @param categoryId    id категории
     * @param price         цена
     * @param mainPhotoId   id основного фото
     */
    public vkProduct(Integer ownerId, String name, String description, Integer categoryId, Float price, Integer mainPhotoId) {
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.mainPhotoId = mainPhotoId;
    }

    public Integer productAdd() {
        return null;
    }
}
