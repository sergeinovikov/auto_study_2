package redmine.model;

import io.qameta.allure.Step;

/**
 * Класс-интерфейс обозначающий операции, которые возможно производить с пользователем
 */

public interface Generatable<T> {

    T read();

    T update();

    T create();

    @Step("Сущность удалена")
    void delete();

    @Step("Сущность сгенерирована")
    default T generate() {
        if (read()!=null) {
            return update();
        } else {
            return create();
        }
    }
}
