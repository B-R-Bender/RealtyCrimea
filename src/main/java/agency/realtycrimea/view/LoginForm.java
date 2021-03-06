package agency.realtycrimea.view;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.network.VkNetworkManager;
import agency.realtycrimea.vk.api.VkAuthMethods;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Created by Bender on 30.11.2016.
 */

@ManagedBean (name = "loginController")
@SessionScoped
public class LoginForm {

    /**
     * Имя пользователя
     */
    private String userName;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Флаг залогинен ли
     */
    private boolean isLoggedIn;

    @PostConstruct
    public void init() {
        try {
            Class.forName("agency.realtycrimea.vk.utility.AppProperty");
        } catch (ClassNotFoundException e) {
            //TODO: ошибку в логер
            e.printStackTrace();
        }
    }

    /**
     * Конструктор по умолчанию
     */
    public LoginForm() {
    }

    /**
     * Обработка данных формы для авторизации пользователя
     * TODO: реализовать логин и перенаправление на страницу с функцилональностью
     */
    public void login() {
        if (userName.equals("Dimamus")&&password.equals("")) {
            isLoggedIn = true;
            processVkAuthorization();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", "Ошибка авторизации"));
        }
    }

    public void processVkAuthorization() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(VkAuthMethods.getCode.getExactMethod());
        } catch (IOException e) {
            //TODO: add log
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Ошибка авторизации"));
        }
    }

    public void start() throws IOException {
        SimpleRequest request = new SimpleRequest(VkAuthMethods.getAccessToken.getExactMethod(), SimpleRequest.RequestType.GET);
        VkNetworkManager manager = new VkNetworkManager();
        manager.sendRequest(request);

//        XmlNetworkManager xmlNetworkManager = new XmlNetworkManager();
//        Document xmlDocument = xmlNetworkManager.sendRequest(xmlRequest);
//
//        VkProductCreator productCreator = new VkProductCreator();
//        List<VkProduct> vkProduct = productCreator.createProducts(xmlDocument);
    }

    //геттеры и сеттеры
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
