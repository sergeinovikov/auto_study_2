#language: ru
@API

Функция: API Кейс 3. Получение пользователей. Пользователь без прав администратора

  Предыстория:
    Пусть Существует пользователь "Первый пользователь"
      | Статус        | 1          |
    Пусть Существует пользователь "Другой пользователь"
      | Статус        | 1          |
    И Создан API-клиент "API-клиент с ключом первого пользователя" с API-ключом пользователя "Первый пользователь"

  Сценарий:  Получение пользователей используя API-ключ пользователя без прав администратора
    Если Создать GET-запрос "Запрос на получение пользователя" с данными пользователя "Первый пользователь"
    И По запросу "Запрос на получение пользователя" через API-клиент "API-клиент с ключом первого пользователя" получить ответ "Ответ по получению пользователя"
    То В ответе "Ответ по получению пользователя" статус код ответа "200"
    И В теле ответа "Ответ по получению пользователя" присутствует информация "Первый пользователь", включая поля поля admin: false, api_key

    Если Создать GET-запрос "Запрос на получение другого пользователя" с данными пользователя "Другой пользователь"
    И По запросу "Запрос на получение другого пользователя" через API-клиент "API-клиент с ключом первого пользователя" получить ответ "Ответ по получению другого пользователя"
    То В ответе "Ответ по получению другого пользователя" статус код ответа "200"
    И В теле ответа "Ответ по получению другого пользователя" присутствует информация пользователя "Другой пользователь", но отсутствуют поля admin: false, api_key