package ru.springcourse.ElibraryBootEdition.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.ElibraryBootEdition.model.Book;
import ru.springcourse.ElibraryBootEdition.model.Person;


import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id_book=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES(?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear()
        );
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id_book=?",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                id
        );
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book where id_book=?", id);
    }

    public List<Book> getPersonBook(int id_person){
        return jdbcTemplate.query("SELECT * FROM book WHERE id_person=?", new Object[]{id_person}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Person getPerson(int id_book){
        Integer id_person = this.jdbcTemplate.queryForObject("select id_person from book where id_book=?", Integer.class, id_book);

        if(id_person != null) {
            return jdbcTemplate.query("SELECT * FROM person WHERE id_person=?", new Object[]{id_person}, new BeanPropertyRowMapper<>(Person.class))
                    .stream().findAny().orElse(null);
        }

        return null;
    }

    public void freeBook(int id_book){
        jdbcTemplate.update("UPDATE book set id_person=null WHERE id_book=?", id_book);
    }

    public void assignPerson(int id_book, int id_person){
        jdbcTemplate.update("UPDATE book SET id_person=? WHERE id_book=?", id_person, id_book);
    }
}
