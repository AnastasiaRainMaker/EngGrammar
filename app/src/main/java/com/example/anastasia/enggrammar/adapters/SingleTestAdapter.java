package com.example.anastasia.enggrammar.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.anastasia.enggrammar.R;

import java.util.List;

/**
 * Created by anastasia on 12/25/17.
 */

public class SingleTestAdapter extends RecyclerView.Adapter<SingleTestAdapter.MyViewHolder> {

    public List<String> optionList;
    public List<String> questionList;
    public Boolean isCleared;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView questionView;
        public RadioGroup radioGroup;

        public MyViewHolder(View view) {
            super(view);
            questionView = view.findViewById(R.id.question);
            radioGroup = view.findViewById(R.id.rgroup);
        }
    }


    public SingleTestAdapter(List<String> optionList, List<String> questionList, boolean isCleared) {
        this.optionList = optionList;
        this.questionList = questionList;
        this.isCleared = isCleared;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_test_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String question = questionList.get(position);
        final String options = optionList.get(position);
        holder.questionView.setText(question);
        String[] mOptions = options.split(" ");
        for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
            ((RadioButton) holder.radioGroup.getChildAt(i)).setText(mOptions[i]);
        }
        if(isCleared) {
            holder.radioGroup.clearCheck();
        }
    }
    public void setIsCleared(boolean value) {
        isCleared = value;
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
