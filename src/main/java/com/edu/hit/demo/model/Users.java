package com.edu.hit.demo.model;


import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;
@Getter
@Setter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String username;
    private String email;
    private String gender;


    private String birthdate;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    private String bio;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {  return gender;    }

    public void setGender(String gender) {this.gender = gender;    }

    public String getBirthdate() { return birthdate;    }

    public void setBirthdate(String birthdate) { this.birthdate = birthdate;    }


    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }






}