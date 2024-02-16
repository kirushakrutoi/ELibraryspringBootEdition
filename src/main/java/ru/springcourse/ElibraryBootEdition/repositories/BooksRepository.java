package ru.springcourse.ElibraryBootEdition.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springcourse.ElibraryBootEdition.model.Book;
import ru.springcourse.ElibraryBootEdition.model.Person;


import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByPerson(Person person);
    List<Book> findByTitleStartingWith(String startingWith);

}
