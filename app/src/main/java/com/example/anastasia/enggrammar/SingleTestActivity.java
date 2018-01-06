package com.example.anastasia.enggrammar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.anastasia.enggrammar.POJO.Question;
import com.example.anastasia.enggrammar.POJO.Test;
import com.example.anastasia.enggrammar.Room.AppDatabase;
import com.example.anastasia.enggrammar.adapters.SingleTestAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by anastasia on 12/27/17.
 */

public class SingleTestActivity extends AppCompatActivity {
    ImageView arrowBack;
    TextView testNameView;
    String testName;
    String topicName;
    RecyclerView mRecycler;
    SingleTestAdapter singleTestAdapter;
    List<Question> questionList = new ArrayList<>();
    RecyclerView.LayoutManager mRecyclerManager;
    BottomNavigationView bottomNavigationView;
    DatabaseReference mDatabase;
    ValueEventListener postListener;
    private AppDatabase roomDatabase;
    Boolean isChecked;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_test);
        isChecked = false;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mDatabase = FirebaseDatabase.getInstance().getReference("topics");
        topicName = getIntent().getStringExtra("topicName");
        testName = getIntent().getStringExtra("testNumber");
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        testNameView = findViewById(R.id.topic_grammar_name);
        mRecycler = findViewById(R.id.recycler_test_content);
        singleTestAdapter = new SingleTestAdapter(this, questionList);
        mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(singleTestAdapter);
        roomDatabase = AppDatabase.getDatabase(getApplicationContext());
        setUpViews();
        prepareData();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(postListener != null) {
            mDatabase.removeEventListener(postListener);
        }
        if(roomDatabase != null) {
            AppDatabase.destroyInstance();
        }
    }

    private void prepareData() {
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DataSnapshot tests = data.child("tests");
                    for (DataSnapshot test : tests.getChildren()) {
                        Test mTest = test.getValue(Test.class);
                        if (mTest != null && Objects.equals(mTest.getName(), testName)) {
//                            if (roomDatabase.testDao().findTestById(mTest.getId()).size() == 0) {
//                                roomDatabase.testDao().insertTest(mTest);
//                            }
                            DataSnapshot questions = test.child("questions");
                            for (DataSnapshot q : questions.getChildren()) {
                                Question mQuestion = q.getValue(Question.class);
                                if (mQuestion != null) {
                                    if (roomDatabase.questionDao().findQuestionById(mQuestion.getId()).size() == 0) {
                                        Question newQuestion = new Question();
                                        newQuestion.setId(mQuestion.getId());
                                        newQuestion.setAnswer(mQuestion.getAnswer());
                                        roomDatabase.questionDao().insertQuestion(newQuestion);
                                    }
                                 questionList.add(mQuestion);
                                }
                            }
                        }
                    }
                    singleTestAdapter.setuAnswerListSize(questionList.size());
                    singleTestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.orderByChild("name").equalTo(topicName).addValueEventListener(postListener);
    }

    private void setUpViews() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.clear:
                                singleTestAdapter.setCleared(true);
                                singleTestAdapter.setChecked(false);
                                singleTestAdapter.clearUserAnswer();
                                singleTestAdapter.notifyDataSetChanged();
                                setIsChecked(false);
                                break;
                            case R.id.check:
                                if (!isChecked) {
                                    singleTestAdapter.setChecked(true);
                                   // singleTestAdapter.setCleared(false);
                                    if (singleTestAdapter.checkTest()) {
                                        singleTestAdapter.notifyDataSetChanged();
                                    }
                                    //singleTestAdapter.displayTestResult();
                                }
                                break;
                            case R.id.go_to_rule:
                                Intent i = new Intent(getApplicationContext(), TopicGrActivity.class);
                                i.putExtra("topicName", topicName);
                                startActivity(i);
                                break;
                        }
                        return true;
                    }
                });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        testNameView.setText(testName);

    }
    public ColorStateList setColorStateList() {

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked},
                new int[] {-android.R.attr.state_checked}
        };

        int[] colors = new int[] {
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
        };

        return new ColorStateList(states, colors);
    }

    public void setIsChecked(Boolean value) {
        isChecked = value;
    }

}
