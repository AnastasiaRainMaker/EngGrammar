package com.example.anastasia.enggrammar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.anastasia.enggrammar.POJO.Topic;
import com.example.anastasia.enggrammar.recyclerDividers.SimpleDividerItemDecorationWhite;
import com.example.anastasia.enggrammar.adapters.TestsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import java.util.ArrayList;
import java.util.List;

public class TestsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView menuToolbar;
    private ImageView arrowClose;
    private RecyclerView mRecycler;
    private List<String> testList = new ArrayList<>();
    private TestsAdapter testsAdapter;
    private TextView menuGrammar;
    private TextView menuTests;
    private TextView menuAbout;
    private DatabaseReference mDatabase;
    private ValueEventListener postListener;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        initViews();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("topics");
        testsAdapter = new TestsAdapter(testList);
        RecyclerView.LayoutManager mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(testsAdapter);
        setUpViews();
        prepareTopics();
    }

    public void initViews() {
        progressBar = findViewById(R.id.progress_tests);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuToolbar = findViewById(R.id.menu_toolbar2);
        arrowClose = findViewById(R.id.img_drawer_close);
        menuGrammar = findViewById(R.id.menu_grammar);
        menuTests = findViewById(R.id.menu_tests);
        menuAbout = findViewById(R.id.menu_about);
        mRecycler = findViewById(R.id.recycler_tests);
    }

    private void prepareTopics() {
        progressBar.setVisibility(View.VISIBLE);
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Topic topic = data.getValue(Topic.class);
                    if (topic != null) {
                        testList.add(topic.getName());
                    }
                }
                testsAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Constants.TAG, "loadPost:onCancelled", databaseError.toException());//todo

            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    private void initSpruce() {
        Animator sAnimator;
        LinearLayout drawerRow1 = findViewById(R.id.drawer_row1);
        LinearLayout drawerRow2 = findViewById(R.id.drawer_row2);
        LinearLayout drawerRow3 = findViewById(R.id.drawer_row3);
        sAnimator = new Spruce.SpruceBuilder(drawerRow1)
                .sortWith(new DefaultSort(0))
                .animateWith(DefaultAnimations.fadeInAnimator(drawerRow1, 800),
                        ObjectAnimator.ofFloat(drawerRow1, "translationX", -drawerRow1.getWidth(), 0f).setDuration(800))
                .start();
        sAnimator = new Spruce.SpruceBuilder(drawerRow2)
                .sortWith(new DefaultSort(0))
                .animateWith(DefaultAnimations.fadeInAnimator(drawerRow1, 900),
                        ObjectAnimator.ofFloat(drawerRow2, "translationX", -drawerRow2.getWidth(), 0f).setDuration(900))
                .start();
        sAnimator = new Spruce.SpruceBuilder(drawerRow3)
                .sortWith(new DefaultSort(0))
                .animateWith(DefaultAnimations.fadeInAnimator(drawerRow1, 1000),
                        ObjectAnimator.ofFloat(drawerRow3, "translationX", -drawerRow3.getWidth(), 0f).setDuration(1000))
                .start();
    }

    protected void setUpViews(){
        mRecycler.addItemDecoration(new SimpleDividerItemDecorationWhite(getResources()));
        arrowClose.setOnClickListener(view -> drawerLayout.closeDrawer(GravityCompat.START));
        final NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setVerticalFadingEdgeEnabled(false);
        navigationView.setVerticalScrollBarEnabled(false);
        ViewTreeObserver vto = navigationView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                navigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = drawerLayout.getMeasuredWidth();
                navigationView.getLayoutParams().width = (int) (width * 0.85);
                navigationView.requestLayout();
            }
        });
        menuToolbar.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
            initSpruce();
        });
        menuGrammar.setTextColor(getResources().getColorStateList(R.color.text_menu_selector));
        menuTests.setTextColor(getResources().getColorStateList(R.color.text_menu_selector));
        menuAbout.setTextColor(getResources().getColorStateList(R.color.text_menu_selector));
        menuGrammar.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), GrammarActivity.class);
            startActivity(i);
        });
        menuAbout.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(i);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (postListener != null) {
            mDatabase.removeEventListener(postListener);
        }
    }
}
