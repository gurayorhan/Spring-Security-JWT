package com.example.springsecurityjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String username;

    private String password;

    @ManyToMany
    private List<Role> roles;

}
