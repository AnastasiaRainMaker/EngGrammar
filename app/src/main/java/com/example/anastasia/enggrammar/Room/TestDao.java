package com.example.anastasia.enggrammar.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.example.anastasia.enggrammar.POJO.Test;

import java.util.List;

/**
 * Created by anastasia on 1/5/18.
 */
@Dao
public interface TestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTest(Test... tests);

    @Delete
    void deleteTest(Test... tests);

    @Query("SELECT * FROM test")
    List<Test> loadAllTests();

    @Query("SELECT * FROM test WHERE id LIKE :firebaseId")
    List<Test> findTestById(String firebaseId);

    @Query("UPDATE test SET checkedTest = :value WHERE id = :id")
    void updateCheckedTest (Boolean value, String id);
}
