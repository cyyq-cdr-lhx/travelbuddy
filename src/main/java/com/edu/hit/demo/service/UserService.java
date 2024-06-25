package com.edu.hit.demo.service;


import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public Users getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    public List<Users> findInvitersByInvitee(Users invitee){
        return userRepository.findUsersByInvitee(invitee);
    }
}

