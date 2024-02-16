package ru.springcourse.ElibraryBootEdition.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id_book")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_book;
    @NotEmpty(message = "Название не может быть путсым")
    @Size(max = 100)
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Введите автора")
    @Size(max=100)
    @Column(name = "author")
    private String author;
    @Max(value = 2024, message = "Год не может быть больше 2024")
    @Column(name = "year")
    private int year;

    @Column(name = "taken_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date takenAt;

    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id_person")
    private Person person;

    @Transient
    private boolean overdue;

    public Book(){

    }

    public Book(int id_book, String title, String author, int year) {
        this.id_book = id_book;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId_book() {
        return id_book;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isOverdue() {
        if(takenAt == null)
            return false;
        return new Date().getTime() - takenAt.getTime() > 8600;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int yearOfWriting) {
        this.year = yearOfWriting;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }
}
