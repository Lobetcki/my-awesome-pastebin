﻿<div>
## Бэкенд (REST API) для сервиса аналогичного pastebin
</div>

___
## Описание проекта:

Сервис позволяет заливать куски текста/кода ("пасту") и получать на них короткую ссылку, которую можно отправить другим людям.

### Функции:

1. Срок, в течение которого "паста" будет доступна по ссылке (expiration time):
10 мин., 1 час, 3 часа, 1 день, 1 неделя, 1 месяц, без ограничения.
После окончания срока получить доступ к "пасте" нельзя, в том числе и автору.

2. Ограничение доступа:
public — доступна всем
unlisted — доступна только по ссылке
Для загруженной пасты выдается короткая ссылка 

3. Пользователи могут получить информацию о последних 10 загруженных публичных "пастах".

4. Поиск по названию и/или тексту "паст", с учётом ограничений доступа, можно искать только в public пастах.
___
## Инструменты, используемые в проекте:
* **Backend**:
    - Java 11
    - Maven
    - Spring Boot
    - Spring Web
    - Spring Data JPA
    - Stream API
    - REST
    - GIT
    - Swagger
    - Lombok
* **SQL**:
    - H2
    - Liquibase
* **Test**:
    - Junit
    - Mockito
___
## Запуск приложения:
* Для запуска приложения нужно:
    - Клонировать проект и открыть его в среде разработки (например, *IntelliJ IDEA* или *VSCode*);
    - В файле **application.properties** указать путь к Вашей базе данных;
    - Запустить метод **main** в файле **MarketplaceApplication.java**.

___
### Разработчик:
- [Михайловский Антон](https://github.com/Lobetcki)

