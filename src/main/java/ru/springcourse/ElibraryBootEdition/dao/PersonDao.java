package ru.springcourse.ElibraryBootEdition.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.ElibraryBootEdition.model.Person;


import java.util.List;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id_person=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person(name, year) VALUES(?, ?)",
                person.getName(),
                person.getYear()
        );
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE PERSON SET name=?, year=? WHERE id_person=?",
                person.getName(),
                person.getYear(),
                id
        );
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person where id_person=?", id);
    }
}
