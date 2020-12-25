package redmine.model.role;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum IssuesVisibility {
    ALL("Все задачи"),
    DEFAULT("Только общие задачи"),
    OWN("Задачи созданные или назначенные пользователю");

    private final String description;

    public static IssuesVisibility of(final String description) {
        return Stream.of(values())
                .filter(value -> value.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Тип видимости задач не найден по описанию"));
    }
}
