package redmine.managers;

import java.util.HashMap;
import java.util.Map;

public class Stash {

    private Map<String, Object> entities = new HashMap<>();

    public void put(String stashId, Object entity) {
        entities.put(stashId, entity);
    }

    public <T> T get(String stashId, Class<T> clazz) {
        return clazz.cast(get(stashId));
    }

    public Object get(String stashId) {
        return entities.get(stashId);
    }

    Map<String, Object> getEntities() {
        return entities;
    }

}
