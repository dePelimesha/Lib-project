package com.karengin.libproject.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "users_role")
public class UsersRoleDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty
    private String role;

    @OneToMany(mappedBy = "user_role")
    @JsonIgnore
    private List<UsersDbo> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UsersDbo> getUsers() {
        return users;
    }

    public void setUsers(List<UsersDbo> users) {
        this.users = users;
    }
}
