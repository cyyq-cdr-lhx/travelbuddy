package com.edu.hit.demo.model;


import jakarta.persistence.*;

@Entity
public class Invitee {


    public Long getId() {
        return id;
    }

    public Users getInviter() {
        return inviter;
    }

    public Users getInvitee() {
        return invitee;
    }






    public void setId(Long id) {
        this.id = id;
    }

    public void setInviter(Users inviter) {
        this.inviter = inviter;
    }

    public void setInvitee(Users invitee) {
        this.invitee = invitee;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users inviter;
    @OneToOne
    private Users invitee;

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    private Integer response;






}
