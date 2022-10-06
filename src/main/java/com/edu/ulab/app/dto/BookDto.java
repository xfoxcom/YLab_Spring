package com.edu.ulab.app.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private Long user_id;
    private String title;
    private String author;
    private long pageCount;
}
