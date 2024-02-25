# Сервис Filmorate - сервис кинопоиска с возможностью оставлять отзывы и изменять рейтинг

## Стек технологий: Java, Spring Boot, Maven, Git, REST Api, Lombok, SQL, H2, JDBS, JdbcTemplate.

### Данная программа обеспечивает возможности:
- просмотр общих с другом фильмов с сортировкой по их популярности;
- вывод топ-N фильмов по количеству лайков;
- поиск по названию фильмов;
- просмотр оценок пользователей;
- получение топ-5 фильмов, рекомендованных к просмотру;

#### В проекте реализованы: 

<details>
    <summary><h3> Спринт 9:</h3></summary>

Выполнено проектирование согласно Техническому заданию:
1. Определены модели данных приложения: Film, User;
2. Организовано предварительное хранение данных;
3. Созданы REST-контроллеры: FilmController, UserController;
3. Задана валидация данных;
4. Выполнено логирование данных;
5. Валидация проверяется тестами Unit5.
</details>

<details>
	<summary><h3> Спринт 10:</h3></summary>
1. Переработана архитектура проекта:

* созданы интерфейсы FilmStorage и UserStorage; 
* созданы классы InMemoryFilmStorage и InMemoryUserStorage (@Component); 
* созданы классы UserService и FilmService (@Service);
 
2. API доведен до соответствия REST;
3. Настроен ExceptionHandler для централизованной обработки ошибок
</details>


<details>
	<summary><h3> Спринт 11. </h3></summary>
Часть 1:
 
1. Спроектирована схема БД:
   ![ER-диаграмма](/ER_diagram.png)

2. ### Примеры запросов

<details>
	<summary><h3>Работа с фильмами:</h3></summary>

* Запрос фильма по id:

```SQL
SELECT f.name,
       f.description,
       f.release_date,
       f.duration,
       m.name,
       g.name
FROM films f
JOIN mpa m ON f.mpa_id = m.id
JOIN films_genres fg ON f.id = fg.film_id
JOIN genres g ON fg.genre_id = g.id
WHERE f.id = ?;
```   

* Запрос всех фильмов:

```SQL
SELECT f.name,
       f.description,
       f.releaseDate,
       f.duration,
       m.name,
       g.name
FROM films f
JOIN mpa m ON f.mpa_id = m.id
JOIN films_genres fg ON f.id = fg.film_id
JOIN genres g ON fg.genre_id = g.id;
```

* Запрос топ-N фильмов по количеству лайков:
```SQL
SELECT f.name,
       COUNT(l.film_id) AS likes_count
FROM films f
JOIN likes l ON f.id = l.film_id
GROUP BY f.name
ORDER BY likes_count DESC
LIMIT N;
```
</details>

<details>
    <summary><h3>Работа с пользователями:</h3></summary>

* Запрос пользователя по id:

```SQL
SELECT *
FROM users
WHERE id = ?;
```   

* Запрос всех пользователей:

```SQL
SELECT *
FROM users;
``` 

</details>

<details>
    <summary><h3>Работа с жанрами:</h3></summary>

* Запрос жанра по id:

```SQL
SELECT *
FROM genres
WHERE id = ?;
``` 

* Запрос всех жанров:

```SQL
SELECT *
FROM genres;
```   
</details>

<details>
<summary><h3>Работа с рейтингами MPA:</h3></summary>

* Запрос рейтинга по id:

```SQL
SELECT *
FROM mpa
WHERE id = ?;
``` 

* Запрос всех рейтингов MPA:

```SQL
SELECT *
FROM mpa;
```   
</details>

</details>
