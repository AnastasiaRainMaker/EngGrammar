package com.example.anastasia.enggrammar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.anastasia.enggrammar.adapters.TopicTestListAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasia on 12/26/17.
 */

public class TopicTestsList extends AppCompatActivity {
    RecyclerView mRecycler;
    List<String> testlest = new ArrayList<>();
    TopicTestListAdapter topicTestListAdapter;
    ImageView arrowBack;
    TextView topicNameView;
    String topicName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicName = getIntent().getStringExtra("testName");
        setContentView(R.layout.activity_topic_test_list);
        topicNameView = findViewById(R.id.topic_grammar_name);
        mRecycler = findViewById(R.id.recycler_topic_test_list);
        topicTestListAdapter = new TopicTestListAdapter(testlest);
        RecyclerView.LayoutManager mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(topicTestListAdapter);
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        setUpViews();
        prepareTestList();
    }

    private void prepareTestList() {
        String testName = "Test";
        for (int i = 0; i < 10; i++) {
            testlest.add(testName + " " + i);
        }

    }

    private void setUpViews() {
        mRecycler.addItemDecoration(new SimpleDividerItemDecorationBlue(getResources()));
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        topicNameView.setText(topicName);
    }
}