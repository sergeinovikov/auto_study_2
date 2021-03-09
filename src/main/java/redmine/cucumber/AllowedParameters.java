package redmine.cucumber;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class AllowedParameters {
    static final List<String> ROLE_PARAMETERS = ImmutableList.of(
            "Просмотр задач",
            "Права"
    );

    static final List<String> USER_PARAMETERS = ImmutableList.of(
            "Администратор",
            "Статус"
    );

    static final List<String> PROJECT_PARAMETERS = ImmutableList.of(
            "Публичный",
            "Статус"
    );
}