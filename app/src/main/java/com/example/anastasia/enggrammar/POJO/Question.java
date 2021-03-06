package com.example.anastasia.enggrammar.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by anastasia on 12/25/17.
 */
@Entity(tableName = "question")
public class Question {
    @Ignore
    public String name;

    @Ignore
    private HashMap<String,String> options;

    @Ignore
    private Boolean cleared = false;

    public Boolean checked = false;

    public String answer;

    public String uAnswer;

    @PrimaryKey
    @NonNull
    public String id;

    @Ignore
    public String text;

    public Question() {

    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getIsCleared() {
        return cleared;
    }

    public void setCleared() {
        this.cleared = false;
    }

    public HashMap<String, String> getOptions() {
        return options;
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

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuAnswer() {
        return uAnswer;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }
}
