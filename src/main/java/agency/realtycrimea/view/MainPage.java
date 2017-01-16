package agency.realtycrimea.view;

import agency.realtycrimea.vk.api.VkAuthMethods;
import agency.realtycrimea.vk.model.VkProduct;
import agency.realtycrimea.vk.utility.ResourceCreator;
import agency.realtycrimea.vk.utility.VkProductCreator;
import org.w3c.dom.Document;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by Bender on 14.01.2017.
 */
@ManagedBean (name = "mainPage")
@SessionScoped
public class MainPage {

    private List<VkProduct> loadedProductList;

    public void NOP(){
        System.out.println("test");
    };

    public boolean isVkAuthorized() {
        return VkAuthMethods.isCodeObtained() && VkAuthMethods.isTokenObtained();
    }

    public void loadProducts() {
        Document document = new ResourceCreator().createXMLResource();
        VkProductCreator creator = new VkProductCreator();

        creator.createProducts(document);
        loadedProductList = creator.getVkProductList();
    }

    // геттеры и сеттеры
    public List<VkProduct> getLoadedProductList() {
        return loadedProductList;
    }

    public void setLoadedProductList(List<VkProduct> loadedProductList) {
        this.loadedProductList = loadedProductList;
    }
}
