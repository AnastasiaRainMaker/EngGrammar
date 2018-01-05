package com.example.anastasia.enggrammar.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by anastasia on 12/25/17.
 */
@Entity (tableName = "test")
public class Test {
    @Ignore
    public String name;
    @PrimaryKey
    @NonNull
    public String id;

    public Test() {

    }
    public Test(String name){

        this.name = name;
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

    public void setId(String id) {
        this.id = id;
    }


}
