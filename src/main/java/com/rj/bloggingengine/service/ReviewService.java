package com.rj.bloggingengine.service;

import com.rj.bloggingengine.dto.ReviewDto;
import com.rj.bloggingengine.entity.Review;

import java.util.List;

public interface ReviewService {

    Review saveReview(int postId, Review review);

    List<Review> getReviewsByPostId(int postId);

    Review getReviewById(int postId, int id);

    void deleteReviewByPostId(int postId, int id);

    Review updateReviewByPostId(int postId, int reviewId, ReviewDto reviewDto);

    boolean checkIfAuthorAlreadyExists(int postId, String author);
}
