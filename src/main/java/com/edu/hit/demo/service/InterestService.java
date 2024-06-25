package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.transaction.annotation.Transactional;


@Service
public class InterestService {
    private InterestRepository interestRepository;

<<<<<<< HEAD
    public Interests getInterestByEmail(String email){return interestRepository.findByEmail(email);}
    public void saveInterest(Interests userInterest){interestRepository.save(userInterest);}
    public void updateInterest(Interests userInterest){interestRepository.save(userInterest);}
}
=======
    public List<Interests> getInterestByEmail(String email){return interestRepository.findByEmail(email);}
    public void saveInterest(Interests userInterest){interestRepository.save(userInterest);}
    public void updateInterest(Interests userInterest){interestRepository.save(userInterest);}
    @Transactional
    public void deleteInterestById(Integer id){interestRepository.deleteById(id);}

    public Interests getInterestById(Integer id){
        return  interestRepository.findById(id);
    }
}
>>>>>>> ac97c35dd00386906c969a05187f7401c06c09b4
