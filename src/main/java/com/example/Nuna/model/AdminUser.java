package com.example.Nuna.model;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Entity
public class AdminUser extends BaseUser{

    public AdminUser() {
        super();
        setRole(Role.ADMIN);
    }
}
