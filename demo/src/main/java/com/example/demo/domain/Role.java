package com.example.demo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;
    @OneToMany(mappedBy = "role",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<User> users = new ArrayList<>();


    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
