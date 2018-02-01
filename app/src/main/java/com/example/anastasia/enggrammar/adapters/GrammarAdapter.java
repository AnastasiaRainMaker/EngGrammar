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
import com.example.anastasia.enggrammar.TopicGrActivity;

import java.util.List;

/**
 * Created by anastasia on 12/25/17.
 */

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.MyViewHolder> {

    private List<String> topicList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public LinearLayout mLayout;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.topic);
            mLayout = view.findViewById(R.id.topic_row);
        }

    }


    public GrammarAdapter(List<String> topicList) {
        this.topicList = topicList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String topic = topicList.get(position);
        holder.name.setText(topic);
        holder.mLayout.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent i = new Intent(context, TopicGrActivity.class);
            i.putExtra("topicName", topic);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }
}

