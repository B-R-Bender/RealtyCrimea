package agency.realtycrimea.vk.utility;

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

/**
 * Created by Bender on 22.11.2016.
 */
public class VkProductCreator implements VkObjectCreator {

    Integer productInnerId;

    Integer productOwnerId;

    String productName;

    String productDescription;

    Integer productCategoryId;

    Float productPrice;

    Integer productMainPhotoId;

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

    private List<VkProduct> createExistingProduct(Integer productId) {
        List<VkProduct> productList = new ArrayList<>();
        productList.add(new VkProduct(productId));
        return productList;
    }

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


    private ArrayList<VkProduct> createProducts(Node node, ArrayList<VkProduct> productList) {

        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            SaxHandlerImplementation handler = new SaxHandlerImplementation();
            InputStream inputStreamFromNode = nodeToInputStream(node);

            parser.parse(inputStreamFromNode, handler);

            VkProduct createdProduct = new VkProduct(productInnerId,
                                                     productOwnerId,
                                                     productName,
                                                     productDescription,
                                                     productCategoryId,
                                                     productPrice,
                                                     productMainPhotoId);
            productList.add(createdProduct);

        } catch (ParserConfigurationException | SAXException | TransformerException | IOException e) {
            e.printStackTrace();
        }

        Node nextSibling = node.getNextSibling();
        return nextSibling.getNodeName().equals("offer") ? createProducts(nextSibling, productList) : productList;
    }

    private InputStream nodeToInputStream(Node node) throws TransformerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Result outputTarget = new StreamResult(outputStream);
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.transform(new DOMSource(node), outputTarget);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private class SaxHandlerImplementation extends DefaultHandler {
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "description" :
                    productDescription = "test";
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }
}
