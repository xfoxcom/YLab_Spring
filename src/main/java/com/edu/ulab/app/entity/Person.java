package com.edu.ulab.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String title;
    private int age;
    @ElementCollection
    private List<Long> booksIds;
}
