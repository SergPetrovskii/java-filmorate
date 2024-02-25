package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.LikeStorage;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmValidationTest {

	private final FilmStorage filmStorage;

	private final LikeStorage likeStorage;

	private Film newFilm;

	@BeforeEach
	public void setUp() {
		newFilm = filmStorage.createFilms(Film.builder()
				.name("nisi eiusmod")
				.description("adipisicing")
				.duration(100)
				.releaseDate(LocalDate.of(1999, 01, 12))
				.mpa(Mpa.builder().id(1).build())
				.build());
	}

	@Test
	public void createFilm() {
		Film film = filmStorage.createFilms(Film
				.builder()
				.name("ndbadbjwdmod")
				.description("adiewqhebhwb eking")
				.duration(100)
				.releaseDate(LocalDate.of(1999, 01, 12))
				.mpa(Mpa.builder().id(1).build())
				.build());
		Assertions.assertNotNull(film);
	}

	@Test
	public void getFilm() {
		Film filmOptional = filmStorage.getFilmForId(1);
		Assertions.assertNotNull(filmOptional);
	}

	@Test
	public void getAllFilm() {
		List<Film> filmList = filmStorage.getAllFilms();
		Assertions.assertNotNull(filmList);
	}

	@Test
	public void updateFilm() {
		Film updateFilm = filmStorage.updateFilm(Film.builder()
				.id(1)
				.name("new nisi eiusmod")
				.description("new adipisicing")
				.duration(100)
				.releaseDate(LocalDate.of(1999, 01, 12))
				.mpa(Mpa.builder().id(1).build())
				.build());
		Assertions.assertNotEquals(updateFilm, newFilm);
	}

	@Test
	public void getPopularFilm() {
		List<Film> popularFilms = likeStorage.getPopularFilms(1);
		Assertions.assertNotNull(popularFilms);
	}
}
