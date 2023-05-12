package com.rj.bloggingengine.mapper;

import com.rj.bloggingengine.dto.PostDto;
import com.rj.bloggingengine.dto.PostResponse;
import com.rj.bloggingengine.dto.ReviewDto;
import com.rj.bloggingengine.entity.Post;
import com.rj.bloggingengine.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
public class PostMapperTest {


    private PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Test
    public void toPostDto_withoutReview_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(1)
                .title("title1")
                .content("contentOfPost")
                .author("author")
                .build();

        //when
        PostDto result = postMapper.toPostDto(post);

        //then
        assertThat(result.getId(), equalTo(post.getId()));
        assertThat(result.getTitle(), equalTo(post.getTitle()));
        assertThat(result.getContent(), equalTo(post.getContent()));
        assertThat(result.getAuthor(), equalTo(post.getAuthor()));
    }

    @Test
    public void toPost_withoutReview_ExpectSuccess() {
        //given
        PostDto postDto = PostDto.builder()
                .id(1)
                .title("title1")
                .content("contentOfPost")
                .author("author")
                .build();

        //when
        Post result = postMapper.toPost(postDto);

        //then
        assertThat(result.getId(), equalTo(postDto.getId()));
        assertThat(result.getTitle(), equalTo(postDto.getTitle()));
        assertThat(result.getContent(), equalTo(postDto.getContent()));
        assertThat(result.getAuthor(), equalTo(postDto.getAuthor()));
    }

    @Test
    public void toPostDto_withReview_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(1)
                .title("title1")
                .content("contentOfPost")
                .author("author")
                .build();

        Review review = Review.builder()
                .id(1)
                .rating(3)
                .content("reviewContent")
                .post(post).build();

        post.setReviews(List.of(review));
        //when
        PostDto result = postMapper.toPostDto(post);


        //then
        assertThat(result.getId(), equalTo(post.getId()));
        assertThat(result.getTitle(), equalTo(post.getTitle()));
        assertThat(result.getContent(), equalTo(post.getContent()));
        assertThat(result.getAuthor(), equalTo(post.getAuthor()));


    }

    @Test
    public void toPost_withReview_ExpectSuccess() {
        //given
        PostDto postDto = PostDto.builder()
                .id(1)
                .title("title1")
                .content("contentOfPost")
                .author("author4343")
                .build();

        ReviewDto reviewDto = ReviewDto.builder()
                .id(1)
                .rating(3)
                .content("reviewContent")
                .build();

        //when
        Post result = postMapper.toPost(postDto);


        //then
        assertThat(result.getId(), equalTo(postDto.getId()));
        assertThat(result.getTitle(), equalTo(postDto.getTitle()));
        assertThat(result.getContent(), equalTo(postDto.getContent()));
        assertThat(result.getAuthor(), equalTo(postDto.getAuthor()));


    }


    @Test
    public void toPostResponse_withReview_ExpectSuccess() {
        //given
        Post postResponse = Post.builder()
                .id(1)
                .title("title1")
                .content("contentOfPost")
                .author("author4343")
                .build();

        ReviewDto reviewDto = ReviewDto.builder()
                .id(1)
                .rating(3)
                .content("reviewContent")
                .build();

        //when
        PostResponse result = postMapper.toPostResponse(postResponse);


        //then
        assertThat(result.getId(), equalTo(postResponse.getId()));
        assertThat(result.getTitle(), equalTo(postResponse.getTitle()));
        assertThat(result.getContent(), equalTo(postResponse.getContent()));
        assertThat(result.getAuthor(), equalTo(postResponse.getAuthor()));
        assertThat(result.getAverageRating(), equalTo(postResponse.getAverageRating()));

        assertThat(result.getReviews(), notNullValue());

        ReviewDto getReview = result.getReviews().get(0);

        assertThat(getReview.getAuthor(), equalTo(reviewDto.getAuthor()));
        assertThat(getReview.getRating(), equalTo(reviewDto.getRating()));
        assertThat(getReview.getId(), equalTo(reviewDto.getId()));
        assertThat(getReview.getContent(), equalTo(reviewDto.getContent()));


    }

    @Test
    public void toPostResponse_withoutReview_ExpectSuccess() {
        //given
        Post postResponse = Post.builder()
                .id(1)
                .title("title1")
                .content("contentOfPost")
                .author("author4343")
                .build();


        //when
        PostResponse result = postMapper.toPostResponse(postResponse);


        //then
        assertThat(result.getId(), equalTo(postResponse.getId()));
        assertThat(result.getTitle(), equalTo(postResponse.getTitle()));
        assertThat(result.getContent(), equalTo(postResponse.getContent()));
        assertThat(result.getAuthor(), equalTo(postResponse.getAuthor()));
        assertThat(result.getAverageRating(), equalTo(postResponse.getAverageRating()));

        assertThat(result.getReviews(), nullValue());


    }
}