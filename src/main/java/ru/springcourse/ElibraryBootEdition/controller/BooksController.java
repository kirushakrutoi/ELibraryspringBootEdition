package ru.springcourse.ElibraryBootEdition.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.ElibraryBootEdition.model.Book;
import ru.springcourse.ElibraryBootEdition.model.Person;
import ru.springcourse.ElibraryBootEdition.services.BookService;
import ru.springcourse.ElibraryBootEdition.services.PeopleService;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BooksController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) boolean sort,
                        Model model){
        model.addAttribute("books", bookService.index(page, booksPerPage, sort));
        bookService.test();

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("personn") Person person, Model model){
        model.addAttribute("book", bookService.show(id));
        model.addAttribute("person", bookService.getPerson(id));
        model.addAttribute("people", peopleService.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("book", new Book());

        return "books/new";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.show(id));

        return "books/edit";
    }

    @PostMapping("/{id}")
    public String editPerson(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);

        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);

        return "redirect:/books";
    }

    @PostMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id){
        bookService.freeBook(id);

        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/assign")
    public String assignPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookService.assignPerson(id, person);

        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "start_of_title", required = false) String startOfTitle,
            Model model){

        System.out.println(startOfTitle);
        model.addAttribute("books", bookService.findByTitleStartingWith(startOfTitle));


        return "books/search";
    }

/*    @PostMapping("/search")
    public String searchBook(Model model,
                             @ModelAttribute("startOfTitle") Book book,
                             @ModelAttribute("books") List<Book> books){
        model.addAttribute("books", bookService.findByTitleStartingWith(book.getTitle()));
        return "redirect:books/search";
    }*/
}
