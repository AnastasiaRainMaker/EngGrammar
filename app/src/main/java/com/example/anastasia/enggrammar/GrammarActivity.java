package com.example.anastasia.enggrammar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.anastasia.enggrammar.adapters.GrammarAdapter;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import java.util.ArrayList;
import java.util.List;

public class GrammarActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    View headerView;
    ImageView menuToolbar;
    ImageView arrowClose;
    ImageView exitImage;
    RecyclerView mRecycler;
    Animator sAnimator;
    LinearLayout drawerRow1;
    LinearLayout drawerRow2;
    LinearLayout drawerRow3;
    List<String> topicList = new ArrayList<>();
    GrammarAdapter grammarAdapter;
    TextView menuGrammar;
    TextView menuTests;
    TextView menuAbout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuToolbar = findViewById(R.id.menu_toolbar);
        arrowClose = findViewById(R.id.img_drawer_close);
        exitImage = findViewById(R.id.exit_drawer);
        menuGrammar = findViewById(R.id.menu_grammar);
        menuTests = findViewById(R.id.menu_tests);
        menuAbout = findViewById(R.id.menu_about);
        mRecycler = findViewById(R.id.recycler_grammar);
        grammarAdapter = new GrammarAdapter(topicList);
        RecyclerView.LayoutManager mRecyclerManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mRecyclerManager);
        mRecycler.setAdapter(grammarAdapter);
        setUpViews();
        prepareTopics();
    }

    private void prepareTopics() {
        ArrayList<String> options = new ArrayList<>();
        options.add("");
        Topic topic = new Topic("Present Simple", " ", " ", " ", " ", options);
        topicList.add(topic.getName());
        topic = new Topic("Present Continuous", " ", " ", " ", " ", options);
        topicList.add(topic.getName());
        topic = new Topic("Past Simple", " ", " ", " ", " ", options);
        topicList.add(topic.getName());
        topic = new Topic("Future Simple", " ", " ", " ", " ", options);
        topicList.add(topic.getName());
        topic = new Topic("Present Perfect", " ", " ", " ", " ", options);
        topicList.add(topic.getName());
        topic = new Topic("Past Perfect", " ", " ", " ", " ", options);
        topicList.add(topic.getName());
        topic = new Topic("Future Perfect", " ", " ", " ", " ", options);
        topicList.add(topic.getName());
        topic = new Topic("Conditionals", " ", " ", " ", " ", options);
        topicList.add(topic.getName());

    }

    private void initSpruce() {
        drawerRow1 = findViewById(R.id.drawer_row1);
        drawerRow2 = findViewById(R.id.drawer_row2);
        drawerRow3 = findViewById(R.id.drawer_row3);
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
        mRecycler.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        exitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GrammarActivity.this.finish();
            }
        });
        arrowClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
         final NavigationView navigationView = findViewById(R.id.nav_view);
         navigationView.setVerticalFadingEdgeEnabled(false);
         navigationView.setVerticalScrollBarEnabled(false);
         headerView = navigationView.getHeaderView(0);
         // set drawer size
         ViewTreeObserver vto = navigationView.getViewTreeObserver();
         // When the layout has been draw
         vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
             @Override
             public void onGlobalLayout() {
                 // Remove this listener
                 navigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                 // Get the calculated width of the drawer
                 int width = drawerLayout.getMeasuredWidth();
                 // Set header's height using aspectio ratio
                 navigationView.getLayoutParams().width = (int) (width * 0.85);
                 // Update the layout on screen
                 navigationView.requestLayout();
             }
         });
         menuToolbar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
                initSpruce();
             }
         });
         menuGrammar.setTextColor(getResources().getColorStateList(R.color.text_menu_selector));
         menuTests.setTextColor(getResources().getColorStateList(R.color.text_menu_selector));
         menuAbout.setTextColor(getResources().getColorStateList(R.color.text_menu_selector));
         menuGrammar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(), GrammarActivity.class);
                 startActivity(i);
             }
         });
         menuTests.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

             }
         });
         menuAbout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                 startActivity(i);
             }
         });

     }
}
