package agency.realtycrimea.vk.utility;

import agency.realtycrimea.vk.api.VkMethodsManager;
import agency.realtycrimea.vk.model.VkImage;
import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.interfaces.VkObjectCreator;
import com.sun.org.apache.xerces.internal.dom.DeferredDocumentImpl;
import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static agency.realtycrimea.vk.utility.AppProperty.properties;

/**
 * Created by Bender on 22.11.2016.
 */
public class VkProductCreator implements VkObjectCreator {

    /**
     * Энум используется в имплементации хендлера sax для хранения текущего элемента
     */
    private enum XmlTags {
        offer,
        title,
        description,
        price,
        photo,
        type,
        propertyType,
        category,
        blank;
    }

//    private final Integer ownerId;
    
    private List<VkProduct> vkProductList;

/*
    private VkProductCreator() {
        ownerId = null;
    }

    public VkProductCreator(Integer ownerId) {
        this.ownerId = ownerId;
    }
*/

    /**
     * Фабрика для создания продуктов vk. На вход получет ресурс который может быть:
     * <ul>
     *     <li>{@link Integer} - для создания продукта по его item_id из vk api</li>
     *     <li>{@link List} of  {@link Integer} - для создания списка продуктов по item_id из vk api</li>
     *     <li>{@link Document} - для создания продукта(ов) по xml-документу</li>
     * </ul>
     * получить список продуктов для конкретной группы можно через метод {@link VkProductCreator#getProductsFor(String, String)}
     * @throws IllegalArgumentException если в виде ресерса получен любой другой объект кроме перечисленных
     */
    @Override
    public VkProductCreator createProducts(Object resource) {
        List<VkProduct> createdVkProductList;

        if (resource instanceof Integer) {
            createdVkProductList = createExistingProduct(((Integer) resource));
        } else if (resource instanceof Document) {
            createdVkProductList = createNewProductFromXml(((Document) resource).getChildNodes(), new ArrayList<VkProduct>());
        } else {
            throw new IllegalArgumentException("Illegal resource class: " + resource.getClass());
        }

        vkProductList = createdVkProductList;
        return this;
    }

    /**
     * Возвращает список товаров у которых {@link VkProduct#productType} соответствует переданому type
     * и {@link VkProduct#productPropertyType} соответствует переданому propertyType
     * <br>
     * для игнорирования одного из значений нужно передавать {@link null}
     * <br>
     * для получения всех товаров с группой по умолчанию оба передаваемых значения должны быть {@link null}
     * @param type типа использования товара
     * @param propertyType вид товара
     * @return список продуктов vk соответствующих критериям
     */
    public List<VkProduct> getProductsFor(String type, String propertyType) {
        //TODO: добавить правильные id групп
        List<VkProduct> resultList = new ArrayList<>();
        if (type == null && propertyType == null) {
            for (VkProduct vkProduct : vkProductList) {
                vkProduct.setOwnerId(Integer.parseInt(AppProperty.properties.getProperty("vk.group.id")));
//                vkProduct.setOwnerId(1);
                resultList.add(vkProduct);
            }
        } else if (propertyType == null) {
            for (VkProduct vkProduct : vkProductList) {
                if (vkProduct.getProductType().equals(type)) {
                    vkProduct.setOwnerId(Integer.parseInt(AppProperty.properties.getProperty("vk.group.id")));
//                    vkProduct.setOwnerId(2);
                    resultList.add(vkProduct);
                }
            }
        } else {
            for (VkProduct vkProduct : vkProductList) {
                if (vkProduct.getProductType().equals(type) && vkProduct.getProductPropertyType().equals(propertyType)) {
                    vkProduct.setOwnerId(Integer.parseInt(AppProperty.properties.getProperty("vk.group.id")));
//                    vkProduct.setOwnerId(3);
                    resultList.add(vkProduct);
                }
            }
        }
        return resultList;
    }

    private List<VkProduct> createExistingProduct(Integer productId) {
        List<VkProduct> productList = new ArrayList<>();
        productList.add(new VkProduct(productId));
        return productList;
    }

    /**
     * Метод ищет ноды с именем "offer" и запускает метод создания продукта для vk если находит такую
     * @param nodes нода для поиска описания продукта
     * @param productList список продуктов в который будет добавленный новый продукт после его создания
     * @return список VkProduct в который добавлен новый/созданный продукт, если это получилось сделать
     */
    private List<VkProduct> createNewProductFromXml(NodeList nodes, ArrayList<VkProduct> productList) {

        if (nodes == null || nodes.getLength() == 0){
            return productList;
        }

        for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
            if (nodes.item(nodeIndex).getNodeName().equals("offer")) {
                createProducts(nodes.item(nodeIndex), productList);
                break;
            } else {
                createNewProductFromXml(nodes.item(nodeIndex).getChildNodes(), productList);
            }
        }

        return productList;
    }

    /**
     * Метод для создания конкретного продукта, когда получена нода "offer"
     * @param node элемент документа из которого нужно попробовать создать продкт
     * @param productList список продуктов в который будет добавленный новый продукт после его создания
     * @return список VkProduct в который добавлен новый/созданный продукт, если это получилось сделать
     */
    private ArrayList<VkProduct> createProducts(Node node, ArrayList<VkProduct> productList) {

        if (node == null) {
            return productList;
        }
        //TODO: выпилить второе условие, заглушка чтобы загружать только 31871
        else if (node.getNodeName().equals("offer") /*&& node.getAttributes().getNamedItem("internal-id").getNodeValue().equals("31871")*/) {
            try {
                SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                SaxHandlerImplementation handler = new SaxHandlerImplementation();
                InputStream inputStreamFromNode = nodeToInputStream(node);

                parser.parse(inputStreamFromNode, handler);
                VkProduct createdProduct = new VkProduct(
                        handler.productInnerId,
//                        this.ownerId,
                        handler.productName,
                        handler.productDescription,
                        handler.productCategoryId,
                        handler.productPrice,
                        handler.productMainPhotoURI,
                        handler.photosURI);
                //утановка дополнительных параметров
                if (handler.productType != null) {
                    createdProduct.setProductType(handler.productType);
                }
                if (handler.productPropertyType != null) {
                    createdProduct.setProductPropertyType(handler.productPropertyType);
                }
                productList.add(createdProduct);

            } catch (ParserConfigurationException | SAXException | TransformerException | IOException e) {
                e.printStackTrace();
            }
        }

        return createProducts(node.getNextSibling(), productList);
    }

    /**
     * Конвертер ноды {@link Document} в {@link InputStream} для работы парсера {@link SAXParser}
     * @param node нода которую нужно перевести в поток для парсинга
     * @return ноду преабразованную в поток {@link InputStream}
     * @throws TransformerException если что-то пошло не так
     */
    private InputStream nodeToInputStream(Node node) throws TransformerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Result outputTarget = new StreamResult(outputStream);
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.transform(new DOMSource(node), outputTarget);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * Внутренний класс, используется только в {@link VkProductCreator}.
     * Имплементация хендлера для {@link SAXParser} чтобы разобрать документ
     * и найти элементы нужны для создания {@link VkProduct}
     */
    private class SaxHandlerImplementation extends DefaultHandler {

        Integer productInnerId;

        private String productType;

        private String productPropertyType;

        String productName;

        String productDescription;

        Integer productCategoryId;

        Float productPrice;

        Integer productMainPhotoId;

        List<Integer> photoIds;

        String productMainPhotoURI;

        List<String> photosURI;

        XmlTags currentElement;

        @Override
        public void startDocument() throws SAXException {
//            photoIds = new ArrayList<>();
            photosURI = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "offer":
                    currentElement = XmlTags.offer;
                    int innerIdAttributeIndex = attributes.getIndex("internal-id");
                    productInnerId = Integer.parseInt(attributes.getValue(innerIdAttributeIndex));
                    break;
                case "title-market-vk":
                    currentElement = XmlTags.title;
                    break;
                case "description-market-vk":
                    currentElement = XmlTags.description;
                    break;
                case "cost-market-vk":
                    currentElement = XmlTags.price;
                    break;
                case "type":
                    currentElement = XmlTags.type;
                    break;
                case "property-type":
                    currentElement = XmlTags.propertyType;
                    break;
                case "category":
                    currentElement = XmlTags.category;
                    break;
                case "image":
                    currentElement = XmlTags.photo;
                    break;
                default:
                    currentElement = XmlTags.blank;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            switch (currentElement) {
                case type:
                    productType = new String(ch, start, length).trim();
                    break;
                case propertyType:
                    productPropertyType = new String(ch, start, length).trim();
                    break;
                case category:
                    setUpProductCategory(new String(ch, start, length).trim());
                    break;
                case title:
                    productName = new String(ch, start, length).trim();
                    break;
                case description:
                    productDescription = productDescription == null
                            ? new String(ch, start, length).trim()
                            : productDescription + new String(ch, start, length);
                    break;
                case price:
                    String priceString = new String(ch, start, length).trim();
                    productPrice = priceString.isEmpty() ? 0 : Float.parseFloat(priceString);
                    break;
                case photo:
                    if (productMainPhotoURI == null || photosURI.size() < 4) {
                        String uri = new String(ch, start, length).trim();
                        if (productMainPhotoURI == null) {
                            productMainPhotoURI = uri;
                        } else {
                            photosURI.add(uri);
                        }
                    }
/*
                        String uri = new String(ch, start, length).trim();
                        VkImage vkImage = new VkImage(url, properties.getProperty("vk.group.id"), true, null, null, null);
                        Integer photoId = VkMethodsManager.getInstance().saveImageForProduct(vkImage);
                        if (productMainPhotoId == null) {
                            productMainPhotoId = photoId;
                        } else {
                            photoIds.add(photoId);
                        }
                    }
*/
                    break;
                default:
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentElement = XmlTags.blank;
        }

        /**
         * Устанавливает категорию товара в зависимоти от значения тега category
         * @param category значение тега category
         */
        private void setUpProductCategory(String category) {
            switch (category) {
                case "квартира":
                    productCategoryId = Integer.parseInt(properties.getProperty("vk.category.id.apartments"));
                    break;
                case "комната":
                    productCategoryId = Integer.parseInt(properties.getProperty("vk.category.id.rooms"));
                    break;
                case "дом":
                    productCategoryId = Integer.parseInt(properties.getProperty("vk.category.id.houses"));
                    break;
                case "гараж":
                    productCategoryId = Integer.parseInt(properties.getProperty("vk.category.id.garages"));
                    break;
                case "участок":
                    productCategoryId = Integer.parseInt(properties.getProperty("vk.category.id.lands"));
                    break;
                case "коммерческая":
                    productCategoryId = Integer.parseInt(properties.getProperty("vk.category.id.commercial"));
                    break;
                case "зарубежная":
                    productCategoryId = Integer.parseInt(properties.getProperty("vk.category.id.overseas"));
                    break;
            }
        }

        //тут будут геттеры свойств продукта, если понадобятся
    }
}
