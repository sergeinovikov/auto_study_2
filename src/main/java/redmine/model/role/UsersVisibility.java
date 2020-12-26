package redmine.model.role;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum UsersVisibility {
    ALL("Все активные пользователи"),
    MEMBERS_OF_VISIBLE_PROJECTS("Участники видимых проектов");

    private final String description;

    public static UsersVisibility of(final String description) {
        return Stream.of(values())
                .filter(value -> value.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Тип видимости пользователей не найден по описанию: " + description));
    }
}
