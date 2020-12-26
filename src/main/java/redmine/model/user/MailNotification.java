package redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum MailNotification {
    ALL("Все события"),
    ONLY_MY_EVENTS("Мои события"),
    ONLY_ASSIGNED("Назначенные события"),
    ONLY_OWNER("Где я владелец"),
    NONE("Нет событий");


    private final String description;

    public static MailNotification of(final String description) {
        return Stream.of(values())
                .filter(value -> value.description.contains(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Уведомление не найдено по описанию"));
    }
}
