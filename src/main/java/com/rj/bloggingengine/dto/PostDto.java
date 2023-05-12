package com.rj.bloggingengine.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PostDto {


    private Integer id;

    @NotBlank
    private String author;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private boolean archived;

    private double averageRating;


}
