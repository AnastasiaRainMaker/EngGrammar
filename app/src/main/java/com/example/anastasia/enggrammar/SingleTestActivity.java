package com.example.anastasia.enggrammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    Button checkBtn;
    String testName;
    TextView goToRule;
    RecyclerView mRecycler;
    SingleTestAdapter singleTestAdapter;
    List<String> questionList = new ArrayList<>();
    List<String> optionsList = new ArrayList<>();
    RadioGroup mRadioGroup;
    RecyclerView.LayoutManager mRecyclerManager;
    TextView clear;
    Boolean isCleared;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_test);
        testName = getIntent().getStringExtra("testNumber");
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        testNameView = findViewById(R.id.topic_grammar_name);
        checkBtn = findViewById(R.id.check);
        goToRule = findViewById(R.id.go_to_rule);
        clear = findViewById(R.id.clear_filled);
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
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleTestAdapter.setIsCleared(true);
                singleTestAdapter.notifyDataSetChanged();
           }
        });
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        testNameView.setText(testName);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SingleTestActivity.this, "Your test is checked", Toast.LENGTH_SHORT).show();
            }
        });
        goToRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TopicGrActivity.class);
                i.putExtra("topicName", testName);
                startActivity(i);
            }
        });
        if (mRadioGroup != null) {
            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                }
            });
        }

    }
}
