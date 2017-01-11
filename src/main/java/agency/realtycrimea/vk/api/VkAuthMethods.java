package agency.realtycrimea.vk.api;

import agency.realtycrimea.vk.api.interfaces.VkApiMethod;
import agency.realtycrimea.vk.utility.AppProperty;

import java.util.Date;

/**
 * Created by Bender on 30.11.2016.
 */
public enum VkAuthMethods implements VkApiMethod {

    /**
     * Клиентский способ авторизиции в vk.api
     * <br>
     * <b>в данный момент неприменим</b>
     */
    @Deprecated
    openAuthDialog {
        @Override
        public String getExactMethod() {
            return "https://oauth.vk.com/authorize?"
                    + "client_id=" + AppProperty.properties.getProperty("vk.client.id")
                    + "&scope=" + AppProperty.properties.getProperty("vk.client.secure.scope")
                    + "&display=popup"
                    + "&redirect_uri=" + AppProperty.properties.getProperty("vk.client.uri.local")
                    + "&response_type=token"
                    + "&v=" + AppProperty.properties.getProperty("vk.api.version");
        }

        @Override
        public String getMethodName() {
            return "authorize";
        }
    },
    /**
     * Метод получения ключа пользователя, нужен для получения приложением токена доступа
     */
    getCode {
        @Override
        public String getExactMethod() {
            return "https://oauth.vk.com/authorize?"
                    + "client_id=" + AppProperty.properties.getProperty("vk.client.id")
                    + "&scope=" + AppProperty.properties.getProperty("vk.client.secure.scope")
                    + "&display=popup"
                    + "&redirect_uri=" + AppProperty.properties.getProperty("vk.client.uri.local")
                    + "&response_type=code"
                    + "&v=" + AppProperty.properties.getProperty("vk.api.version");
        }

        @Override
        public String getMethodName() {
            return "authorize";
        }
    },
    /**
     * Получение токена доступа к vk.api на основании ключа пользователя
     */
    getAccessToken {
        @Override
        public String getExactMethod() {
            return "https://oauth.vk.com/access_token?"
                    + "client_id=" + AppProperty.properties.getProperty("vk.client.id")
                    + "&client_secret=" + AppProperty.properties.getProperty("vk.client.secure.key")
                    + "&redirect_uri=" + AppProperty.properties.getProperty("vk.client.uri.local")
                    + "&code=" + code;
        }

        @Override
        public String getMethodName() {
            return "access_token";
        }
    };

    /**
     * Код полученный от vk.api при авторизации пользоваетля
     */
    private static String code;

    /**
     * Токен для работы с vk.api полученный по ключу пользователя
     */
    private static String token;

    /**
     * Время жизни токена - в момент получения к текущей дате прибавляется время его жизни.
     * <br>
     * таким образом понятно когда он станет невалидным
     */
    private static Date tokenExpiresIn;

    /**
     * ID пользователя вконтактика, от которго получен ключ и затем токен.
     * <br>
     * т.е. от чьего имени работает сейчас приложение
     */
    private static Long vkUserId;

    // геттеры и сеттеры

    /**
     * Флаг, показывающий получен ли код
     * @return true если код != null
     */
    public static boolean isCodeObtained() {
        return code != null;
    }

    /**
     * Флаг, показывающий получен ли токен
     * @return true если токен != null
     */
    public static boolean isTokenObtained() {
        return token != null;
    }

    /**
     * Защищенный геттер для доступа к токену из других классов пакета VK.API
     * и наследников если такие будут ))
     * @return токен
     */
    protected static String getToken() {
        return token;
    }

    /**
     * Получить время жизни токена
     * @return дату и время, когда закончится срок действия токена
     */
    public static Date getTokenExpiresIn() {
        return tokenExpiresIn;
    }

    public static Long getVkUserId() {
        return vkUserId;
    }

    public static void setCode(String vkCode) {
        code = vkCode;
    }

    public static void setToken(String token) {
        VkAuthMethods.token = token;
    }

    public static void setTokenExpiresIn(Date tokenExpiresIn) {
        VkAuthMethods.tokenExpiresIn = tokenExpiresIn;
    }

    public static void setVkUserId(Long vkUserId) {
        VkAuthMethods.vkUserId = vkUserId;
    }

}
