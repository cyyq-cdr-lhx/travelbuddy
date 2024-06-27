package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Comment;
import com.edu.hit.demo.model.Post2;
import com.edu.hit.demo.repository.CommentRepository;
import com.edu.hit.demo.repository.Post2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Post2Service {

    @Autowired
    private Post2Repository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Post2> getAllPosts() {
        return postRepository.findAll();
    }

    public int likePost(Long id) {
        Post2 post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return post.getLikes();
    }

    public void commentPost(Long id, String text) {
        Post2 post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment(text);
        comment.setPost(post);
        post.getComments().add(comment);
        postRepository.save(post);
    }

    public void savePost(Post2 post) {
        postRepository.save(post);
    }

    public List<Post2> findAllOrderByLikesDesc() {
        // 使用方法命名规则
        return postRepository.findAllByOrderByLikesDesc();

        // 或者使用 @Query 注解方式
        // return postRepository.findAllOrderByLikesDesc();
    }

    public Post2 getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    public List<Comment> getCommentsByPost(Post2 post){
        return commentRepository.findByPost(post);
    }

    public List<Post2> findPostsByEmail(String email){
        return postRepository.findByEmail(email);
    }

    public List<Comment> findCommentsByEmail(String email){
        return commentRepository.findByEmail(email);
    }



}
