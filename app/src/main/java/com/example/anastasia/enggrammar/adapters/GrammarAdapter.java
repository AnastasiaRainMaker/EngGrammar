package com.example.anastasia.enggrammar.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anastasia.enggrammar.R;
import com.example.anastasia.enggrammar.Topic;

import java.util.List;

/**
 * Created by anastasia on 12/25/17.
 */

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.MyViewHolder> {

    private List<String> topicList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.topic);
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
        String topic = topicList.get(position);
        holder.name.setText(topic);
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }
}

