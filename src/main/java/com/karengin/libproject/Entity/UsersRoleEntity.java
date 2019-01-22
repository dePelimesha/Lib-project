package com.karengin.libproject.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users_role")
public class UsersRoleEntity extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String role;
}
