package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mpa {
    private int id;
    private String name;
}
