package com.jeanbarcellos.project106.repositories;

import javax.enterprise.context.ApplicationScoped;

import com.jeanbarcellos.core.RepositoryBase;
import com.jeanbarcellos.project106.domain.Person;

@ApplicationScoped
public class PersonRepository extends RepositoryBase<Person, Long> {

    public boolean existsByCpf(String cpf) {
        return this.existsBy(Person.FIELD_CPF, cpf);
    }

}
