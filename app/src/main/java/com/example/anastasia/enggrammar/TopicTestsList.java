package com.example.anastasia.enggrammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.anastasia.enggrammar.POJO.Test;
import com.example.anastasia.enggrammar.Room.AppDatabase;
import com.example.anastasia.enggrammar.recyclerDividers.SimpleDividerItemDecorationBlue;
import com.example.anastasia.enggrammar.adapters.TopicTestListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.anastasia.enggrammar.Constants.FROM_TESTS;
import static com.example.anastasia.enggrammar.Constants.TEST_NAME;
import static com.example.anastasia.enggrammar.Constants.TEST_NUMBER;
import static com.example.anastasia.enggrammar.Constants.TOPIC_NAME;

/**
 * Created by anastasia on 12/26/17.
 */

public class TopicTestsList extends AppCompatActivity implements TopicTestListAdapter.OnItemClicked {
    private RecyclerView mRecycler;
    private List<Test> testList = new ArrayList<>();
    private TopicTestListAdapter topicTestListAdapter;
    private ImageView arrowBack;
    private TextView topicNameView;
    private Boolean fromTest;
    private String topicName;
    private DatabaseReference mDatabase;
    private ValueEventListener postListener;
    private ProgressBar progressBar;
    private AppDatabase roomDatabase;
    private CompositeDisposable mSubscriptions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromTest = getIntent().getBooleanExtra(FROM_TESTS, false);
        topicName = getIntent().getStringExtra(TEST_NAME);
        setContentView(R.layout.activity_topic_test_list);
        initView();
        mDatabase = FirebaseDatabase.getInstance().getReference("topics");
        mRecycler = findViewById(R.id.recycler_topic_test_list);
        topicTestListAdapter = new TopicTestListAdapter(this, testList);
        RecyclerView.LayoutManager mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(topicTestListAdapter);
        roomDatabase = AppDatabase.getDatabase(getApplicationContext());
        mSubscriptions = new CompositeDisposable();
        setUpViews();
        prepareTestList();
    }

    public void initView(){
        topicNameView = findViewById(R.id.topic_grammar_name);
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        progressBar = findViewById(R.id.progress_test_list);
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
                        testList.add(mTest);
                    }
                  }
                   topicTestListAdapter.setTestCheckedSize(testList.size());
                   topicTestListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.orderByChild("name").equalTo(topicName).addValueEventListener(postListener);
    }

    private void setUpViews() {
        progressBar.setVisibility(View.VISIBLE);
        mRecycler.addItemDecoration(new SimpleDividerItemDecorationBlue(getResources()));
        arrowBack.setOnClickListener(view -> onBackPressed());
        topicNameView.setText(topicName);
        topicTestListAdapter.setOnClick(this);
    }

    public void isTestChecked(String id, int position) {
        mSubscriptions.add(readWithRX(id, position));
        progressBar.setVisibility(View.VISIBLE);
    }

    public Disposable readWithRX(String id, int position) {
        return Single.fromCallable (() -> roomDatabase.testDao().findTestById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            if (res.size() > 0 && res.get(0).isCheckedTest()) {
                                topicTestListAdapter.setTestChecked(true, position);
                            } else {
                                topicTestListAdapter.setTestChecked(false, position);
                            }
                            if ( res.size() > 0 && res.get(0).isCorrectTest() != null && res.get(0).isCorrectTest()) {
                               topicTestListAdapter.setTestCorrect(true, position);
                            } else if (res.size() > 0 && res.get(0).isCorrectTest() != null && !res.get(0).isCorrectTest()){
                                topicTestListAdapter.setTestCorrect(false, position);
                            }
                            topicTestListAdapter.notifyItemChanged(position);

                        },
                        throwable -> Log.e( Constants.TAG, "rxjava testlist threw error")
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        topicTestListAdapter.clearTestChecked();
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
        if (mSubscriptions != null && !mSubscriptions.isDisposed()) {
            mSubscriptions.clear();
        }
    }

    @Override
    public void onItemClick(int position) {
        String testNumber = testList.get(position).getName();
        Intent i = new Intent(getApplicationContext(), SingleTestActivity.class);
        i.putExtra(FROM_TESTS, fromTest);
        i.putExtra(TEST_NUMBER, testNumber);
        i.putExtra(TOPIC_NAME, topicName);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
            if (!fromTest) {
                super.onBackPressed();
            } else {
                Intent i = new Intent(getApplicationContext(), TestsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
    }

    public void hideProgress(int position) {
        if (position == testList.size()-1) progressBar.setVisibility(View.GONE);
    }
}