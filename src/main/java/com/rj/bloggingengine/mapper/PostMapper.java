package com.rj.bloggingengine.mapper;

import com.rj.bloggingengine.dto.PostDto;
import com.rj.bloggingengine.dto.PostResponse;
import com.rj.bloggingengine.entity.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {

    PostDto toPostDto(Post post);

    Post toPost(PostDto postDto);

    PostResponse toPostResponse(Post post);
}
