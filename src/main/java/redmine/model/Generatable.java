package redmine.model;

/**
 * Класс-интерфейс обозначающий операции, которые возможно производить с пользователем
 */

public interface Generatable<T> {

    T read();

    T update();

    T create();

    void delete();

    default T generate() {
        if (read()!=null) {
            return update();
        } else {
            return create();
        }
    }
}
