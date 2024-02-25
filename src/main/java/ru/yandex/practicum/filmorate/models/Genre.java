package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    private Integer id;
    private String name;
}
