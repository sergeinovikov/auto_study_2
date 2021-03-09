#language: ru
@UI

Функция: UI Кейс 8. Администрирование. Создание пользователя

  Предыстория:
    Пусть Существует пользователь "Администратор"
      | Администратор | true |
      | Статус        | 1    |
    И Открыт браузер на странице авторизации

  Сценарий: Авторизация пользователя с правами администратора.  Переход на страницы \"Админитрирование\" и \"Пользователи\". Создание пользователя на клиентской части. Проверка созданного пользователя в базе данных
    Если Авторизоваться пользователем "Администратор"
    То На странице "Заголовок" отображается элемент "Заголовок страницы" с текстом "Моя страница"

    Если На странице "Заголовок" нажать на элемент "Администрирование"
    То На странице "Заголовок" отображается элемент "Заголовок страницы" с текстом "Администрирование"

    Если На странице "Администрирование" нажать на элемент "Пользователи"
    То На странице "Заголовок" отображается элемент "Заголовок страницы" с текстом "Пользователи"

    Если На странице "Пользователи" нажать на элемент "Новый пользователь"
    То На странице "Заголовок" отображается элемент "Заголовок страницы" с текстом "Пользователи » Новый пользователь"

    Если Заполнить данные нового пользователя "Пользователь" случаныйми корректными значениями
    И На странице "Новый пользователь" нажать на элемент "Задать пароль"
    И На странице "Новый пользователь" нажать на элемент "Создать"
    То На странице "Новый пользователь" отображается элемент "Уведомление" с текстом "Пользователь ${Пользователь->логин} создан."
    И В базе данных в таблице users появилась запись с данными пользователя "Пользователь"