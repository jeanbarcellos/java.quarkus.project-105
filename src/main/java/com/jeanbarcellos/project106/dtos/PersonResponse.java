package com.jeanbarcellos.project106.dtos;

import java.time.LocalDate;

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
public class PersonResponse {

    private Long id;
    private String name;
    private String personalNumber;
    private String email;
    private LocalDate dateBirthday;

}