package agency.realtycrimea.network;

import java.util.Map;

/**
 * Created by Bender on 24.11.2016.
 */
public class SimpleRequest {

    private String url;

    private Map requestParametersMap;

    public SimpleRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Map getRequestParametersMap() {
        return requestParametersMap;
    }

    public void setRequestParametersMap(Map requestParametersMap) {
        this.requestParametersMap = requestParametersMap;
    }
}
