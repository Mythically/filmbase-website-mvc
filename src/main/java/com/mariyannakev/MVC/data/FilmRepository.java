package com.mariyannakev.MVC.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    List<Film> findFilmByTitleContainingIgnoreCase(String title);
    Film findFilmById(Integer id);
}
