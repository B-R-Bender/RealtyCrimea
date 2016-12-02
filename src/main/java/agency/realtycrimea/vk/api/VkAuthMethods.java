package agency.realtycrimea.vk.api;

import agency.realtycrimea.view.LoginForm;
import agency.realtycrimea.vk.api.interfaces.VkApiMethod;
import agency.realtycrimea.vk.utility.AppProperty;

import javax.faces.bean.ManagedProperty;

/**
 * Created by Bender on 30.11.2016.
 */
public enum VkAuthMethods implements VkApiMethod {

    openAuthDialog {
        @Override
        public String getExactMethod() {
            return "https://oauth.vk.com/authorize?"
                    + "client_id=" + AppProperty.properties.getProperty("vk.client.id")
                    + "&scope=market"
                    + "&display=popup"
                    + "&redirect_uri=" + AppProperty.properties.getProperty("vk.client.uri.local")
                    + "&response_type=token"
                    + "&v=5.60";
        }
    },
    getCode {
        @Override
        public String getExactMethod() {
            return "https://oauth.vk.com/authorize?"
                    + "client_id=" + AppProperty.properties.getProperty("vk.client.id")
                    + "&scope=market"
                    + "&display=popup"
                    + "&redirect_uri=" + AppProperty.properties.getProperty("vk.client.uri.local")
                    + "&response_type=code"
                    + "&v=5.60";
        }
    },
    getAccessToken {
        @Override
        public String getExactMethod() {
            return "https://oauth.vk.com/access_token?"
                    + "client_id=" + AppProperty.properties.getProperty("vk.client.id")
                    + "&client_secret=" + AppProperty.properties.getProperty("vk.client.secure.key")
                    + "&redirect_uri=" + AppProperty.properties.getProperty("vk.client.uri.local")
                    + "&code=" + code;
        }
    };

/*
    @ManagedProperty(value = "#{loginController}")
    private LoginForm form;
*/

    private static String code;

    private static String token;

    private Long tokenExpiresIn;

    public static void setCode(String vkCode) {
        code = vkCode;
    }

}
