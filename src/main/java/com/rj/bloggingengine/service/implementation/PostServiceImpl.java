package com.rj.bloggingengine.service.implementation;

import com.rj.bloggingengine.dto.PostDto;
import com.rj.bloggingengine.entity.Post;
import com.rj.bloggingengine.exception.ResourceNotFoundException;
import com.rj.bloggingengine.repository.PostRepo;
import com.rj.bloggingengine.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {


    private final PostRepo postRepository;


    @Override
    public Post savePost(Post post) {
        Post savedPost = postRepository.saveAndFlush(post);
        log.info("Saving post with id: " + post.getId());
        return savedPost;
    }

    @Override
    public Post getPostById(int id) {
        Post getEntity = postRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("Post with id: " + id + " does not exist"));
        log.info("Get post with id: " + id);
        return getEntity;

    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        log.info("Get all posts: " + postList.size());
        return postList;
    }

    @Override
    public Post updatePost(int id, PostDto postDto) {
        Post updatedPost = getPostById(id);
        updatedPost.setArchived(postDto.isArchived());
        if (Objects.nonNull(postDto.getTitle()) && !postDto.getTitle().isEmpty()) {
            updatedPost.setTitle(postDto.getTitle());
        }
        if (Objects.nonNull(postDto.getContent()) && !postDto.getContent().isEmpty()) {
            updatedPost.setContent(postDto.getContent());
        }
        log.info("Update post by id: " + id);
        return postRepository.saveAndFlush(updatedPost);
    }

    @Override
    public Double getAverageRating() {
        return postRepository.averageRating();
    }


    @Override
    public void deletePostById(int id) {
        log.info("Delete post by id: " + id);
        postRepository.deleteById(id);
    }


}
