package com.example.anastasia.enggrammar.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.anastasia.enggrammar.POJO.Test;
import com.example.anastasia.enggrammar.R;
import com.example.anastasia.enggrammar.TopicTestsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasia on 12/25/17.
 */

public class TopicTestListAdapter extends RecyclerView.Adapter<TopicTestListAdapter.MyViewHolder> {

    private List<Test> testList;
    private OnItemClicked onClick;
    private TopicTestsList mActivity;
    private Boolean[] testChecked;

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

    public TopicTestListAdapter(TopicTestsList mActivity, List<Test> testList) {
        this.mActivity = mActivity;
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
        final String testNumber = testList.get(position).getName();
        if (testChecked[position] == null) {
            mActivity.isTestChecked(testList.get(position).getId(), position);
        } else {
            if (testChecked[position]) {
                holder.name.setTextColor(mActivity.getResources().getColor(R.color.colorAccent));
            } else {
                holder.name.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            }
           mActivity.hideProgress(position);
        }
        holder.name.setText(testNumber);
        holder.name.setOnClickListener(view -> onClick.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }

    public void setTestCheckedSize(int size) {
        testChecked = new Boolean[size];
    }

    public  void setTestChecked(Boolean value, int position) {
        testChecked[position] = value;
    }

    public void clearTestChecked() {
        if (testChecked != null) {
            for (int i = 0; i < testChecked.length; i++) {
                testChecked[i] = null;
            }
            notifyDataSetChanged();
        }
    }
}
