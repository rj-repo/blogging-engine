package com.rj.bloggingengine.mapper;

import com.rj.bloggingengine.dto.ReviewDto;
import com.rj.bloggingengine.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
public class ReviewMapperTest {

    @InjectMocks
    private ReviewMapperImpl reviewMapper;

    @Test
    public void toReviewDto_withoutAttachingToPost_ExpectSuccess() {
        //given
        Review review = Review.builder()
                .id(1)
                .rating(2)
                .author("dasdasdasd")
                .content("contentOfPost")
                .build();

        //when
        ReviewDto result = reviewMapper.toReviewDto(review);

        //then
        assertThat(result.getId(), equalTo(review.getId()));
        assertThat(result.getContent(), equalTo(review.getContent()));
        assertThat(result.getRating(), equalTo(review.getRating()));
        assertThat(result.getAuthor(), equalTo(review.getAuthor()));
    }

    @Test
    public void toPost_withoutAttachingToPost_ExpectSuccess() {
        //given
        ReviewDto reviewDto = ReviewDto.builder()
                .id(1)
                .rating(3)
                .author("fw4vr")
                .content("contentOfPost")
                .build();

        //when
        Review result = reviewMapper.toReview(reviewDto);

        //then
        assertThat(result.getId(), equalTo(reviewDto.getId()));
        assertThat(result.getContent(), equalTo(reviewDto.getContent()));
        assertThat(result.getRating(), equalTo(reviewDto.getRating()));
        assertThat(result.getAuthor(), equalTo(reviewDto.getAuthor()));
    }


}
