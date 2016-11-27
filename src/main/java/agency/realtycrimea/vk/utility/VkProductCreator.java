package agency.realtycrimea.vk.utility;

import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.interfaces.VkObjectCreator;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bender on 22.11.2016.
 */
public class VkProductCreator implements VkObjectCreator {

    @Override
    public List<VkProduct> fabricMethod(Object resource) {
        List<VkProduct> createdVkProductList = null;

        if (resource instanceof Integer) {
            createdVkProductList = createExistingProduct(((Integer) resource));
        } else if (resource instanceof Document) {
            createdVkProductList = createNewProductFromXml(((Document) resource).getChildNodes(), new ArrayList<VkProduct>());
        } else {
            throw new IllegalArgumentException("Illegal resource class: " + resource.getClass());
        }

        return createdVkProductList;
    }

    private List<VkProduct> createNewProductFromXml(NodeList nodes, ArrayList<VkProduct> productList) {

        if (nodes == null
                || nodes.getLength() == 0
                || nodes.item(0).getNodeName().equalsIgnoreCase("/realty-feed")){
            return productList;
        }

        return productList;
    }

    private List<VkProduct> createExistingProduct(Integer productId) {
        List<VkProduct> productList = new ArrayList<>();
        productList.add(new VkProduct(productId));
        return productList;
    }


}
