package ru.springcourse.ElibraryBootEdition.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.ElibraryBootEdition.model.Person;
import ru.springcourse.ElibraryBootEdition.services.BookService;
import ru.springcourse.ElibraryBootEdition.services.PeopleService;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.index());

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person person = peopleService.show(id);
        model.addAttribute("person", person);
        model.addAttribute("books", bookService.findPersonBook(person));
        return "people/show";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("person", new Person());

        return "people/new";
    }

    @PostMapping("/new")
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model){
        model.addAttribute("person", peopleService.show(id));

        return "people/edit";
    }

    @PostMapping("/{id}")
    public String editPerson(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);

        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);

        return "redirect:/people";
    }
}
