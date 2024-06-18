package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Comment;
import com.edu.hit.demo.model.Image;
import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.repository.CommentRepository;
import com.edu.hit.demo.repository.ImageRepository;
import com.edu.hit.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public int likePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return post.getLikes();
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

    public List<Post> findAllOrderByLikesDesc() {
        // 使用方法命名规则
        return postRepository.findAllByOrderByLikesDesc();

        // 或者使用 @Query 注解方式
        // return postRepository.findAllOrderByLikesDesc();
    }

    public List<Image> getImagesByPost(Post post) {
        return imageRepository.findByPost(post);
    }

    public List<Comment> getCommentsByPost(Post post){
        return commentRepository.findByPost(post);
    }



}
