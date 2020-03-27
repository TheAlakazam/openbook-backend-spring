package com.project.openbook.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity(name = "current_shelf")
public class CurrentShelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookid")
    private Book book;

    @Column
    private Date start_date;
}
