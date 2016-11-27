package agency.realtycrimea.network;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Класс описывает составляющие запроса, отправляемого серверу.
 * <br>
 * содержит URL запроса и параметры запроса
 *
 * Created by Bender on 24.11.2016.
 */
public class SimpleRequest {

    /**
     * Перечисление типов отправляемого запроса
     */
    public enum RequestType {
        POST,
        GET;
    }

    /**
     * URL по которому должен уйти запрос
     */
    private URI uri;

    /**
     * Мапа параметров запроса если они есть
     */
    private Map requestParametersMap;

    /**
     * Конкретный тип запроса
     */
    private RequestType type;

    /**
     * Конструктор по умолочанию приниматет URL и тип запроса, если нужны параметры, они устанавливаются сеттером.
     * @param url на который должен уйти запрос
     * @param requestType тип создаваемого запроса
     */
    public SimpleRequest(String uri, RequestType requestType) {
        try {
            this.uri = new URI(uri);
            this.type = requestType;
        } catch (URISyntaxException e) {
            //TODO: вывести ошибку в лог
        }
    }

    /**
     * Получить URL запроса
     * @return строку с URL
     */
    public URI getUri() {
        return uri;
    }

    /**
     * Получить тип запроса
     * @return тип запроса
     */
    public RequestType getType() {
        return type;
    }

    /**
     * Получить мапус с параметрами запроса
     * @return мапа с параметрами запроса
     */
    public Map getRequestParametersMap() {
        return requestParametersMap;
    }

    /**
     * Установить мапу с параметрами запроса
     * @param requestParametersMap мапа которая будет преобразована в параметры запроса
     */
    public void setRequestParametersMap(Map requestParametersMap) {

        this.requestParametersMap = requestParametersMap;
    }
}
