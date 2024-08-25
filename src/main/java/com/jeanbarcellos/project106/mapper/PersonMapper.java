package com.jeanbarcellos.project106.mapper;

import java.util.List;

import com.jeanbarcellos.core.MapperBase;
import com.jeanbarcellos.project106.domain.Person;
import com.jeanbarcellos.project106.dtos.PersonRequest;
import com.jeanbarcellos.project106.dtos.PersonResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonMapper extends MapperBase<Person> {

    public Person toPerson(PersonRequest source) {
        return this.map(source);
    }

    public PersonResponse toPersonResponse(Person source) {
        return this.map(source, PersonResponse.class);
    }

    public List<PersonResponse> toListPersonResponse(List<Person> source) {
        return this.mapList(source, PersonResponse.class);
    }

    public Person copy(Person destination, PersonRequest source) {
        return this.copyProperties(destination, source);
    }

}
