package com.karengin.libproject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "author")
public class AuthorEntity extends AbstractEntity {

    @NotNull
    private String name;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<BookEntity> books;
}
