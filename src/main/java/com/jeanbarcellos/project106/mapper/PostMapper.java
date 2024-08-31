package com.jeanbarcellos.project106.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ExpressionMap;

import com.jeanbarcellos.core.MapperBase;
import com.jeanbarcellos.project106.domain.Category;
import com.jeanbarcellos.project106.domain.Person;
import com.jeanbarcellos.project106.domain.Post;
import com.jeanbarcellos.project106.dtos.PostRequest;
import com.jeanbarcellos.project106.repositories.CategoryRepository;
import com.jeanbarcellos.project106.repositories.PersonRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PostMapper extends MapperBase<Post> {

    @Inject
    PersonRepository personRepository;

    @Inject
    CategoryRepository categoryRepository;

    public Post copy(Post destination, PostRequest source) {
        var mp = this.getModelMapper();

        Converter<Long, Category> categoryConverter = context -> {
            var value = context.getSource();

            var category = this.categoryRepository.getReference(value);
            return category;
        };

        Converter<Long, Person> personConverter = context -> {
            var value = context.getSource();

            var person = this.personRepository.getReference(value);
            return person;
        };

        mp.addConverter(categoryConverter, Long.class, Category.class);
        mp.addConverter(personConverter, Long.class, Person.class);

        mp.map(source, destination);

        return destination;
    }

    public Post copy3(Post destination, PostRequest source) {
        var mp = this.getModelMapper();

        log.info("source.getCategoryId() {}", source.getCategoryId());
        log.info("source.getAuthorId() {}", source.getAuthorId());
        log.info("destination.getCategory().getId() {}", destination.getCategory().getId());
        log.info("destination.getAuthor.getId() {}", destination.getAuthor().getId());

        // TypeMap<PostRequest, Post> propertyMapper =
        // mapper.createTypeMap(PostRequest.class, Post.class);

        ExpressionMap<PostRequest, Post> mapper = mapperInt -> {
            mapperInt.<Long>map(PostRequest::getCategoryId,
                    (dest, value) -> {
                        log.info("getCategoryId() {}", value);

                        // var category = this.categoryRepository.getReference(value);
                        // dest.setCategory(category);

                        dest.getCategory().setId(value);
                    });

            mapperInt.<Long>map(PostRequest::getAuthorId,
                    (dest, value) -> {
                        log.info("getAuthorId() {}", value);

                        // var person = this.personRepository.getReference(value);
                        // dest.setAuthor(person);

                        dest.getAuthor().setId(value);
                    });
        };

        mp.typeMap(PostRequest.class, Post.class)
                .addMappings(mapper);

        // typeMap.addMappings(mapper -> mapper.<String>map(src ->
        // src.getPerson().getFirstName(), (dest, v) -> dest.getCustomer().setName(v)));

        // // Provider<Post> postProvider = p -> this.postRepository.findById(1L);

        mp.map(source, destination);

        log.info("source.getCategoryId() {}", source.getCategoryId());
        log.info("source.getAuthorId() {}", source.getAuthorId());
        log.info("destination.getCategory().getId() {}", destination.getCategory().getId());
        log.info("destination.getAuthor.getId() {}", destination.getAuthor().getId());

        return destination;
        // return this.copyProperties(source, request);
    }

    public Post copy2(Post destination, PostRequest source) {
        var mp = this.getModelMapper();

        destination.setCategory(this.categoryRepository.getReference(source.getCategoryId()));
        destination.setAuthor(this.personRepository.getReference(source.getAuthorId()));
        destination.setTitle(source.getTitle());
        destination.setText(source.getText());

        mp.map(source, destination);

        log.info("getCategoryId() {}", source.getCategoryId());
        log.info("getAuthorId() {}", source.getAuthorId());

        return destination;
    }
}
