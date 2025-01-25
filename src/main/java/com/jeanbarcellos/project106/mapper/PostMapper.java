package com.jeanbarcellos.project106.mapper;

import org.modelmapper.Converter;

import com.jeanbarcellos.core.MapperBase;
import com.jeanbarcellos.project106.domain.Category;
import com.jeanbarcellos.project106.domain.Comment;
import com.jeanbarcellos.project106.domain.Person;
import com.jeanbarcellos.project106.domain.Post;
import com.jeanbarcellos.project106.dtos.CommentRequest;
import com.jeanbarcellos.project106.dtos.PostRequest;
import com.jeanbarcellos.project106.repositories.CategoryRepository;
import com.jeanbarcellos.project106.repositories.PersonRepository;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class PostMapper extends MapperBase<Post> {

    protected PersonRepository personRepository;

    protected CategoryRepository categoryRepository;

    public Post copy(Post destination, PostRequest source) {
        var mp = this.getModelMapper();

        mp.addConverter(this.getCategoryConverter(), Long.class, Category.class);
        mp.addConverter(this.getPersonConverter(), Long.class, Person.class);

        mp.map(source, destination);

        return destination;
    }

    public Comment copy(Comment destination, CommentRequest source) {
        destination.setText(source.getText());

        return destination;
    }

    private Converter<Long, Person> getPersonConverter() {
        return context -> {
            var id = context.getSource();
            return this.personRepository.getReference(id);
        };
    }

    private Converter<Long, Category> getCategoryConverter() {
        return context -> {
            var id = context.getSource();
            return this.categoryRepository.getReference(id);
        };
    }

}
