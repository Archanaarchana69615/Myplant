package com.example.library.dto;

import jakarta.persistence.Entity;
import lombok.Data;


@Data
public class PasswordDto {
    private long id;

    private String password;

    private String conformpassword;
}
