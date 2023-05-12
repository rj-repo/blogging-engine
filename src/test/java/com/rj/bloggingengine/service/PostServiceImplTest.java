package com.rj.bloggingengine.service;

import com.rj.bloggingengine.dto.PostDto;
import com.rj.bloggingengine.entity.Post;
import com.rj.bloggingengine.entity.Review;
import com.rj.bloggingengine.exception.ResourceNotFoundException;
import com.rj.bloggingengine.mapper.PostMapper;
import com.rj.bloggingengine.repository.PostRepo;
import com.rj.bloggingengine.service.implementation.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepo postRepository;

    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    private List<Post> initPosts() {
        Post post1 = Post.builder()
                .id(34)
                .title("postDtoTitle1")
                .content("contentPostDto421")
                .author("dasd3awd")
                .build();
        Post post2 = Post.builder()
                .id(353)
                .title("postDtoTitle321")
                .content("contentPostDto421")
                .author("dfwsvdrt")
                .build();
        Post post3 = Post.builder()
                .id(3)
                .title("postDtoTitle3123")
                .content("contentPostDto5231")
                .author("fefwe4fw")
                .build();

        Review review1 = Review.builder()
                .id(52)
                .rating(0)
                .author("dasd")
                .content("contentReviewDto312321")
                .build();

        Review review2 = Review.builder()
                .id(554)
                .rating(5)
                .author("d3dqad")
                .content("contentReviewDto43124")
                .build();

        Review review3 = Review.builder()
                .id(5)
                .rating(3)
                .author("d3wqad")
                .content("contentReviewDto43124")
                .build();

        post1.setReviews(List.of(review1, review2));
        post2.setReviews(List.of(review3));

        return List.of(post1, post2, post3);

    }

    @Test
    void savePost_withoutReviews_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(32)
                .title("postDtoTitle")
                .content("contentPostDto")
                .author("dsadafasdf")
                .build();

        //mock when
        when(postRepository.saveAndFlush(any(Post.class))).thenReturn(post);

        //when
        Post result = postService.savePost(post);

        //then
        assertThat(result.getId(), equalTo(post.getId()));
        assertThat(result.getReviews(), equalTo(null));
        assertThat(result.getTitle(), equalTo(post.getTitle()));
        assertThat(result.getAuthor(), equalTo(post.getAuthor()));
        assertThat(result.getContent(), equalTo(post.getContent()));

    }

    @Test
    void savePost_withReviews_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(32)
                .title("postDtoTitle")
                .content("contentPostDto")
                .author("Dsadas")
                .build();

        Review review = Review.builder()
                .id(56)
                .rating(4)
                .author("3qdf")
                .content("contentReviewDto")
                .build();
        post.setReviews(List.of(review));

        //mock when;
        when(postRepository.saveAndFlush(any(Post.class))).thenReturn(post);

        //when
        Post result = postService.savePost(post);

        //then
        assertThat(result.getId(), equalTo(post.getId()));
        assertThat(result.getTitle(), equalTo(post.getTitle()));
        assertThat(result.getContent(), equalTo(post.getContent()));
        assertThat(result.getAuthor(), equalTo(post.getAuthor()));

        assertThat(result.getReviews(), notNullValue());

        Review firstReview = result.getReviews().get(0);
        assertThat(firstReview.getRating(), equalTo(review.getRating()));
        assertThat(firstReview.getContent(), equalTo(review.getContent()));
        assertThat(firstReview.getId(), equalTo(review.getId()));
        assertThat(firstReview.getAuthor(), equalTo(review.getAuthor()));


    }

    @Test
    void getPostById_withReviews_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(32)
                .title("postDtoTitle")
                .content("contentPostDto")
                .author("dsadasdasdsadas")
                .build();

        Review review = Review.builder()
                .id(56)
                .rating(4)
                .author("d33f")
                .content("contentReviewDto")
                .build();
        post.setReviews(List.of(review));

        //mock when
        when(postRepository.findById(32)).thenReturn(Optional.of(post));


        //when
        Post result = postService.getPostById(32);

        //then
        assertThat(result.getId(), equalTo(post.getId()));
        assertThat(result.getTitle(), equalTo(post.getTitle()));
        assertThat(result.getContent(), equalTo(post.getContent()));
        assertThat(result.getAuthor(), equalTo(post.getAuthor()));

        assertThat(result.getReviews(), notNullValue());

        Review firstReview = result.getReviews().get(0);
        assertThat(firstReview.getRating(), equalTo(review.getRating()));
        assertThat(firstReview.getContent(), equalTo(review.getContent()));
        assertThat(firstReview.getId(), equalTo(review.getId()));
        assertThat(firstReview.getAuthor(), equalTo(review.getAuthor()));


    }

    @Test
    void getPostById_NotExistedPost_ExpectException() {
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            postService.getPostById(1);
        });

    }

    @Test
    void getAllPosts_getListOfPost_ExpectSuccess() {
        //given
        List<Post> postList = initPosts();

        //when mock

        when(postRepository.findAll()).thenReturn(postList);

        //when
        List<Post> resultList = postService.getAllPosts();

        //then
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(3));

    }

    @Test
    void getAllPosts_getEmptyList_ExpectSuccess() {
        //given
        List<Post> postList = new ArrayList<>();

        //when mock

        when(postRepository.findAll()).thenReturn(postList);

        //when
        List<Post> resultList = postService.getAllPosts();

        //then
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(0));

    }


    @Test
    void updatePost_withReview_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(32)
                .title("postDtoTitle")
                .content("contentPostDto")
                .archived(false)
                .author("qw33qwqw3")
                .build();

        assertThat(post.isArchived(), equalTo(false));

        Review review = Review.builder()
                .id(56)
                .rating(4)
                .author("d33f")
                .content("contentReviewDto")
                .build();

        post.setReviews(List.of(review));
        when(postRepository.findById(32)).thenReturn(Optional.of(post));

        PostDto editedPost = PostDto.builder()
                .id(32)
                .title("postDtoTitleUpdated")
                .author("qw33qwqw3")
                .content("contentPostDto")
                .archived(true)
                .build();

        //when mock
        when(postRepository.saveAndFlush(any(Post.class))).thenReturn(post);

        //when
        Post result = postService.updatePost(32, editedPost);

        assertThat(result.getId(), equalTo(editedPost.getId()));
        assertThat(result.getTitle(), equalTo(editedPost.getTitle()));
        assertThat(result.getContent(), equalTo(editedPost.getContent()));
        assertThat(result.getAuthor(), equalTo(editedPost.getAuthor()));
        assertThat(result.isArchived(), equalTo(true));

        assertThat(result.getReviews(), notNullValue());

        Review firstReview = result.getReviews().get(0);
        assertThat(firstReview.getRating(), equalTo(review.getRating()));
        assertThat(firstReview.getContent(), equalTo(review.getContent()));
        assertThat(firstReview.getId(), equalTo(review.getId()));
        assertThat(firstReview.getAuthor(), equalTo(review.getAuthor()));

    }

    @Test
    void updatePost_withoutReview_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(32)
                .title("postDtoTitle")
                .content("contentPostDto")
                .archived(false)
                .author("qw33qwqw3")
                .build();

        assertThat(post.isArchived(), equalTo(false));


        when(postRepository.findById(32)).thenReturn(Optional.of(post));

        PostDto editedPost = PostDto.builder()
                .id(32)
                .title("postDtoTitleUpdated")
                .content("contentPostDto")
                .archived(true)
                .build();

        //when mock
        when(postRepository.saveAndFlush(any(Post.class))).thenReturn(postMapper.toPost(editedPost));

        //when
        Post result = postService.updatePost(32, editedPost);

        assertThat(result.getId(), equalTo(editedPost.getId()));
        assertThat(result.getTitle(), equalTo(editedPost.getTitle()));
        assertThat(result.getContent(), equalTo(editedPost.getContent()));
        assertThat(result.getAuthor(), equalTo(editedPost.getAuthor()));
        assertThat(result.isArchived(), equalTo(true));

    }

    @Test
    void updatePost_oneFieldUpdated_ExpectSuccess() {
        //given
        Post post = Post.builder()
                .id(32)
                .title("postDtoTitle")
                .content("contentPostDto")
                .archived(false)
                .author("qw33qwqw3")
                .build();

        assertThat(post.isArchived(), equalTo(false));


        when(postRepository.findById(32)).thenReturn(Optional.of(post));

        PostDto editedPost = PostDto.builder()
                .id(32)
                .title("postDtoTitleUpdated")
                .build();

        //when mock
        when(postRepository.saveAndFlush(any(Post.class))).thenReturn(postMapper.toPost(editedPost));

        //when
        Post result = postService.updatePost(32, editedPost);

        assertThat(result.getId(), equalTo(editedPost.getId()));
        assertThat(result.getTitle(), equalTo(editedPost.getTitle()));
        assertThat(result.getContent(), equalTo(editedPost.getContent()));
        assertThat(result.getAuthor(), equalTo(editedPost.getAuthor()));
        assertThat(result.isArchived(), equalTo(false));

    }
}