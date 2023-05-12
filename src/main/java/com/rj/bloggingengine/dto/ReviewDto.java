package com.rj.bloggingengine.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ReviewDto {


    private Integer id;

    @NotNull
    @NotBlank
    @Min(1)
    @Max(5)
    private int rating;

    @NotNull
    @NotBlank
    private String author;

    @NotNull
    @NotBlank
    private String content;
}
