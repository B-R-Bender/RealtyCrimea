package agency.realtycrimea.vk.api.interfaces;

import agency.realtycrimea.network.SimpleRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bender on 24.11.2016.
 */
public interface VkApiMethod {

    /**
     * Базовый запрос вызова метода vk.api
     * <br>
     * <b>параметры:</b>
     * <ul>
     * <li>{1} - название метода</li>
     * <li>{2} - строка с параметрами метода</li>
     * <li>{3} - токен</li>
     * <li>{4} - версия vk.api</li>
     * </ul>
     */
    String BASE_API_METHOD = "https://api.vk.com/method/%s?%s&access_token=%s&v=%s";

    /**
     * Мапа с параметрами метода для использования в POST запросах
     */
    Map<String, Object> methodParameters = new HashMap<>();

    /**
     * Получение полностью сформированного метода для обращения к vk.api
     * @return готовую строку метода для передачи в запросе серверу vk.api
     */
    String getExactMethod();

    /**
     * Получение точного имени метода соответствующего vk.api
     * @return строковое представление имени метода vk.api
     */
    String getMethodName();

    default SimpleRequest.RequestType getMethodRequestType() {
        return SimpleRequest.RequestType.GET;
    }

    default Map<String, Object> getAndClearMethodParameters() {
        Map<String, Object> result = new HashMap<>();
        result.putAll(methodParameters);
        methodParameters.clear();
        return result;
    }
}
