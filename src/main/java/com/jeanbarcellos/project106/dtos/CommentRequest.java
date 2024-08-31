package com.jeanbarcellos.project106.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class CommentRequest {

    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private Long authorId;

    @NotBlank
    private String text;

}
