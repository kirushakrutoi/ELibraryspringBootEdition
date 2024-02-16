package ru.springcourse.ElibraryBootEdition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springcourse.ElibraryBootEdition.model.Book;
import ru.springcourse.ElibraryBootEdition.model.Person;
import ru.springcourse.ElibraryBootEdition.repositories.BooksRepository;


import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> index(Integer page, Integer pagePerBooks, Boolean sort){

        if(page != null && pagePerBooks != null && sort) {
            return booksRepository.findAll(PageRequest.of(page, pagePerBooks, Sort.by("year"))).getContent();

        } else if (page != null && pagePerBooks != null) {
            return booksRepository.findAll(PageRequest.of(page, pagePerBooks)).getContent();

        } else if (sort) {
            return booksRepository.findAll(Sort.by("year"));
        }
        return booksRepository.findAll();
    }

    public Book show(int id){
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book){
        book.setId_book(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public List<Book> findPersonBook(Person person){
        return booksRepository.findByPerson(person);
    }

    public Person getPerson(int id_book){
        return booksRepository.findById(id_book).map(Book::getPerson).orElse(null);
    }

    @Transactional
    public void freeBook(int id_book){
        Book book = booksRepository.findById(id_book).stream().findFirst().orElse(null);
        if(book != null){
            book.setPerson(null);
            book.setTakenAt(null);
        }
    }

    @Transactional
    public void assignPerson(int id_book, Person person){
        Book book = booksRepository.findById(id_book).stream().findFirst().orElse(null);
        if(book != null){
            book.setPerson(person);
            book.setTakenAt(new Date());
        }
    }

    public void test() {
        System.out.println("Testing here with debug. Inside Hibernate Transaction");
    }

    public List<Book> findByTitleStartingWith(String startingWith){
        return booksRepository.findByTitleStartingWith(startingWith);
    }
}
