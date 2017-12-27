package com.example.anastasia.enggrammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by anastasia on 12/27/17.
 */

public class SingleTestActivity extends AppCompatActivity {
    ImageView arrowBack;
    TextView testNameView;
    Button checkBtn;
    String testName;
    TextView goToRule;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_test);
        testName = getIntent().getStringExtra("testNumber");
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        testNameView = findViewById(R.id.topic_grammar_name);
        checkBtn = findViewById(R.id.check);
        goToRule = findViewById(R.id.go_to_rule);
        setUpViews();
    }

    private void setUpViews() {
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

    }
}
