package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Invitee;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.InviteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService {

    @Autowired
    private InviteeRepository inviteeRepository;

    @Autowired
    private UserService userService;

    public void onlySaveInvitee(Invitee invitee){
        inviteeRepository.save(invitee);
    }

    public List<Invitee> findAllByInvitee(Users invitee){
        return inviteeRepository.findAllByInvitee(invitee);
    }

    public List<Invitee> findAllByInviter(Users inviter){
        return inviteeRepository.findAllByInviter(inviter);
    }

    public Invitee findAllByInviterAndInvitee(Users inviter,Users invitee){
        return inviteeRepository.findAllByInviterAndInvitee(inviter,invitee);
    }
}
