package com.project.openbook.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookid;

    @Column
    private String title;

    @Column
    private String authors;

    @Column
    private float average_rating;

    @Column
    private String isbn;

    @Column
    private double isbn13;

    @Column
    private String language_code;

    @Column
    private int num_pages;

    @Column
    private double ratings_count;

    @Column
    private double text_reviews_count;

    @Column
    private String publication_date;

    @Column
    private String publisher;
}
