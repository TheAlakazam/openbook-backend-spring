package com.project.openbook.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReadShelf> readBooks;

    @OneToMany(mappedBy = "user")
    private List<CurrentShelf> currentBooks;

    @OneToMany(mappedBy = "user")
    private List<ToReadShelf> toReadBooks;
}
