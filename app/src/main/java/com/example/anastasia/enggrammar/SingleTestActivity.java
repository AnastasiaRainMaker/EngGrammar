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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anastasia.enggrammar.adapters.SingleTestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasia on 12/27/17.
 */

public class SingleTestActivity extends AppCompatActivity {
    ImageView arrowBack;
    TextView testNameView;
    String testName;
    RecyclerView mRecycler;
    SingleTestAdapter singleTestAdapter;
    List<String> questionList = new ArrayList<>();
    List<String> optionsList = new ArrayList<>();
    RadioGroup mRadioGroup;
    RecyclerView.LayoutManager mRecyclerManager;
    Boolean isCleared;
    BottomNavigationView bottomNavigationView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_test);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        testName = getIntent().getStringExtra("testNumber");
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        testNameView = findViewById(R.id.topic_grammar_name);
        mRecycler = findViewById(R.id.recycler_test_content);
        isCleared = false;
        singleTestAdapter = new SingleTestAdapter(optionsList, questionList, isCleared);
        mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(singleTestAdapter);
        prepareData();
        setUpViews();
    }

    private void prepareData() {
        questionList.add("We bought __ house.");
        questionList.add("We bought __ house.");
        questionList.add("We bought __ house.");
        questionList.add("We bought __ house.");
        questionList.add("We bought __ house.");
        questionList.add("We bought __ house.");
        questionList.add("We bought __ house.");
        optionsList.add("a the -");
        optionsList.add("a the -");
        optionsList.add("a the -");
        optionsList.add("a the -");
        optionsList.add("a the -");
        optionsList.add("a the -");
        optionsList.add("a the -");
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
}
