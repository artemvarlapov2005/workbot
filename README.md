# workbot

Стартовый stateless REST-сервис на Kotlin и Spring Boot.

## Запуск

```bash
./gradlew bootRun
```

Проверка ручки:

```bash
curl -i http://localhost:8080/api/v1/health
```

Ожидаемый ответ — `HTTP/1.1 200` без тела.

## Архитектура

Проект использует гексагональную архитектуру:

```text
adapter/input/rest  ->  application/port/input  ->  application/service  ->  domain
                                                                  |
                                                    application/port/output
                                                                  |
                                                           adapter/output/jpa
```

- `domain` — независимая от фреймворков предметная область;
- `application/port/input` — use cases, вызываемые входными адаптерами;
- `application/port/output` — интерфейсы зависимостей приложения;
- `application/service` — реализация use cases и оркестрация домена;
- `adapter/input/rest` — HTTP-адаптеры;
- `adapter/output/jpa` — реализации выходных портов на JPA.
- `config` — связывание приложения с Spring.

Зависимости направлены к центру: адаптеры знают application, application знает domain, но domain не знает о Spring, HTTP и JPA. Подключены Spring Web и Spring Data JPA. Для локального старта используется H2 in-memory; сервис не хранит состояние между запросами.
