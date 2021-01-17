package redmine.api.interfaces;

import java.util.Map;

public interface Request {

    String getUri();

    HttpMethods getMethods();

    Map<String, String> getParameters();

    Map<String, String> getHeaders();

    Object getBody();
}
