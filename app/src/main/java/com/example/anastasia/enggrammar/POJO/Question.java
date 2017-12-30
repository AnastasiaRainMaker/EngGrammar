package com.example.anastasia.enggrammar.POJO;

import java.util.ArrayList;

/**
 * Created by anastasia on 12/25/17.
 */
public class Question {
    public String name;
    public ArrayList<String> options;
    public String answer;

    public Question() {

    }
    public Question(String name, String answer, ArrayList<String> options){

        this.name = name;
        this.options = options;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
