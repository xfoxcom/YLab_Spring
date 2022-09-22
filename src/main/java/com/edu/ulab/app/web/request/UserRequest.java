package com.edu.ulab.app.web.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {
    @NotBlank(message = "Name cant be empty or blank.")
    private String fullName;
    @NotNull
    private String title;
    @Min(value = 14, message = "Age must be greater than or equal to 14")
    private int age;
}
