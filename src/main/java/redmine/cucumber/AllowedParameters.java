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
            "Статус",
            "Язык"
    );

    static final List<String> PROJECT_PARAMETERS = ImmutableList.of(
            "Публичный",
            "Статус"
    );

    static final List<String> ERRORS_PARAMETERS = ImmutableList.of(
            "Email has already been taken",
            "Login has already been taken",
            "Email is invalid",
            "Login has already been taken",
            "Password is too short (minimum is 8 characters)"
    );
}