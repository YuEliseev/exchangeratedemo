# exchangeratedemo
Перед вами демонстрационное приложение, связывающееся с сервисом openexchangerates.org и сравнивающее актуальный курс валюты с её же курсом за прошедшее время. Выбранная валюта сравнивается с USD.
По результатам сравнения возвращается рандомная gif в зависимости от просадки курса, его роста или стабильности.

Использованы следующие технологии:
- Gradle;
- Java corretto-11;
- Spring boot 2.7.0;
- Openfeign;
- Thymeleaf.

Все запросы приходят на HTTP endpoin
Приложение построено на архитектуре MVC

Для старта приложения необходимо:
- изменить upload-path в application.properties;
- создать базу данных exchangeratedemo через pgAdmin;
- добавить в эту базу таблицы (SQL-команды ниже).


```
CREATE TABLE request (
  id UUID NOT NULL,
   date date,
   comparing_date date,
   symbols VARCHAR(255),
   latest_rates DECIMAL,
   comparing_rate DECIMAL,
   CONSTRAINT pk_request PRIMARY KEY (id)
);
```
```
CREATE TABLE user_role (
  user_id UUID NOT NULL,
   role VARCHAR(255)
);
```
```
CREATE TABLE usr (
  id UUID NOT NULL,
   username VARCHAR(255),
   password VARCHAR(255),
   active BOOLEAN NOT NULL,
   CONSTRAINT pk_usr PRIMARY KEY (id)
);
```
ALTER TABLE user_role ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES usr (id);

Все параметры задаются через application.properties

- tagRich = rich - тэг для запроса gif при росте валютного курса
- tagBroke = broke - тэг для запроса gif при просадке валютного курса
- tagBalance = balance - тэг для запроса gif если курс не изменился
- symbols = RUB - валюта, сравниваемая с USD
- comparisonDate = 10 - количество дней с настоящего дня для получения даты сравнения

P.S. 
Этот проект находится в разработке
