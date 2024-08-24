package com.jeanbarcellos.project106.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @NotEmpty
    @Email
    private String email;

    @NotNull
    @PastOrPresent
    @Schema(pattern = "date", description = "Data de nascimento")
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", lenient
    // = OptBoolean.FALSE)
    private LocalDate dateBirthday;

}