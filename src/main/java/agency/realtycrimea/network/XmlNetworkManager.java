package agency.realtycrimea.network;

import agency.realtycrimea.network.interfaces.NetworkManager;
import sun.net.www.http.HttpClient;

import java.io.IOException;

/**
 * Created by Bender on 22.11.2016.
 */
public class XmlNetworkManager implements NetworkManager {

    @Override
    public Object sendGetRequest(SimpleRequest request) {

        try {

            HttpClient httpClient = HttpClient.New(request.getUrl());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object sendPostRequest(SimpleRequest request) {
        return null;
    }
}
