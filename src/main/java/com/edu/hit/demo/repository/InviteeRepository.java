package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Invitee;
import com.edu.hit.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteeRepository extends JpaRepository<Invitee, Long> {
    public List<Invitee> findAllByInvitee(Users invitee);
    public List<Invitee> findAllByInviter(Users inviter);
    public Invitee findAllByInviterAndInvitee(Users inviter, Users invitee);

}
