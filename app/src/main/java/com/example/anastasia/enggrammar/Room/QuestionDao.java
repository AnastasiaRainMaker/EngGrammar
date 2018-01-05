package com.example.anastasia.enggrammar.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.anastasia.enggrammar.POJO.Question;
import java.util.List;


/**
 * Created by anastasia on 1/5/18.
 */
@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question... questions);

    @Delete
    void deleteQuestion(Question... questions);

    @Query("SELECT id FROM question")
    String getId();

    @Query("SELECT answer FROM question WHERE id LIKE :questionId")
    String getrAnswer(String questionId);

    @Query("SELECT uAnswer FROM question WHERE id LIKE :questionId")
    String getuAnswer(String questionId);

    @Query("SELECT * FROM question WHERE id LIKE :firebaseId")
    List<Question> findQuestionById(String firebaseId);

    @Query("UPDATE question SET uAnswer = :uAnswer WHERE id = :id")
    void updateQuestion (String uAnswer, String id);
}