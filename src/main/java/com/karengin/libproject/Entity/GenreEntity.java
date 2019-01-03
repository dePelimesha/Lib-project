package com.karengin.libproject.Entity;

import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "genre")
public class GenreEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List <BookEntity> books;
}