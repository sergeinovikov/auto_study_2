package redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

/**
 * Класс-Enum содержайщий перечесление видов уведомлений по почте
 */

@AllArgsConstructor
public enum MailNotification {
    ALL("Все события"),
    ONLY_MY_EVENTS("Мои события"),
    ONLY_ASSIGNED("Назначенные события"),
    ONLY_OWNER("Где я владелец"),
    NONE("Нет событий");

    private final String description;

    private String getDescription() {
        return description;
    }

    public static MailNotification of(final String description) {
        return (description.isEmpty())
                ? NONE
                : Stream.of(values())
                    .filter(value -> value.name().equals(description.toUpperCase()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Уведомление не найдено по описанию:" + description));
    }
}
