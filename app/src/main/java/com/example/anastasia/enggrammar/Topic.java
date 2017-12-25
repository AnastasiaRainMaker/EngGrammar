package com.example.anastasia.enggrammar;

import java.util.ArrayList;

/**
 * Created by anastasia on 12/25/17.
 */
public class Topic {
    public String name;
    public String description;
    public String testName;
    public String question;
    public String answer;
    public ArrayList<String> options;

    public Topic() {

    }
    public Topic (String name, String description, String testName, String question, String answer, ArrayList<String> options){
        this.name = name;
        this.description = description;
        this.testName = testName;
        this.question = question;
        this.answer = answer;
        this.options = options;
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

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
