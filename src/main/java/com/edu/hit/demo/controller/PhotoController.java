package com.edu.hit.demo.controller;

//import com.edu.hit.demo.imgservice.ImageUtil;
//import com.edu.hit.demo.model.Image;
//import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.model.Post2;
import com.edu.hit.demo.model.Users;
//import com.edu.hit.demo.repository.ImageRepository;
import com.edu.hit.demo.repository.Post2Repository;
//import com.edu.hit.demo.repository.PostRepository;
import com.edu.hit.demo.service.FileStorageService;
import com.edu.hit.demo.service.Post2Service;


import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


@SessionAttributes("homeUser")
@Controller
public class PhotoController {

    @Autowired
    private Post2Service postService;

    @Autowired
    private Post2Repository postRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

//    @GetMapping("/upload")
//    public String showUploadForm(@RequestParam("email") String hEmail, Model model) {
//        Users findUser = userService.getUserByEmail(hEmail);
//        System.out.println(findUser.getEmail());
//        System.out.println(findUser.getUsername());
//        model.addAttribute("homeUser", findUser);
//        return "upload";
//    }
    @GetMapping("/upload")
    public String showUploadForm(@ModelAttribute("homeUser") Users hUser, Model model) {
        //Users findUser = userService.getUserByEmail(hEmail);
        //System.out.println(findUser.getEmail());
        //System.out.println(findUser.getUsername());
        model.addAttribute("homeUser", hUser);
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
        Post2 post = new Post2();
        post.setLikes(0);
        post.setUsername(findUser.getUsername());
        post.setEmail(findUser.getEmail());
        postRepository.save(post);
        model.addAttribute("message", "Successfully uploaded images!");
        model.addAttribute("files", fileNames);
        model.addAttribute("homeUser",findUser);
        List<Post2> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "redirect:/feed";
    }
//    @Transactional
//    @PostMapping("/upload")
//    public String handleFileUpload(@RequestParam("email") String hEmail,@RequestParam("caption") String caption,
//                                   @RequestParam("images") MultipartFile[] images,Model model) throws IOException {
//        Users findUser = userService.getUserByEmail(hEmail);
//        System.out.println(findUser.getEmail());
//        System.out.println(findUser.getUsername());
//        model.addAttribute("homeUser", findUser);
//        try {
//            //List<byte[]> imageDatas = new ArrayList<>();
//            List<Image> imageDatas = new ArrayList<>();
//            ImageUtil  imageUtil = new ImageUtil();
//            Post post = new Post();
//            post.setCaption(caption);
//
//            for (MultipartFile image : images) {
//                String originalFilename = image.getOriginalFilename();
//                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//                //允许上传的图片格式集合
//                String[] suffixes = new String[]{".bmp", ".jpeg", ".jpg", ".png"};
//                boolean bool = false;
//                //判断格式是否在suffixes中
//                for(String string : suffixes){
//                    if (string.equals(suffix)){
//                        bool = true;
//                        break;
//                    }
//                }
//                if(!bool) {
//                    return "upload";
//                }
//                byte[] imageData = image.getBytes();
//                //上传七牛
//                String imageName = imageUtil.uploadImage(imageData, findUser.getEmail());
//
//                com.edu.hit.demo.model.Image image1 = new com.edu.hit.demo.model.Image();
//                image1.setName(imageName);
//                image1.setPost(post); // 设置双向关联
//                imageDatas.add(image1);
//
//
//            }
//
//            post.setLikes(0);
//            post.setImageData(imageDatas);
//            post.setUsername(findUser.getUsername());
//            post.setEmail(findUser.getEmail());
//            postRepository.save(post);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle exception
//        }
//
//        List<Post> posts = postService.getAllPosts();
//        model.addAttribute("posts", posts);
//
//        return "redirect:/feed";
//
//
//    }


//    @GetMapping("/feed")
//    public String showFeed(Model model,@ModelAttribute("homeUser") Users hUser) {
//        List<Post> posts = postService.findAllOrderByLikesDesc();
//        model.addAttribute("posts", posts); // 确保正确添加数据到模型中
//        model.addAttribute("homeUser",hUser);
//        return "feed";
//    }
    @GetMapping("/feed")
    public String showProfile(@ModelAttribute("homeUser") Users hUser, Model model) {
        List<Post2> posts = postService.findAllOrderByLikesDesc();
        model.addAttribute("posts", posts); // 确保正确添加数据到模型中
        model.addAttribute("homeUser",hUser);
        return "feed";
    }
    @GetMapping("/postDetail/{postId}")
    public String viewPostDetail(@ModelAttribute("homeUser") Users hUser,
                                 @PathVariable("postId") Long postId,
                                 Model model){
        //Post post = postService.getPostById(postId);
        Post2 post = postService.getPostById(postId);
//        ImageUtil  imageUtil = new ImageUtil();
//        List<String> imageUtils = new ArrayList<>();
//        for (Image image: post.getImageData()){
//            String url = imageUtil.getFilePath(image.getName());
//            imageUtils.add(url);
//        }
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
