DROP ALL OBJECTS; -- Удаляет все объекты базы данных (таблицы и пр.), если они существуют
create table IF NOT EXISTS MPA -- Создаёт таблицу MPA (Минимальная Премия Ассоциации)
(
    MPA_ID   INTEGER auto_increment,  -- Идентификатор мемнния, автоинкремент
    MPA_NAME CHARACTER VARYING(50) not null,  -- Название мемнния, не может быть null
    constraint MPA_PK
        primary key (MPA_ID)  -- Устанавливает MPA_ID как первичный ключ
);
create table IF NOT EXISTS FILMS
(
    FILM_ID       INTEGER auto_increment,  -- Идентификатор фильма, автоинкремент
    FILM_NAME     CHARACTER VARYING(50)  not null,  -- Название фильма, не может быть null
    DESCRIPTION   CHARACTER VARYING(200) not null,  -- Описание фильма, не может быть null
    RELEASE_DATE  DATE                   not null,  -- Дата релиза, не может быть null
    DURATION      INTEGER                not null,  -- Длительность фильма, не может быть null
    RATING        INTEGER default 0      not null,  -- Рейтинг фильма, по умолчанию 0, не может быть null
    MPA_RATING_ID INTEGER                not null,  -- Внешний ключ на MPA, который не может быть null
    constraint FILMS_PK
        primary key (FILM_ID),  -- Устанавливает FILM_ID как первичный ключ
    constraint FILMS_MPA_MPA_ID_FK
        foreign key (MPA_RATING_ID) references MPA  -- Устанавливает внешний ключ на таблицу MPA
);
create table IF NOT EXISTS GENRE
(
    GENRE_ID   INTEGER auto_increment,  -- Идентификатор жанра, автоинкремент
    GENRE_NAME CHARACTER VARYING(50) not null,  -- Название жанра, не может быть null
    constraint GENRE_PK
        primary key (GENRE_ID)  -- Устанавливает GENRE_ID как первичный ключ
);
create table IF NOT EXISTS FILM_GENRE
(
    GENRE_ID INTEGER not null,  -- Идентификатор жанра, не может быть null
    FILM_ID  INTEGER not null,   -- Идентификатор фильма, не может быть null
    constraint FILM_GENRE_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,  -- Устанавливает внешний ключ на таблицу FILMS
    constraint FILM_GENRE_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE  -- Устанавливает внешний ключ на таблицу GENRE
);
create table IF NOT EXISTS USERS
(
    USER_ID   INTEGER auto_increment,  -- Идентификатор пользователя, автоинкремент
    USER_NAME CHARACTER VARYING(50),  -- Имя пользователя
    LOGIN     CHARACTER VARYING(50) not null,  -- Логин, не может быть null
    EMAIL     CHARACTER VARYING(50) not null,  -- Email, не может быть null
    BIRTHDAY  DATE                  not null,  -- Дата рождения, не может быть null
    constraint USERS_PK
        primary key (USER_ID)  -- Устанавливает USER_ID как первичный ключ
);
create table IF NOT EXISTS LIKES
(
    LIKE_ID INTEGER auto_increment,  -- Идентификатор лайка, автоинкремент
    FILM_ID INTEGER not null,  -- Идентификатор фильма, не может быть null
    USER_ID INTEGER not null,  -- Идентификатор пользователя, не может быть null
    constraint LIKES_PK
        primary key (LIKE_ID),  -- Устанавливает LIKE_ID как первичный ключ
    constraint LIKES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,  -- Устанавливает внешний ключ на таблицу FILMS
    constraint LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS  -- Устанавливает внешний ключ на таблицу USERS
);
create table IF NOT EXISTS FRIENDS
(
    RELATIONSHIP_ID INTEGER auto_increment,  -- Идентификатор взаимоотношений, автоинкремент
    USER_ID         INTEGER               not null,  -- Идентификатор пользователя, не может быть null
    FRIEND_ID       INTEGER               not null,  -- Идентификатор друга, не может быть null
    STATUS          BOOLEAN default FALSE not null,  -- Статус дружбы (добавлен/удален), по умолчанию FALSE, не может быть null
    constraint FRIENDS_PK
        primary key (RELATIONSHIP_ID),  -- Устанавливает RELATIONSHIP_ID как первичный ключ
    constraint FRIENDS_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS,  -- Устанавливает внешний ключ на таблицу USERS
    constraint FRIENDS_USERS_USER_ID_FK_2
        foreign key (FRIEND_ID) references USERS  -- Устанавливает внешний ключ на таблицу USERS (друг)
);