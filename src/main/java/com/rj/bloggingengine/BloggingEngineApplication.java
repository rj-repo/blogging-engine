package com.rj.bloggingengine;

import com.rj.bloggingengine.entity.Post;
import com.rj.bloggingengine.entity.Review;
import com.rj.bloggingengine.service.PostService;
import com.rj.bloggingengine.service.ReviewService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class BloggingEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggingEngineApplication.class, args);
    }


    @Bean
    CommandLineRunner run(PostService postService, ReviewService reviewService) {
        return args -> {
            //add two Posts
            Post post = Post.builder()
                    .id(1)
                    .title("Post about sth")
                    .content("content of Post")
                    .archived(false)
                    .author("junior")
                    .build();

            Post post1 = Post.builder()
                    .id(1)
                    .title("Very old post so archived")
                    .content("long content")
                    .archived(true)
                    .author("senior")
                    .build();

            //add two review to one post
            Review review1 = Review.builder()
                    .id(1)
                    .author("hater")
                    .rating(1)
                    .content("bad")
                    .build();

            Review review2 = Review.builder()
                    .id(2)
                    .author("fan")
                    .rating(5)
                    .content("best")
                    .build();

            postService.savePost(post);
            reviewService.saveReview(1,review1);
            reviewService.saveReview(1,review2);

        };
    }
}
