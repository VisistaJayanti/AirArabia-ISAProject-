package com.example.ApplicationLogin.DTO;

public class RoleDTO {

    private Long id;
    private String name;


    //Creating full constructors

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    //Creating empty constructor

    public RoleDTO() {
    }


    //Creating getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //Creating toString methods


    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
