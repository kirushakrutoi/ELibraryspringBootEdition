package ru.springcourse.ElibraryBootEdition.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springcourse.ElibraryBootEdition.model.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
