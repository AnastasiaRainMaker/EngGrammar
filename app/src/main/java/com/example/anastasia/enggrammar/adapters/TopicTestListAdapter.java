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
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        MyViewHolder(View view) {
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String testNumber = testList.get(position);
        holder.name.setText(testNumber);
        holder.name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}
