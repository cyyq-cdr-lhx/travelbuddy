package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Likes;
import com.edu.hit.demo.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService {
    @Autowired
    private LikesRepository likeRepository;

    public Likes findByPostidAndEmail(Long postid, String email){return likeRepository.findByPostIdAndEmail(postid, email);}
    public Integer countLike(Long postid){return likeRepository.countByPostId(postid);}
    public void deletelike(Long id){likeRepository.deleteById(id);}
    public  void saveLike(Likes like){likeRepository.save(like);}

}

