package com.rj.bloggingengine.mapper;

import com.rj.bloggingengine.dto.ReviewDto;
import com.rj.bloggingengine.entity.Review;
import org.mapstruct.Mapper;

@Mapper
public interface ReviewMapper {

    ReviewDto toReviewDto(Review review);

    Review toReview(ReviewDto reviewDto);
}
