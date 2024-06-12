package com.edu.hit.demo.model;



import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;

public class SignUser {

    private String username;

    private String email;

    private String password1;

    //确认密码
    private String password2;


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword1(String password1){
        this.password1 = password1;
    }

    public String getPassword1(){
        return password1;
    }

    public void setPassword2(String password2){
        this.password2 = password2;
    }

    public String getPassword2(){
        return password2;
    }

}
