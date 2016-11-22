package agency.realtycrimea.vk.api;

import java.util.List;

/**
 * Created by Bender on 22.11.2016.
 */
public class vkProduct {

    /**
     * ������������� ������.
     *
     * <br>
     * ������������ ����� ��������� ���������� ������.
     */
    private Integer productId;

    /**
     * ������������� ��������� ������.
     *
     * <br>
     * ������������� ���������� � ��������� owner_id ���������� ��������� �� ������ "-" � ��������,
     * owner_id=-1 ������������� �������������� ���������� ��������� API (club1),
     * ����� �����, <b>������������ ��������</b>.
     */
    private Integer ownerId;

    /**
     * �������� ������
     *
     * <br>
     * ����������� �� ����� ��������� � ��������� cp1251. ������, <b>������������ ��������</b>.
     * ����������� ����� 4, ������������ ����� 100
     */
    private String name;

    /**
     * �������� ������.
     *
     * <br>
     * ������, ����������� ����� 10, <b>������������ ��������</b>.
     */
    private String description;

    /**
     * ������������� ��������� ������.
     *
     * <br>
     * ������������� �����, ������������ ��������
     */
    private Integer categoryId;

    /**
     * ���� ������.
     *
     * <br>
     * ������� �����, ������������ ��������, ����������� �������� 0.01
     */
    private Float price;

    /**
     * ������ ������ (1 � ����� ������, 0 � ����� �� ������).
     *
     * <br>
     * ����, ����� ��������� �������� 1 ��� 0
     */
    private boolean deleted;

    /**
     * ������������� ���������� ������� ������.
     *
     * <br>
     * ���������� ������ ���� ��������� � ������� ������ photos.getMarketUploadServer, ������� �������� main_photo.
     * ������������� �����, ������������ ��������
     */
    private Integer mainPhotoId;

    /**
     * �������������� �������������� ���������� ������.
     *
     * <br>
     * ���������� ������ ���� ��������� � ������� ������ photos.getMarketUploadServer.
     * ������ ������������� �����, ����������� ��������, ���������� ��������� ������ ���������� �� ����� 4
     */
    private List<Integer> photoIds;

    /**
     * ����������� ������ �� ID. ������� ����� ��������� ��� id ������ � ������� �� vk ������ �� ������.
     * @param productId id ������
     */
    public vkProduct(Integer productId) {
        this.productId = productId;
    }

    /**
     * ����������� ������ ������.
     *
     * @param ownerId       id ��������� ������
     * @param name          ��� ������
     * @param description   ��������
     * @param categoryId    id ���������
     * @param price         ����
     * @param mainPhotoId   id ��������� ����
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
