package com.mariyannakev.MVC.services;

import com.mariyannakev.MVC.data.FilmRepository;
import com.mariyannakev.MVC.data.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll(Sort.sort(Film.class).by(Film::getId));
    }

    public Film getFilmById(Integer id) {
        return filmRepository.findFilmById(id);
    }

    public List<Film> findFilmByTitle(String title) {
        return filmRepository.findFilmByTitleContainingIgnoreCase(title);
    }

    public Film add(Film film) {
        return filmRepository.save(film);
    }

    public Film update(Integer id, Film film) {
        film.setId(id);
        return filmRepository.save(film);
    }

    public void delete(Integer id) {
        filmRepository.deleteById(id);
    }
}

