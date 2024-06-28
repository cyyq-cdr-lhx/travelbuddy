package com.edu.hit.demo.controller;


import com.edu.hit.demo.model.Comment;
import com.edu.hit.demo.model.Likes;
import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.PostRepository;
import com.edu.hit.demo.service.FileStorageService;
import com.edu.hit.demo.service.LikesService;
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
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@SessionAttributes({"homeUser","flag"})
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikesService likeService;
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
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    // Create a directory for the user's uploads if it doesn't exist
                    String userDir = uploadDir + File.separator + email;
                    File dir = new File(userDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    Path path = Paths.get(userDir+File.separator+caption);
                    Files.write(path, image.getBytes());
                    //fileNames.add(caption);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("message", "Failed to upload '" + caption + "'!");
                    return "upload";
                }
            }
        }
        Users findUser = userService.getUserByEmail(email);
        Post post = new Post();
        post.setCaption(caption);
        post.setUsername(findUser.getUsername());
        post.setEmail(findUser.getEmail());
        post.setLikes(0);
        postRepository.save(post);
        model.addAttribute("message", "Successfully uploaded images!");
        model.addAttribute("homeUser",findUser);
        model.addAttribute("flag","feed");
        //List<Post> posts = postService.getAllPosts();
        //model.addAttribute("posts", posts);
        return "redirect:/feed";
    }

    @GetMapping("/feed")

    public String showMyPost(@ModelAttribute("homeUser") Users hUser, Model model) {

        List<Post> posts = postService.findPostsByEmail(hUser.getEmail());

        model.addAttribute("posts", posts); // 确保正确添加数据到模型中
        model.addAttribute("homeUser",hUser);
        model.addAttribute("flag","feed");
        return "feed";


    }
    @GetMapping("/allPost")
    public String showAllPost(@ModelAttribute("homeUser") Users hUser, Model model) {
        List<Post> posts = postService.getAllPost();
        model.addAttribute("posts", posts);
        model.addAttribute("homeUser",hUser);
        model.addAttribute("flag","allpost");
        return "allPost";
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
            images.add("/uploads/" + hUser.getEmail() + "/" + post.getCaption());
        }

        model.addAttribute("images", images);
        model.addAttribute("homeUser",hUser);
        model.addAttribute("post",post);
        List<Comment> comments = postService.getCommentsByPost(post);
        model.addAttribute("comments",comments);
        return "postDetail";


    }

    //用户可以删除自己的post
    @GetMapping("/deletePost/{postId}")
    public String deletePost(@ModelAttribute("homeUser") Users hUser,
                             @PathVariable("postId") Long postId,
                             Model model){
        postService.deletePost(postId);
        List<Post> posts = postService.findPostsByEmail(hUser.getEmail());


        model.addAttribute("posts", posts); // 确保正确添加数据到模型中
        model.addAttribute("homeUser",hUser);
        return "feed";
    }


    @GetMapping("/like/{postId}")
    //@ResponseBody
    public String likePost(@PathVariable("postId") Long postId,
                           @ModelAttribute("homeUser") Users hUser,Model model)
    {
        Post findpost = postService.getPostById(postId);
        Likes findLike = likeService.findByPostidAndEmail(postId,hUser.getEmail());
        //该用户已经给该post点过赞则删除
        if(findLike!=null){
            likeService.deletelike(findLike.getId());
            postService.notLikePost(findpost);
        }else {
            //未查到记录，则新增点赞
            Likes like = new Likes();
            like.setPostId(postId);
            like.setEmail(hUser.getEmail());
            likeService.saveLike(like);
            postService.likePost(findpost);
        }
        //返回该post点赞总数
        Integer cnt = likeService.countLike(postId);
        findpost.setLikes(cnt);

        String flag = Objects.requireNonNull(model.getAttribute("flag")).toString();
        if(flag.equals("allpost")){
            model.addAttribute("flag","allpost");
            return "redirect:/allPost" ;
        }
        model.addAttribute("flag","feed");
        return "redirect:/feed" ;

    }

    @PostMapping("/comment/{postid}")
    @ResponseBody
    public Map<String, Object> commentPost(@PathVariable Long postid, @RequestBody Map<String, String> payload,
                                           Model model) {
        //给该post增加评论
        postService.commentPost(postid, payload.get("text"));


        return Collections.singletonMap("status", "success");
    }




}
