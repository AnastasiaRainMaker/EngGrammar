package com.example.anastasia.enggrammar.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anastasia.enggrammar.R;
import com.example.anastasia.enggrammar.SingleTestActivity;
import com.example.anastasia.enggrammar.TopicGrActivity;
import com.example.anastasia.enggrammar.TopicTestsList;

import java.util.List;

/**
 * Created by anastasia on 12/25/17.
 */

public class TopicTestListAdapter extends RecyclerView.Adapter<TopicTestListAdapter.MyViewHolder> {

    private List<String> testList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.topic_test_item);
        }
    }


    public TopicTestListAdapter(List<String> testList) {
        this.testList = testList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_test_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String topic = testList.get(position);
        holder.name.setText(topic);
        holder.name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(context, SingleTestActivity.class);
                i.putExtra("testNumber", topic);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }
}
