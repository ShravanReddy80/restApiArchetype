package com.ref.api.model;

import javax.validation.constraints.NotNull;

//@Entity
//@Table(name = "customers")
public class Customer extends AbstractEntity {

    @NotNull(message = "first name can not be null.")
//    @Column(name = "first_name", nullable = false)
    private String firstname;

    @NotNull(message = "last name can not be null.")
//    @Column(name = "last_name", nullable = false)
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}