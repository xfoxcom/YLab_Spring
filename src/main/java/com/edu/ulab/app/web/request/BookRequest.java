package com.edu.ulab.app.web.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookRequest {
    @NotBlank(message = "Book must have title.")
    private String title;
    @NotNull
    private String author;
    @Min(value = 1, message = "Book must have at least 1 page.")
    private long pageCount;
}
