package com.example.anastasia.enggrammar;

import android.content.Intent;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.anastasia.enggrammar.POJO.Option;
import com.example.anastasia.enggrammar.POJO.Question;
import com.example.anastasia.enggrammar.POJO.Test;
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
    List<String> questionList = new ArrayList<>();
    List<String> optionsList = new ArrayList<>();
    RadioGroup mRadioGroup;
    RecyclerView.LayoutManager mRecyclerManager;
    Boolean isCleared;
    BottomNavigationView bottomNavigationView;
    DatabaseReference mDatabase;
    ValueEventListener postListener;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_test);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mDatabase = FirebaseDatabase.getInstance().getReference("topics");
        topicName = getIntent().getStringExtra("topicName");
        testName = getIntent().getStringExtra("testNumber");
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        testNameView = findViewById(R.id.topic_grammar_name);
        mRecycler = findViewById(R.id.recycler_test_content);
        isCleared = false;
        singleTestAdapter = new SingleTestAdapter(optionsList, questionList, isCleared);
        mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(singleTestAdapter);
        setUpViews();
        prepareData();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(postListener != null) {
            mDatabase.removeEventListener(postListener);
        }
    }

    private void prepareData() {
        final StringBuilder stringBuilder = new StringBuilder();
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DataSnapshot tests = data.child("tests");
                    for (DataSnapshot test : tests.getChildren()) {
                        Test mTest = test.getValue(Test.class);
                        if (mTest != null && Objects.equals(mTest.getName(), testName)) {
                            DataSnapshot questions = test.child("questions");
                            for (DataSnapshot q : questions.getChildren()) {
                                DataSnapshot options = q.child("options");
                                Question mQuestion = q.getValue(Question.class);
                                if (mQuestion != null) {
                                    questionList.add(mQuestion.getText());
                                }
                                for (DataSnapshot o : options.getChildren()) {
                                    String mOption = o.getValue(String.class);
                                    if (mOption != null) {
                                        stringBuilder.append(mOption).append(" ");
                                    }
                                }
                            }
                        }
                        optionsList.add(stringBuilder.toString());
                    }
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
                                singleTestAdapter.setIsCleared(true);
                                singleTestAdapter.notifyDataSetChanged();
                                break;
                            case R.id.check:
                                Toast.makeText(SingleTestActivity.this, "Your test is checked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.go_to_rule:
                                Intent i = new Intent(getApplicationContext(), TopicGrActivity.class);
                                i.putExtra("topicName", testName);
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
        if (mRadioGroup != null) {
            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(postListener != null) {
//            mDatabase.removeEventListener(postListener);
//        }
    }

}
