package com.example.anastasia.enggrammar.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anastasia.enggrammar.R;
import com.example.anastasia.enggrammar.TestsActivity;
import com.example.anastasia.enggrammar.TopicTestsList;

import java.util.List;

/**
 * Created by anastasia on 12/25/17.
 */

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.MyViewHolder> {

    private List<String> testsList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public LinearLayout mLayout;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.test);
            mLayout = view.findViewById(R.id.tests_row);
           }
    }


    public TestsAdapter(List<String> topicList) {
        this.testsList = topicList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_row, parent, false);
           return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String test = testsList.get(position);
        holder.name.setText(test);
        holder.mLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(context, TopicTestsList.class);
                i.putExtra("testName", test);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testsList.size();
    }
}

