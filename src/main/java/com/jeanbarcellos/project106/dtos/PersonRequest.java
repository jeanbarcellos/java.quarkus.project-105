package com.jeanbarcellos.project106.dtos;

import java.time.LocalDate;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequest {

    @JsonIgnore
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 128)
    private String name;

    @NotEmpty
    @CPF
    private String personalNumber;

    @NotNull
    @PastOrPresent
    @Schema(pattern = "date", description = "Data de nascimento")
    private LocalDate dateBirthday;

    @NotEmpty
    @Email
    private String email;
}