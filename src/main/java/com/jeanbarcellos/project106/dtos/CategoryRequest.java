package com.jeanbarcellos.project106.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CategoryRequest {

    @JsonIgnore
    private Long id;

    @NotBlank
    @Size(min = 4, max = 128)
    private String name;

    @NotBlank
    protected String description;

}
