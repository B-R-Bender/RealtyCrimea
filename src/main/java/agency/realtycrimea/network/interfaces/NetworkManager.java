package agency.realtycrimea.network.interfaces;

import agency.realtycrimea.network.SimpleRequest;

import java.util.Map;

/**
 * Created by Bender on 23.11.2016.
 */
public interface NetworkManager {

    /**
     * Метод, отправляющий Get запрос в сеть и возвращающий ответ сервера в виде java.lang.Object
     *
     * @return ответ сервера в виде java.lang.Object
     */
    Object sendGetRequest(SimpleRequest request);

    /**
     * Метод, отправляющий Post запрос в сеть и возвращающий ответ сервера в виде java.lang.Object
     *
     * @return ответ сервера в виде java.lang.Object
     */
    Object sendPostRequest(SimpleRequest request);

}
