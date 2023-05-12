package com.rj.bloggingengine.service.implementation;

import com.rj.bloggingengine.dto.ReviewDto;
import com.rj.bloggingengine.entity.Post;
import com.rj.bloggingengine.entity.Review;
import com.rj.bloggingengine.exception.ForbiddenActionException;
import com.rj.bloggingengine.exception.ResourceAlreadyExistsException;
import com.rj.bloggingengine.exception.ResourceNotFoundException;
import com.rj.bloggingengine.repository.ReviewRepo;
import com.rj.bloggingengine.service.PostService;
import com.rj.bloggingengine.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepository;
    private final PostService postService;


    @Override
    public Review saveReview(int postId, Review review) {
        Post getPostEntity = postService.getPostById(postId);
        if (getPostEntity.isArchived()) {
            throw new ForbiddenActionException("Post by id: " + postId + " is archived, review cannot be saved!");
        }
        if (checkIfAuthorAlreadyExists(postId, review.getAuthor())) {
            throw new ResourceAlreadyExistsException("Review by author: " + review.getAuthor() + " has been already given!");
        }
        review.setPost(getPostEntity);
        Review savedReview = reviewRepository.saveAndFlush(review);
        getPostEntity.setAverageRating(postService.getAverageRating());
        postService.savePost(getPostEntity);
        log.info("Saving review with id: " + review.getId() + " -> for post by id: " + postId);
        return savedReview;

    }

    @Override
    public List<Review> getReviewsByPostId(int postId) {
        Post getPostEntity = postService.getPostById(postId);
        log.info("Get all reviews -> for post by id: " + postId);
        return getPostEntity.getReviews();
    }

    @Override
    public Review getReviewById(int postId, int id) {
        Post getPostEntity = postService.getPostById(postId);
        Review getReviewEntity = getPostEntity.getReviews().stream()
                .filter(el -> el.getId() == id).findFirst().orElseThrow(
                        () -> new ResourceNotFoundException("Review by id: " + id + " -> for post by id: " + id + "does not exist")
                );
        log.info("Get review with id: " + id + " -> for post by id: " + postId);
        return getReviewEntity;

    }

    @Override
    public void deleteReviewByPostId(int postId, int id) {
        Post getPostEntity = postService.getPostById(postId);
        Review getReview = getPostEntity.getReviews().stream()
                .filter(el -> el.getId() == id).findFirst().orElseThrow(
                        () -> new ResourceNotFoundException("Review by id: " + id + " -> for post by id: " + id + "does not exist")
                );
        log.info("Delete review with id: " + id + " -> for post by id: " + postId);
        reviewRepository.deleteById(id);
    }

    @Override
    public Review updateReviewByPostId(int postId, int reviewId, ReviewDto reviewDto) {
        Review getOldReviewEntity = getReviewById(postId, reviewId);

        if (Objects.nonNull(reviewDto.getContent()) && !reviewDto.getContent().isEmpty()) {
            getOldReviewEntity.setContent(reviewDto.getContent());
        }
        getOldReviewEntity.setRating(reviewDto.getRating());

        log.info("Updating review with id: " + reviewId + " -> for post by id: " + postId);
        return reviewRepository.saveAndFlush(getOldReviewEntity);
    }

    @Override
    public boolean checkIfAuthorAlreadyExists(int postId, String author) {
        return reviewRepository.existsByAuthorAndPost_Id(author, postId);
    }

}
