package com.edu.hit.demo.service;

import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private UserService userService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private PostService postService;

    public List<Users> findTeammates(Users targetUser) {
        List<Users> allUsers = userService.getAllUsers();

        // Remove the target user from the list of all users
        allUsers.removeIf(user -> user.getEmail().equals(targetUser.getEmail()));

        List<Interests> targetUserInterests = interestService.getInterestByEmail(targetUser.getEmail());
        List<Post> targetUserPosts = postService.findPostsByEmail(targetUser.getEmail());
        List<Comment> targetUserComments = postService.findCommentsByEmail(targetUser.getEmail());

        // Precompute target user vectors
        Map<String, Double> targetInterestVector = computeInterestVector(targetUserInterests);
        Map<String, Double> targetPostVector = computePostVector(targetUserPosts);
        Map<String, Double> targetCommentVector = computeCommentVector(targetUserComments);

        // Compute similarity scores
        Map<Users, Double> similarityScores = new HashMap<>();
        for (Users user : allUsers) {
            List<Interests> userInterests = interestService.getInterestByEmail(user.getEmail());
            List<Post> userPosts = postService.findPostsByEmail(user.getEmail());
            List<Comment> userComments = postService.findCommentsByEmail(user.getEmail());

            double interestScore = computeCosineSimilarity(targetInterestVector, computeInterestVector(userInterests));
            double postScore = computeCosineSimilarity(targetPostVector, computePostVector(userPosts));
            double commentScore = computeCosineSimilarity(targetCommentVector, computeCommentVector(userComments));

            double totalScore = 0.4 * interestScore + 0.4 * postScore + 0.2 * commentScore;
            similarityScores.put(user, totalScore);
        }

        // Sort users by similarity scores and get top 5
        return similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Users, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<String, Double> computeInterestVector(List<Interests> interests) {
        Map<String, Double> vector = new HashMap<>();
        for (Interests interest : interests) {
            vector.put("startcity:" + interest.getStartcity(), 1.0);
            vector.put("destination:" + interest.getDestination(), 1.0);
            // Add other interest properties to the vector
        }
        return vector;
    }

    private Map<String, Double> computePostVector(List<Post> posts) {
        Map<String, Double> vector = new HashMap<>();
        for (Post post : posts) {
            vector.put("post:" + post.getCaption(), 1.0);
            // Add other post properties to the vector
        }
        return vector;
    }

    private Map<String, Double> computeCommentVector(List<Comment> comments) {
        Map<String, Double> vector = new HashMap<>();
        for (Comment comment : comments) {
            vector.put("comment:" + comment.getText(), 1.0);
            // Add other comment properties to the vector
        }
        return vector;
    }

    private double computeCosineSimilarity(Map<String, Double> vectorA, Map<String, Double> vectorB) {
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(vectorA.keySet());
        allKeys.addAll(vectorB.keySet());

        double dotProduct = 0.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;

        for (String key : allKeys) {
            double valueA = vectorA.getOrDefault(key, 0.0);
            double valueB = vectorB.getOrDefault(key, 0.0);

            dotProduct += valueA * valueB;
            magnitudeA += valueA * valueA;
            magnitudeB += valueB * valueB;
        }

        return dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
    }
}