package com.edu.hit.demo.controller;

import com.edu.hit.demo.imgservice.ImageUtil;
import com.edu.hit.demo.model.Image;
import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.ImageRepository;
import com.edu.hit.demo.repository.PostRepository;
import com.edu.hit.demo.service.FileStorageService;
import com.edu.hit.demo.service.PostService;

import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class PhotoController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @GetMapping("/upload")
    public String showUploadForm(@RequestParam("email") String email, Model model) {
        Users findUser = userService.getUserByEmail(email);
        model.addAttribute("homeUser", findUser);
        return "upload";
    }
    @Transactional
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("email") String email,@RequestParam("caption") String caption,
                                   @RequestParam("images") MultipartFile[] images,Model model) throws IOException {
        Users findUser = userService.getUserByEmail(email);
        model.addAttribute("homeUser", findUser);
        try {
            //List<byte[]> imageDatas = new ArrayList<>();
            List<Image> imageDatas = new ArrayList<>();
            ImageUtil  imageUtil = new ImageUtil();
            Post post = new Post();
            post.setCaption(caption);

            for (MultipartFile image : images) {
                String originalFilename = image.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //允许上传的图片格式集合
                String[] suffixes = new String[]{".bmp", ".jpeg", ".jpg", ".png"};
                boolean bool = false;
                //判断格式是否在suffixes中
                for(String string : suffixes){
                    if (string.equals(suffix)){
                        bool = true;
                        break;
                    }
                }
                if(!bool) {
                    return "upload";
                }
                byte[] imageData = image.getBytes();
                //上传七牛
                String imageName = imageUtil.uploadImage(imageData, email);

                com.edu.hit.demo.model.Image image1 = new com.edu.hit.demo.model.Image();
                image1.setName(imageName);
                image1.setPost(post); // 设置双向关联
                imageDatas.add(image1);


            }

            post.setLikes(0);
            post.setImageData(imageDatas);
            post.setUsername(findUser.getUsername());
            post.setEmail(findUser.getEmail());
            postRepository.save(post);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }

        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

        return "redirect:/feed";


    }


    @GetMapping("/feed")
    public String showFeed(Model model) {
        List<Post> posts = postService.findAllOrderByLikesDesc();
        model.addAttribute("posts", posts); // 确保正确添加数据到模型中
        return "feed";
    }

    @PostMapping("/like/{id}")
    @ResponseBody
    public Map<String, Object> likePost(@PathVariable Long id) {
        int likes = postService.likePost(id);
        return Collections.singletonMap("likes", likes);
    }

    @PostMapping("/comment/{id}")
    @ResponseBody
    public Map<String, Object> commentPost(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        postService.commentPost(id, payload.get("text"));
        return Collections.singletonMap("status", "success");
    }
}
