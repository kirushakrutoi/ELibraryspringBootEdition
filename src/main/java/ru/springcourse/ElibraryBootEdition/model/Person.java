package ru.springcourse.ElibraryBootEdition.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_person;
    @NotEmpty(message = "ФИО не может быть путсым")
    @Column(name = "name")
    private String name;
    @Min(value = 1900, message = "Год не может быть меньше 1900")
    @Max(value = 2024, message = "Год не может быть больше 2024")
    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "person")
    private List<Book> books;

    public Person(){

    }

    public Person(String name, int year, int id_person) {
        this.name = name;
        this.year = year;
        this.id_person = id_person;
    }

    public int getId_person() {
        return id_person;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setId_person(int id) {
        this.id_person = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int yearOfBirth) {
        this.year = yearOfBirth;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
