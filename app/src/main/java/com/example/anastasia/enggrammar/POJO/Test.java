package com.example.anastasia.enggrammar.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by anastasia on 12/25/17.
 */
@Entity (tableName = "test")
public class Test {
    @Ignore
    public String name;
    @Ignore
    private String isNew;
    @NonNull
    @PrimaryKey
    public String id;

    public boolean checkedTest;

    public Boolean correctTest;

    public Test() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public boolean isCheckedTest() {
        return checkedTest;
    }

    public void setCheckedTest() {
        this.checkedTest = false;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setNew(String aNew) {
        this.isNew = aNew;
    }

    public Boolean isCorrectTest() {
        return correctTest;
    }

    public void setCorrectTest(Boolean correctTest) {
        this.correctTest = correctTest;
    }

}
