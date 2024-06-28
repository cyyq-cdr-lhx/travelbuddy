package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Comment;
//import com.edu.hit.demo.model.Image;
import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.repository.CommentRepository;
//import com.edu.hit.demo.repository.ImageRepository;
import com.edu.hit.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private CommentRepository commentRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }



    public void commentPost(Long id, String text) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment(text);
        comment.setPost(post);
        post.getComments().add(comment);
        postRepository.save(post);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }


    public List<Comment> getCommentsByPost(Post post){
        return commentRepository.findByPost(post);
    }

    public List<Post> findPostsByEmail(String email){
        return postRepository.findByEmail(email);
    }

    public List<Comment> findCommentsByEmail(String email){
        return commentRepository.findByEmail(email);
    }
    public List<Post> getAllPost(){return postRepository.findAllByOrderByLikesDesc();}

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public void likePost(Post post) {
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    public void notLikePost(Post post) {
        post.setLikes(post.getLikes() - 1);
        postRepository.save(post);
    }
}