package com.jeanbarcellos.project106.mapper;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.jeanbarcellos.core.MapperBase;
import com.jeanbarcellos.project106.domain.Person;
import com.jeanbarcellos.project106.dtos.PersonRequest;
import com.jeanbarcellos.project106.dtos.PersonResponse;

@ApplicationScoped
public class PersonMapper extends MapperBase {

    public Person toPerson(PersonRequest source) {
        return this.map(source, Person.class);
    }

    public PersonResponse toPersonResponse(Person source) {
        return this.map(source, PersonResponse.class);
    }

    public List<PersonResponse> toListPersonResponse(List<Person> source) {
        return this.mapList(source, PersonResponse.class);
    }

}
