merge into MPA
    using values (1, 'G') as incoming(ID,NAME)
    on MPA.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into MPA
    using values (2, 'PG') as incoming(ID,NAME)
    on MPA.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into MPA
    using values (3, 'PG-13') as incoming(ID,NAME)
    on MPA.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into MPA
    using values (4, 'R') as incoming(ID,NAME)
    on MPA.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into MPA
    using values (5, 'NC-17') as incoming(ID,NAME)
    on MPA.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into GENRES
    using values (1, 'Комедия') as incoming(ID,NAME)
    on GENRES.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into GENRES
    using values (2, 'Драма') as incoming(ID,NAME)
    on GENRES.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into GENRES
    using values (3, 'Мультфильм') as incoming(ID,NAME)
    on GENRES.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into GENRES
    using values (4, 'Триллер') as incoming(ID,NAME)
    on GENRES.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into GENRES
    using values (5, 'Документальный') as incoming(ID,NAME)
    on GENRES.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;

merge into GENRES
    using values (6, 'Боевик') as incoming(ID,NAME)
    on GENRES.ID = incoming.ID
    when not matched then
        insert (ID, NAME) values (incoming.ID, incoming.NAME)
    when matched then
        update set NAME = incoming.NAME;