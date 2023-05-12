package com.rj.bloggingengine.controller;

import com.rj.bloggingengine.dto.ReviewDto;
import com.rj.bloggingengine.mapper.ReviewMapper;
import com.rj.bloggingengine.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);

    @Operation(
            summary = "Save new Post",
            description = "Save new review to EXISTED POST. In other way there will be thrown exception. You do not need to and should not specify id. It will be done by backend automatically",
            tags = {"review"})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/{idPost}/reviews")
    public ResponseEntity<ReviewDto> saveReview(@PathVariable(name = "idPost") int idPost,
                                                @RequestBody ReviewDto reviewDto) {
        reviewService.saveReview(idPost, reviewMapper.toReview(reviewDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get review for given post id and review id",
            tags = {"review"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{idPost}/reviews/{idReview}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable(name = "idPost") int idPost,
                                               @PathVariable(name = "idReview") int idReview) {
        ReviewDto getReview = reviewMapper.toReviewDto(reviewService.getReviewById(idPost, idReview));
        return ResponseEntity.ok(getReview);
    }

    @Operation(
            summary = "Get all reviews for given post id ",
            tags = {"review"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{idPost}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByPost(@PathVariable(name = "idPost") int idPost) {
        List<ReviewDto> getReviewList = reviewService.getReviewsByPostId(idPost).stream().map(reviewMapper::toReviewDto).toList();
        return ResponseEntity.ok(getReviewList);
    }

    @Operation(
            summary = "Update exact review for given post id ",
            tags = {"review"})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/{idPost}/reviews/{idReview}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(name = "idPost") int idPost,
                                                  @PathVariable(name = "idReview") int idReview,
                                                  @RequestBody ReviewDto reviewDto) {
        reviewService.updateReviewByPostId(idPost, idReview, reviewDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Delete exact review for given post id ",
            tags = {"review"})
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/{idPost}/reviews/{idReview}")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable(name = "idPost") int idPost,
                                                  @PathVariable(name = "idReview") int idReview) {
        reviewService.deleteReviewByPostId(idPost, idReview);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
