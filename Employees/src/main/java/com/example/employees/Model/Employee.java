package com.example.employees.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
public class Employee {

    @NotNull
    @Size(min = 2, message = "the ID should not less then 2 ")
    private String id;


    @NotNull
    @Size (min = 4 , message = "the name should between 4 ")
    private String name;

    @NotNull
    @Min(value = 25, message = "Age should be 25 or above")
    private int age;


    @NotNull
    @Pattern(regexp = "supervisor|coordinator", message = "Role must be supervisor or coordinator")
    private String role;

    @Builder.Default
    // we can use the assertFalse to make this value false but i prefer to use default
    private Boolean onLeave = false;

    @NotNull
    @PastOrPresent // this annotation is not coming with int data type so i prefer to use the date
    private Date employmentYear;

    @NotNull
    @PositiveOrZero
    private int annualLeave;

}
