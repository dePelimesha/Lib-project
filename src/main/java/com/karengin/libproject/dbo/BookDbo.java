package com.karengin.libproject.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "books")
public class BookDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private AuthorDbo author;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<CommentsDbo> comments;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuthorDbo getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDbo author) {
        this.author = author;
    }

    public List<CommentsDbo> getComments() {
        return comments;
    }

    public void setComments(List<CommentsDbo> comments) {
        this.comments = comments;
    }
}
