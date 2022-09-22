package com.edu.ulab.app.entity;

import lombok.Data;

import java.util.List;


@Data
public class User {
    private long id;
    private String fullName;
    private String title;
    private int age;
    private List<Long> booksIds;
}
