package redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

/**
 * Класс-Enum содержайщий перечесление видов уведомлений по почте
 */

@AllArgsConstructor
public enum Action {
    API("Ключ API"),
    SESSION("Ключ сессии"),
    FEEDS("Ключ обратной связи");
    
    private final String description;

    private String getDescription() {
        return description;
    }

    public static Action of(final String description) {
        return Stream.of(values())
                .filter(value -> value.name().equals(description.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Тип ключа не найден по описанию:" + description));
    }
}

