package redmine.model.role;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum TimeEntriesVisibility {
    ALL("Все трудозатраты"),
    OWN("Только собственные трудозатраты");

    private final String description;

    public static TimeEntriesVisibility of(final String description) {
        return Stream.of(values())
                .filter(value -> value.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Тип трудохатрат не найден по описанию: " + description));
    }
}
