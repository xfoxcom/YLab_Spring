package com.edu.ulab.app.entity;

import lombok.Data;

@Data
public class Book {
    private long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}
