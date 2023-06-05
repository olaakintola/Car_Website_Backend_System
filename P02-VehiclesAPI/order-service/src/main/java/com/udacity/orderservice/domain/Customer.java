package com.udacity.orderservice.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Embeddable
public class Customer {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastName;

    private LocalDate birthDate;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
