package com.mariyannakev.MVC.controllers;

import com.mariyannakev.MVC.Errors.FilmNotFoundException;
import com.mariyannakev.MVC.services.FilmService;
import com.mariyannakev.MVC.data.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping
    public String index(Model model) {
        List<Film> films = filmService.getAllFilms();
        model.addAttribute("films", films);
        return "films";
    }

    @GetMapping("/add")
    public String showSaveForm(Model model) {
        model.addAttribute("film", new Film());
        return "film-form";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Film film= filmService.getFilmById(id);
        model.addAttribute("film", film);
        return "film-form";
    }
    @GetMapping("/search")
    public String search(@RequestParam("query") String title, Model model) {
        List<Film> films = filmService.findFilmByTitle(title);
        model.addAttribute("films", films);
        return "films";
    }


    @PostMapping
    public String save(@ModelAttribute Film film , RedirectAttributes redirectAttributes) {
        film.setTitle(HtmlUtils.htmlEscape(film.getTitle()));
        film.setDirector(HtmlUtils.htmlEscape(film.getDirector()));
        film.setStars(HtmlUtils.htmlEscape(film.getStars()));
        film.setReview(HtmlUtils.htmlEscape(film.getReview()));

        filmService.add(film);

        //return a little notification message
        redirectAttributes.addFlashAttribute("message", "Film with ID: " + film.getId()+ " saved successfully");

        return "redirect:/films";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Film film, RedirectAttributes redirectAttributes) {

        film.setTitle(HtmlUtils.htmlEscape(film.getTitle()));
        film.setDirector(HtmlUtils.htmlEscape(film.getDirector()));
        film.setStars(HtmlUtils.htmlEscape(film.getStars()));
        film.setReview(HtmlUtils.htmlEscape(film.getReview()));
        filmService.update(id, film);
        redirectAttributes.addFlashAttribute("message", "Film with ID: " + film.getId()+ " edited successfully");
        return "redirect:/films";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // use the FilmService to delete the film
        filmService.delete(id);
        redirectAttributes.addFlashAttribute("deleteSuccess", "Film with ID: " +id+ " deleted successfully");
        // redirect the user to the list of films
        return "redirect:/films";
    }
}