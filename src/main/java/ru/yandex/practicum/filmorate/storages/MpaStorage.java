package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.Mpa;

import java.util.List;

public interface MpaStorage {

    List<Mpa> getMpaList();

    Mpa getMpaById(Integer id);
}
