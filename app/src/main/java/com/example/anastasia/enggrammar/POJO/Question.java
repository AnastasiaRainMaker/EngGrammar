package com.example.anastasia.enggrammar.POJO;

import android.app.VoiceInteractor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anastasia on 12/25/17.
 */
public class Question {
    public String name;
    public HashMap<String,String> options;
    public String answer;
    public String id;
    public String text;

    public Question() {

    }

    public Question(String name, String answer, HashMap<String,String> options, String text){
        this.answer = answer;
        this.name = name;
        this.text = text;
        this.options = options;
    }
    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}