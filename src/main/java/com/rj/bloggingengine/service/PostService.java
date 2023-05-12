package com.rj.bloggingengine.service;

import com.rj.bloggingengine.dto.PostDto;
import com.rj.bloggingengine.entity.Post;

import java.util.List;

public interface PostService {

    Post savePost(Post post);

    Post getPostById(int id);

    void deletePostById(int id);

    List<Post> getAllPosts();

    Post updatePost(int id, PostDto post);

    Double getAverageRating();


}
