package com.kafka.consumer.example.Model;

import lombok.With;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
@With
public class UserEntity {
    public UserEntity(String id, String name, Integer age, Float rank) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.rank = rank;
    }

    public UserEntity() {
    }

    @Id
    private String id;
    private String name;
    private Integer age;

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    private Float rank;
    private Float previousRank;

    public Float getPreviousRank() {
        return previousRank;
    }

    public void setPreviousRank(Float previousRank) {
        this.previousRank = previousRank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
