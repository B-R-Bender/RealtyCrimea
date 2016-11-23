package agency.realtycrimea.network.interfaces;

/**
 * Created by Bender on 23.11.2016.
 */
public interface networkManager {

    /**
     * Метод, отправляющий Get запрос в сеть и возвращающий ответ сервера в виде java.lang.Object
     *
     * @return ответ сервера в виде java.lang.Object
     */
    Object sendGetRequest();

    /**
     * Метод, отправляющий Post запрос в сеть и возвращающий ответ сервера в виде java.lang.Object
     *
     * @return ответ сервера в виде java.lang.Object
     */
    Object sendPostRequest();

}
