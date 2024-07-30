package com.example.ApplicationLogin.domain;


import jakarta.persistence.*;


@Entity
@Table(name="Roles")
public class Role {

    @Id
    @Column(name= "role_id", length=45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //Creating full constructors


    public Role(int role_id) {
        this.id = role_id;
    }


    //Creating empty constructor

    public Role() {
    }


    //Creating getters and setters

    public int getRole_id() {
        return id;
    }

    public void setRole_id(int role_id) {
        this.id = role_id;
    }

    //Creating toString() method

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + id +
                '}';
    }
}
