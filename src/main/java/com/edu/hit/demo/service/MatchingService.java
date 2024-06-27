//package com.edu.hit.demo.service;
//
//import com.edu.hit.demo.model.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class MatchingService {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private InterestService interestService;
//
//    @Autowired
//    //private PostService postService;
//    private Post2Service postService;
//    public List<Users> findTeammates(Users targetUser) {
//        List<Users> allUsers = userService.getAllUsers();
//
//        // Remove the target user from the list of all users
//        allUsers.remove(targetUser);
//
//        List<Interests> getInterests = interestService.getInterestByEmail(targetUser.getEmail());
//        //List<Post> getPosts = postService.findPostsByEmail(targetUser.getEmail());
//        List<Post2> getPosts = postService.findPostsByEmail(targetUser.getEmail());
//        List<Comment> getComments = postService.findCommentsByEmail(targetUser.getEmail());
//
//
//
//        // Precompute target user vectors
//        Map<String, Double> targetInterestVector = computeInterestVector(getInterests);
//        Map<String, Double> targetPostVector = computePostVector(getPosts);
//        Map<String, Double> targetCommentVector = computeCommentVector(getComments);
//
//        // Compute similarity scores
//        Map<Users, Double> similarityScores = new HashMap<>();
//        for (Users user : allUsers) {
//            double interestScore = computeCosineSimilarity(targetInterestVector, computeInterestVector(getInterests));
//            double postScore = computeCosineSimilarity(targetPostVector, computePostVector(getPosts));
//            double commentScore = computeCosineSimilarity(targetCommentVector, computeCommentVector(getComments));
//
//            double totalScore = 0.4 * interestScore + 0.4 * postScore + 0.2 * commentScore;
//            similarityScores.put(user, totalScore);
//        }
//
//        // Sort users by similarity scores
//        return similarityScores.entrySet().stream()
//                .sorted(Map.Entry.<Users, Double>comparingByValue().reversed())
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }
//
//    private Map<String, Double> computeInterestVector(List<Interests> interests) {
//        Map<String, Double> vector = new HashMap<>();
//        for (Interests interest : interests) {
//            vector.put("startcity:" + interest.getStartcity(), 1.0);
//            vector.put("destination:" + interest.getDestination(), 1.0);
//            // Add other interest properties to the vector
//        }
//        return vector;
//    }
//
//    private Map<String, Double> computePostVector(List<Post> posts) {
//        Map<String, Double> vector = new HashMap<>();
//        for (Post post : posts) {
//            vector.put("post:" + post.getCaption(), 1.0);
//            // Add other post properties to the vector
//        }
//        return vector;
//    }
//
//    private Map<String, Double> computeCommentVector(List<Comment> comments) {
//        Map<String, Double> vector = new HashMap<>();
//        for (Comment comment : comments) {
//            vector.put("comment:" + comment.getText(), 1.0);
//            // Add other comment properties to the vector
//        }
//        return vector;
//    }
//
//    private double computeCosineSimilarity(Map<String, Double> vectorA, Map<String, Double> vectorB) {
//        Set<String> allKeys = new HashSet<>();
//        allKeys.addAll(vectorA.keySet());
//        allKeys.addAll(vectorB.keySet());
//
//        double dotProduct = 0.0;
//        double magnitudeA = 0.0;
//        double magnitudeB = 0.0;
//
//        for (String key : allKeys) {
//            double valueA = vectorA.getOrDefault(key, 0.0);
//            double valueB = vectorB.getOrDefault(key, 0.0);
//
//            dotProduct += valueA * valueB;
//            magnitudeA += valueA * valueA;
//            magnitudeB += valueB * valueB;
//        }
//
//        return dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
//    }
//}
