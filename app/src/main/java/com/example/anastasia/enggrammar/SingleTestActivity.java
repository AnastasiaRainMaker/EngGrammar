package com.example.anastasia.enggrammar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anastasia on 12/27/17.
 */

public class SingleTestActivity extends AppCompatActivity {
    Boolean fromTests;
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
    Test newTest;
    String testId;
    ProgressBar progress;
    private CompositeDisposable mSubscriptions;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_test);
        isChecked = false;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mDatabase = FirebaseDatabase.getInstance().getReference("topics");
        fromTests = getIntent().getBooleanExtra("fromTests", false);
        topicName = getIntent().getStringExtra("topicName");
        testName = getIntent().getStringExtra("testNumber");
        arrowBack = findViewById(R.id.arrow_back_toolbar);
        testNameView = findViewById(R.id.topic_grammar_name);
        mRecycler = findViewById(R.id.recycler_test_content);
        mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        singleTestAdapter = new SingleTestAdapter(this, questionList, testId);
        mRecycler.setAdapter(singleTestAdapter);
        roomDatabase = AppDatabase.getDatabase(getApplicationContext());
        mSubscriptions = new CompositeDisposable();
        progress = findViewById(R.id.progress_single_test);
        setUpViews();
        prepareData();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (postListener != null) {
            mDatabase.removeEventListener(postListener);
        }
        if (roomDatabase != null) {
            AppDatabase.destroyInstance();
        }
        if (mSubscriptions != null && !mSubscriptions.isDisposed()) {
            mSubscriptions.clear();
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
                            if (roomDatabase.testDao().findTestById(mTest.getId()).size() == 0) {
                                testId = mTest.getId();
                                newTest = new Test();
                                newTest.setId(testId);
                                newTest.setCheckedTest(false);
                                insertTestRoomRx(newTest);
                            } else {
                                testId = roomDatabase.testDao().findTestById(mTest.getId()).get(0).getId();
                            }
                            DataSnapshot questions = test.child("questions");
                            for (DataSnapshot q : questions.getChildren()) {
                                Question mQuestion = q.getValue(Question.class);
                                if (mQuestion != null) {
                                    if (roomDatabase.questionDao().findQuestionById(mQuestion.getId()).size() == 0) {
                                        Question newQuestion = new Question();
                                        newQuestion.setId(mQuestion.getId());
                                        newQuestion.setAnswer(mQuestion.getAnswer());
                                        newQuestion.setChecked(false);
                                        insertQuestionRoomRx(newQuestion);
                                    }
                                     questionList.add(mQuestion);
                                }
                            }
                        }
                    }
                    singleTestAdapter.setuAnswerListSize(questionList.size());
                    singleTestAdapter.notifyDataSetChanged();
                    if (!isChecked)
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.orderByChild("name").equalTo(topicName).addValueEventListener(postListener);
    }

    private void setUpViews() {
        if (!isChecked) {
            progress.setVisibility(View.VISIBLE);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.clear:
                            singleTestAdapter.setChecked(false);
                            roomDatabase.testDao().updateCheckedTest(false, testId);
                            setIsChecked(false);
                            singleTestAdapter.clearUserAnswer();
                            singleTestAdapter.notifyDataSetChanged();
                            updateCorrectTestRoom(null, testId);
                            break;
                        case R.id.check:
                            if (!isChecked) {
                                singleTestAdapter.setChecked(true);
                                if (singleTestAdapter.checkTest()) {
                                    singleTestAdapter.notifyDataSetChanged();
                                    if (singleTestAdapter.getTestResult()) {
                                        updateCorrectTestRoom(true,testId);
                                    } else {
                                        updateCorrectRoomRx(false, testId);
                                    }
                                    roomDatabase.testDao().updateCheckedTest(true, testId);
                                 }
                            }
                            break;
                        case R.id.go_to_rule:
                            Intent i = new Intent(getApplicationContext(), TopicGrActivity.class);
                            i.putExtra("fromTests", fromTests);
                            i.putExtra("topicName", topicName);
                            startActivity(i);
                            break;
                    }
                    return true;
                });

        arrowBack.setOnClickListener(view -> onBackPressed());
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

    public void writeToRoom(String id, String uAnswer) {
        if (roomDatabase != null) {
            mSubscriptions.add(writeToRoomRx(uAnswer, id));
        }
    }

    public boolean checkRoom() {
        if (roomDatabase != null) {
            List<Test> foundT = roomDatabase.testDao().findTestById(testId);
            if (foundT.size() > 0) {
                return foundT.get(0).isCheckedTest();
            }
        }
        return false;
    }
    public void insertTestRoomRx(Test test) {
        mSubscriptions.add(addTToRoomRx(test));
    }


    public void insertQuestionRoomRx (Question question) {
        mSubscriptions.add(addQToRoomRx(question));
    }

    public void readFromRoom (String id, int position) {
        progress.setVisibility(View.VISIBLE);
        mSubscriptions.add(readAnswerWithRoomRx(id, position));

    }

    public void  updateCheckedRoom (Boolean value, String id) {
        mSubscriptions.add(updateCheckedRoomRx(value, id));
    }

    public void  updateCorrectTestRoom (Boolean value, String id) {
        mSubscriptions.add(updateCorrectRoomRx(value, id));
    }


    public void deleteFromRoom (String id) {
        mSubscriptions.add(deleteFromRoomRx(id));
    }

    public Disposable updateCorrectRoomRx (Boolean value, String id) {
        return Completable.fromAction(() -> roomDatabase.testDao().updateCorrectTest(value, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe();
    }

    public Disposable updateCheckedRoomRx (Boolean value, String id) {
        return Completable.fromAction(() -> roomDatabase.questionDao().updateChecked(value, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe();
    }

    public Disposable deleteFromRoomRx (String id) {
        return Completable.fromAction(() -> roomDatabase.questionDao().updateQuestion(null, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe();
    }

    public Disposable writeToRoomRx(String uAnswer, String id) {
        return Completable.fromAction(() -> roomDatabase.questionDao().updateQuestion(uAnswer, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe();
    }


    public Disposable addTToRoomRx(Test test) {
        return Completable.fromAction(() -> roomDatabase.testDao().insertTest(test))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .doOnComplete(() -> Log.i("RXJAVA", "test added"))
                .subscribe();
    }

    public Disposable addQToRoomRx(Question question) {
        return Completable.fromAction(() -> roomDatabase.questionDao().insertQuestion(question))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .doOnComplete(() -> Log.i("RXJAVA", "q added"))
                .subscribe();
    }

    public Disposable readAnswerWithRoomRx(String id, int position) {
       return Single.fromCallable (() -> roomDatabase.questionDao().findQuestionById(id))
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       res -> {
                           singleTestAdapter.setUAnswerFromRoom(res.get(0).getuAnswer(), position);
                           progress.setVisibility(View.GONE);
                       },
                       throwable ->onError(throwable.getMessage())
               );
    }

    public void onError(String message) {
        Log.e("rxJava failed", message);
    }

}
