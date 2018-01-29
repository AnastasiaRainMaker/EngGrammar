package com.example.anastasia.enggrammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.anastasia.enggrammar.POJO.Topic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by anastasia on 12/26/17.
 */

public class TopicGrActivity extends AppCompatActivity {
    String topicName;
    TextView topicNameView;
    Button goToTestsBtn;
    ImageView arrowBack;
    DatabaseReference mDatabase;
    ValueEventListener postListener;
    TextView description;
    ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_gr);
        mDatabase = FirebaseDatabase.getInstance().getReference("topics");
        topicName = getIntent().getStringExtra("topicName");
        topicNameView = findViewById(R.id.topic_grammar_name);
        goToTestsBtn = findViewById(R.id.go_to_tests);
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        description = findViewById(R.id.topic_description);
        progressBar = findViewById(R.id.progress_topic);
        setUpViews();
        loadData();
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Topic topic = data.getValue(Topic.class);
                    if (topic != null) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            String spannableString = new SpannableString(Html.fromHtml(topic.getDescription(), Html.FROM_HTML_MODE_LEGACY)).toString();
                            Spanned spanned = Html.fromHtml(spannableString, Html.FROM_HTML_MODE_LEGACY);
                            description.setText(spanned, TextView.BufferType.SPANNABLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            String spannableString = new SpannableString(Html.fromHtml(topic.getDescription())).toString();
                            Spanned spanned = Html.fromHtml(spannableString);
                            description.setText(spanned, TextView.BufferType.SPANNABLE);
                            progressBar.setVisibility(View.GONE);
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
        description.setMovementMethod(new ScrollingMovementMethod());
        description.setMovementMethod(LinkMovementMethod.getInstance());
        topicNameView.setText(topicName);
        goToTestsBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), TopicTestsList.class);
            i.putExtra("testName", topicName);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
        arrowBack.setOnClickListener(view -> onBackPressed());

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(postListener != null) {
            mDatabase.removeEventListener(postListener);
        }
    }
//
//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
////        Intent i = new Intent(getApplicationContext(), GrammarActivity.class);
////        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        startActivity(i);
//    }
}
