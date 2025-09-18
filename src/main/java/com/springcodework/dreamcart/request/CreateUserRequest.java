package com.springcodework.dreamcart.request;

import lombok.Data;
import lombok.Getter;

@Data
public class CreateUserRequest {

    private String  firstName;
    private String lastName;
    private String email;
    private String password;
}
