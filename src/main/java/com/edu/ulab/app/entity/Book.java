package com.edu.ulab.app.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}
