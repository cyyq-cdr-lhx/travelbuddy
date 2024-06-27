package com.edu.hit.demo.controller;


import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.PostRepository;
import com.edu.hit.demo.service.FileStorageService;
import com.edu.hit.demo.service.PostService;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Controller
@SessionAttributes({"homeUser"})
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private UserService userService;
    @GetMapping("/upload")
    public  String showUpLoadFormz(@ModelAttribute("homeUser") Users hUser,
                                   Model model
                                   ){
        model.addAttribute("homeUser",hUser);
        return "upload";
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public String uploadPhotos(@RequestParam("images") MultipartFile[] images,
                               @RequestParam("caption") String caption,
                               @RequestParam("email") String email,
                               Model model) {
        //每个用户的图片保存在一个单独的文件夹中
        //文件夹内图片以caption命名
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    // Create a directory for the user's uploads if it doesn't exist
                    String userDir = uploadDir + File.separator + email;
                    File dir = new File(userDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    // Save the uploaded file to the user's directory
                    //String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                    //Path path = Paths.get(userDir + File.separator + fileName);
                    Path path = Paths.get(userDir+File.separator+caption);
                    Files.write(path, image.getBytes());

                    //fileNames.add(fileName);
                    fileNames.add(caption);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("message", "Failed to upload '" + caption + "'!");
                    return "upload";
                }
            }
        }
        Users findUser = userService.getUserByEmail(email);
        Post post = new Post();
        post.setLikes(0);
        post.setUsername(findUser.getUsername());
        post.setEmail(findUser.getEmail());
        postRepository.save(post);
        model.addAttribute("message", "Successfully uploaded images!");
        model.addAttribute("files", fileNames);
        model.addAttribute("homeUser",findUser);
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "redirect:/feed";
    }

    @GetMapping("/feed")
    public String showProfile(@ModelAttribute("homeUser") Users hUser, Model model) {
        List<Post> posts = postService.findAllOrderByLikesDesc();
        model.addAttribute("posts", posts); // 确保正确添加数据到模型中
        model.addAttribute("homeUser",hUser);
        return "feed";
    }
    @GetMapping("/postDetail/{postId}")
    public String viewPostDetail(@ModelAttribute("homeUser") Users hUser,
                                 @PathVariable("postId") Long postId,
                                 Model model){
        Post post = postService.getPostById(postId);
        String userDir = uploadDir + File.separator + hUser.getEmail();
        File dir = new File(userDir);
        List<String> images = new ArrayList<>();

        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                images.add("/uploads/" + hUser.getEmail() + "/" + file.getName());
            }
        }

        model.addAttribute("images", images);
        model.addAttribute("homeUser",hUser);
        model.addAttribute("post",post);
        //model.addAttribute("images",imageUtils);
        return "postDetail";


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
