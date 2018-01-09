package com.example.anastasia.enggrammar.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.anastasia.enggrammar.POJO.Question;
import java.util.List;

import io.reactivex.Single;


/**
 * Created by anastasia on 1/5/18.
 */
@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question... questions);

    @Query("SELECT id FROM question")
    String getId();

    @Query("SELECT * FROM question WHERE id LIKE :firebaseId LIMIT 1")
    List<Question> findQuestionById(String firebaseId);

    @Query("UPDATE question SET uAnswer = :uAnswer WHERE id = :id")
    void updateQuestion (String uAnswer, String id);

    @Query("UPDATE question SET checked = :checked WHERE id = :id")
    void updateChecked (Boolean checked, String id);
}
