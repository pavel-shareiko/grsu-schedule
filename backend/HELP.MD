# GRSU Schedule

## Стек

* Java 11
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Web
* PostgreSQL
* Lombok
* MapStruct
* Liquibase
* Keycloak

## Авторизация

Авторизация в приложении происходит через Keycloak. При первом развертывании необходимо создать пользователя.
Для этого необходимо перейти по ссылке http://localhost:8180/auth/ и войти в административную панель,
используя данные учетной записи администратора, указанные при запуске контейнера.

### Пошаговая настройка Keycloak:

1. Создать realm с именем `grsu-schedule`
2. В созданном realm создать клиента с именем `grsu-schedule-client`
3. В настройках клиента в поле `Valid Redirect URIs` указать `http://localhost:8080/*`
4. Создать роли `admin` и `user`
5. Создать пользователя
6. Настроить `Client Scopes` путем установки `microprofile-jwt` в значение `default`
