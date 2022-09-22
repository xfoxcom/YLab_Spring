package com.edu.ulab.app.web.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserBookRequest {
    @Valid
    @NotNull
    private UserRequest userRequest;
    @Valid
    @NotNull
    private List<BookRequest> bookRequests;
}
