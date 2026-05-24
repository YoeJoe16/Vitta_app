package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table(name="tb_users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer totalPoints;
    private String range;

    public User() {
    }

    public User(Integer id, String name, String email, String password, Integer totalPoints, String range) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.totalPoints = totalPoints;
        this.range = range;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
