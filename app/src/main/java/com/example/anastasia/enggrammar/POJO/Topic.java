package com.example.anastasia.enggrammar.POJO;

import java.util.ArrayList;

/**
 * Created by anastasia on 12/25/17.
 */
public class Topic {
    public String name;
    public String description;

    public Topic() {

    }
    public Topic (String name, String description){

        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
