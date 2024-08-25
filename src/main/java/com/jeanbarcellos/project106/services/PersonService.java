package com.jeanbarcellos.project106.services;

import java.util.List;

import com.jeanbarcellos.core.Constants;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.core.validation.Validate;
import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project106.domain.Person;
import com.jeanbarcellos.project106.dtos.PersonRequest;
import com.jeanbarcellos.project106.dtos.PersonResponse;
import com.jeanbarcellos.project106.mapper.PersonMapper;
import com.jeanbarcellos.project106.repositories.PersonRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PersonService {

    public static final String MSG_ERROR_PESSOA_NOT_FOUND = "Não há pessoa para o ID '%s' informado.";
    public static final String MSG_ERROR_PESSOA_CPF_EXISTS = "Pessoa com o CPF '%s' já existe.";

    @Inject
    protected Validator validator;

    @Inject
    protected PersonRepository repository;

    @Inject
    protected PersonMapper mapper;

    public List<PersonResponse> getAll() {
        var entities = this.repository.listAll();

        return this.mapper.toListPersonResponse(entities);
    }

    public PersonResponse getById(Long id) {
        var entity = this.findByIdOrThrow(id);

        return this.mapper.toPersonResponse(entity);
    }

    @Transactional
    public PersonResponse insert(@Validate PersonRequest request) {
        this.validateExistsByCpf(request.getPersonalNumber());

        var entity = this.mapper.toPerson(request);

        this.repository.persist(entity);

        this.repository.flush();

        return this.mapper.toPersonResponse(entity);
    }

    @Transactional
    public PersonResponse update(@Validate PersonRequest request) {
        var entity = this.findByIdOrThrow(request.getId());

        this.mapper.copy(entity, request);

        this.repository.flush();

        return this.mapper.toPersonResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!this.repository.existsById(id)) {
            throw new NotFoundException(String.format(MSG_ERROR_PESSOA_NOT_FOUND, id));
        }

        this.repository.deleteById(id);

        this.repository.flush();
    }

    private Person findByIdOrThrow(Long id) {
        return this.repository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_PESSOA_NOT_FOUND, id)));
    }

    private void validateExistsByCpf(String cpf) {
        if (this.repository.existsByCpf(cpf)) {
            throw new ValidationException(Constants.MSG_ERROR_VALIDATION,
                    String.format(MSG_ERROR_PESSOA_CPF_EXISTS, cpf));
        }
    }

}
