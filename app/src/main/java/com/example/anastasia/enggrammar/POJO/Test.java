package com.example.anastasia.enggrammar.POJO;

import java.util.ArrayList;

/**
 * Created by anastasia on 12/25/17.
 */
public class Test {
    public String name;
    public ArrayList<String> questions;

    public Test() {

    }
    public Test(String name, ArrayList<String> questions){

        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }
}
