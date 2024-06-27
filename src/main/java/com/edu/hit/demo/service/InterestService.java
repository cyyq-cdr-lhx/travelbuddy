package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public List<Interests> getInterestByEmail(String email){return interestRepository.findByEmail(email);}
    public void saveInterest(Interests userInterest){interestRepository.save(userInterest);}
    public void updateInterest(Interests userInterest){interestRepository.save(userInterest);}
    @Transactional
    public void deleteInterestById(Integer id){interestRepository.deleteById(id);}

    public Interests getInterestById(Integer id){
        return  interestRepository.findById(id);
    }
}

