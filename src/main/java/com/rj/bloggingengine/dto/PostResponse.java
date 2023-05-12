package com.rj.bloggingengine.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PostResponse {

    private Integer id;

    @NotBlank
    private String author;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private boolean archived;

    private double averageRating;

    private List<ReviewDto> reviews;

}
