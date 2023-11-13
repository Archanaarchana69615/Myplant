package com.example.library.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cutomer_id")

    private Long id;
    @NotEmpty(message = "cannot be empty")
    @NotBlank(message = "field cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "name can only contain letters and spaces")
    private String firstname;
    @NotEmpty(message = "cannot be blank")
    @NotBlank(message = "cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "only contain letters and spsces")
    private String lastname;

    @NotEmpty(message = "cannot be blank")
   @Pattern(regexp ="^\\+?[0-9\\-]*$",message = "only contain numbers")
   @Size(min = 10,max = 10,message = "only 10 numbers")
    private String mobilenumber;
    @NotEmpty
    @Email
    @NotEmpty(message = "cannot be blank")
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String conformpassword;

    private boolean is_activated;

//    @Column
//    private long otp;

//   private boolean deleted;

}
