package com.rj.bloggingengine.controller;

import com.rj.bloggingengine.dto.PostDto;
import com.rj.bloggingengine.dto.PostResponse;
import com.rj.bloggingengine.entity.Post;
import com.rj.bloggingengine.mapper.PostMapper;
import com.rj.bloggingengine.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Posts", description = "API for management Post")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Operation(
            summary = "Save new Post",
            description = "Save new post. You do not need to specify id. It will be done by backend automatically",
            tags = {"post"})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto) {
        Post getPost = postMapper.toPost(postDto);
        postService.savePost(getPost);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get a Post by id",
            tags = {"post"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable(name = "id") int id) {
        Post getPost = postService.getPostById(id);
        PostResponse getResponse = postMapper.toPostResponse(getPost);
        return ResponseEntity.ok(getResponse);
    }

    @Operation(
            summary = "Get all posts",
            tags = {"post"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> postList = postService.getAllPosts().stream().map(postMapper::toPostResponse).toList();
        return ResponseEntity.ok(postList);
    }

    @Operation(
            summary = "Update post by its id",
            tags = {"post"})
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable(name = "id") int id,
                                                   @RequestBody PostDto postDto) {
        Post getUpdatedPost = postService.updatePost(id, postDto);
        PostResponse getResponse = postMapper.toPostResponse(getUpdatedPost);
        return ResponseEntity.ok(getResponse);
    }

    @Operation(
            summary = "Delete post by its id",
            tags = {"post"})
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable(name = "id") int id) {
        postService.deletePostById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
