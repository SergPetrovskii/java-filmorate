package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storages.MpaStorage;

import java.util.List;

@Service
public class MpaService {

    private MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> getMpaList() {
        return mpaStorage.getMpaList();
    }

    public Mpa getMpaById(Integer id) {
        return mpaStorage.getMpaById(id);
    }
}
