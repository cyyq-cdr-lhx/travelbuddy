package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public List<Interests> getInterestByEmail(String email){return interestRepository.findByEmail(email);}
    public void saveInterest(Interests userInterest){interestRepository.save(userInterest);}
    public void updateInterest(Interests userInterest){interestRepository.save(userInterest);}

    public Interests getInterestById(Integer id){
        return  interestRepository.findById(id);
    }
}