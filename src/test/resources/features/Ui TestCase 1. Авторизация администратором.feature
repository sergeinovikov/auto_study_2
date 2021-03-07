#language: ru
@UI

Функция: Авторизация пользователем с правами администратора

  Предыстория:
    Пусть Существует пользователь "Пользователь"
      | Администратор | true |
      | Статус        | 1    |
    И Открыт браузер на странице авторизации

  Сценарий: UI Кейс 1. Авторизация пользователем с правами администратора. Проверка присутствия элементов страницы после авторизации
    Если Авторизоваться пользователем "Пользователь"
    То На странице "Заголовок" отображается элемент "Заголовок страницы"
    И На странице "Заголовок" отображается элемент "Вошли как" с текстом "Вошли как ${Пользователь->логин}"
    И На странице "Заголовок" отображается элемент "Домашняя страница"
    И На странице "Заголовок" отображается элемент "Моя страница"
    И На странице "Заголовок" отображается элемент "Проекты"
    И На странице "Заголовок" отображается элемент "Администрирование"
    И На странице "Заголовок" отображается элемент "Помощь"
    И На странице "Заголовок" отображается элемент "Моя учётная запись"
    И На странице "Заголовок" отображается элемент "Выйти"
    И На странице "Заголовок" не отображается элемент "Войти"
    И На странице "Заголовок" не отображается элемент "Регистрация"
    И На странице "Заголовок" отображается элемент "Имя поиска"
    И На странице "Заголовок" отображается элемент "Поиск"




