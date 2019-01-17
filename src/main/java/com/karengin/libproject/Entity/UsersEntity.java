package com.karengin.libproject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    @JsonIgnore
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id")
    @JsonIgnore
    private UsersRoleEntity userRole;
}
