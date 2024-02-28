create table IF NOT EXISTS MPA
(
    MPA_ID   INTEGER primary key,
    MPA_NAME CHARACTER VARYING not null
);

create table IF NOT EXISTS FILMS
(
    ID      INTEGER auto_increment,
    NAME    CHARACTER VARYING not null,
    DESCRIPTION  CHARACTER VARYING not null,
    RELEASE_DATE TIMESTAMP         not null,
    DURATION     INTEGER           not null,
    MPA_ID       INTEGER,
    constraint FILMS_PK
        PRIMARY KEY  (ID),
    constraint FILMS_RATING_MPA_ID_FK
        foreign key (MPA_ID) references MPA(MPA_ID)
);

create table IF NOT EXISTS GENRES
(
    ID   INTEGER           not null primary key,
    NAME CHARACTER VARYING not null
);

create table IF NOT EXISTS FILMS_GENRES
(
    GENRE_FILM_ID INTEGER auto_increment,
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint GENRE_FILM_PK
        PRIMARY KEY (GENRE_FILM_ID),
    constraint GENRE_FILM_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS(ID),
    constraint GENRE_GENRE_FILM_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRES(ID)
);

create table IF NOT EXISTS USERS

(
    ID INTEGER  auto_increment,
    USERNAME CHARACTER VARYING not null,
    LOGIN    CHARACTER VARYING not null,
    BIRTHDAY TIMESTAMP         not null,
    EMAIL    CHARACTER VARYING not null,
    constraint USER_PK
        PRIMARY KEY  (ID)
);

create table IF NOT EXISTS FRIENDSHIPS

(
    FRIENDSHIP_ID INTEGER auto_increment,
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint USER_FRIEND_PK
        PRIMARY KEY (FRIENDSHIP_ID),
    constraint USER_FRIEND_USERS_FRIENDSHIP_ID_FK
        foreign key (USER_ID) references USERS(ID),
    constraint USER_FRIEND_FRIEND_ID_FRIENDSHIP_ID_FK
        foreign key (FRIEND_ID) references USERS(ID)
);

create table IF NOT EXISTS LIKES

(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint LIKES_PK
        PRIMARY KEY (USER_ID, FILM_ID),
    constraint LIKES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS(ID),
    constraint LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS(ID)
);

create unique index if not exists USER_EMAIL_UINDEX on USERS (email);
create unique index if not exists USER_LOGIN_UINDEX on USERS (login);
create unique index if not exists FILMS_GENRES_UINDEX on FILMS_GENRES (FILM_ID, GENRE_ID);
create unique index if not exists USER_FRIEND_UINDEX on FRIENDSHIPS (user_id, friend_id);
