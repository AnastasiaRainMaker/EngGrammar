package com.example.anastasia.enggrammar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.anastasia.enggrammar.POJO.Test;
import com.example.anastasia.enggrammar.RecyclerDividers.SimpleDividerItemDecorationBlue;
import com.example.anastasia.enggrammar.adapters.TopicTestListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasia on 12/26/17.
 */

public class TopicTestsList extends AppCompatActivity implements TopicTestListAdapter.OnItemClicked {
    RecyclerView mRecycler;
    List<String> testList = new ArrayList<>();
    TopicTestListAdapter topicTestListAdapter;
    ImageView arrowBack;
    TextView topicNameView;
    String topicName;
    DatabaseReference mDatabase;
    ValueEventListener postListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicName = getIntent().getStringExtra("testName");
        setContentView(R.layout.activity_topic_test_list);
        mDatabase = FirebaseDatabase.getInstance().getReference("topics");
        topicNameView = findViewById(R.id.topic_grammar_name);
        mRecycler = findViewById(R.id.recycler_topic_test_list);
        topicTestListAdapter = new TopicTestListAdapter(testList);
        RecyclerView.LayoutManager mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(topicTestListAdapter);
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        setUpViews();
        prepareTestList();
    }

    private void prepareTestList() {
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                  DataSnapshot tests = data.child("tests");
                  for (DataSnapshot test : tests.getChildren()){
                    Test mTest = test.getValue(Test.class);
                    if(mTest != null) {
                        testList.add(mTest.getName());
                        topicTestListAdapter.notifyDataSetChanged();
                    }
                  }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.orderByChild("name").equalTo(topicName).addValueEventListener(postListener);
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
        topicTestListAdapter.setOnClick(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(postListener != null) {
            mDatabase.removeEventListener(postListener);
        }
    }

    @Override
    public void onItemClick(int position) {
        String testNumber = testList.get(position);
        Intent i = new Intent(getApplicationContext(), SingleTestActivity.class);
        i.putExtra("testNumber", testNumber);
        i.putExtra("topicName", topicName);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), TopicGrActivity.class);
        i.putExtra("topicName", topicName);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}