package com.abs.samih.fcm_ex01;

/**
 * Created by school on 08/06/2016.
 */
public class Person {
    private String email;
    private String password;
    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Person() {
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
