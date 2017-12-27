package com.example.anastasia.enggrammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anastasia on 12/26/17.
 */

public class TopicGrActivity extends AppCompatActivity {

    String topic;
    TextView topicNameView;
    Button goToTestsBtn;
    ImageView arrowBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_gr);
        topic = getIntent().getStringExtra("topicName");
        topicNameView = findViewById(R.id.topic_grammar_name);
        goToTestsBtn = findViewById(R.id.go_to_tests);
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        setUpViews();
    }

    private void setUpViews() {
        topicNameView.setText(topic);
        goToTestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TopicTestsList.class);
                i.putExtra("testName", topic);
                startActivity(i);
            }
        });
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
