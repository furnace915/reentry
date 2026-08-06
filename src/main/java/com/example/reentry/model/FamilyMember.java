package com.example.reentry.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    // Required by JPA/Hibernate, which instantiates entities via reflection when loading from the database.
    protected FamilyMember() {
    }

    public FamilyMember(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

}
