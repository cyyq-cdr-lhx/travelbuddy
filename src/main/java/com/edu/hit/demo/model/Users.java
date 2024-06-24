package com.edu.hit.demo.model;


import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

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

    //@OneToMany(mappedBy = "inviter",cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "inviter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Invitee> invitees;


    private String bio;
    private String password;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setInvitees(List<Invitee> invitees) {
        this.invitees = invitees;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public List<Invitee> getInvitees() {
        return invitees;
    }

    public String getBio() {
        return bio;
    }

    public String getPassword() {
        return password;
    }












}