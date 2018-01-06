package com.example.anastasia.enggrammar.adapters;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anastasia.enggrammar.POJO.Question;
import com.example.anastasia.enggrammar.R;
import com.example.anastasia.enggrammar.SingleTestActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by anastasia on 12/25/17.
 */

public class SingleTestAdapter extends RecyclerView.Adapter<SingleTestAdapter.MyViewHolder> {

    public ArrayList<String> optionList = new ArrayList<>();
    public ArrayList<String> rAnswerList = new ArrayList<>();
    public String[] uAnswerList;
    public List<Question> questionList;
    SingleTestActivity mActivity = new SingleTestActivity();
    public ArrayList<Boolean> isCorrect = new ArrayList<>();
    public ColorStateList mList;
    public int count1;
    public int count2;
    public View.OnClickListener onClickListener;
    int size;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView questionView;
        public RadioGroup radioGroup;

        public MyViewHolder(View view) {
            super(view);
            questionView = view.findViewById(R.id.question);
            radioGroup = view.findViewById(R.id.rgroup);
            mList = mActivity.setColorStateList();

        }
    }


    public SingleTestAdapter(SingleTestActivity mActivity, List<Question> questionList) {
        this.questionList = questionList;
        this.mActivity = mActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_test_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Question question = questionList.get(position);
        final HashMap<String, String> map = questionList.get(position).getOptions();
        holder.questionView.setText(question.getText());
        rAnswerList.add(question.getAnswer());

        for (Map.Entry entry : map.entrySet()) {
             optionList.add(entry.getValue().toString());

        }
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton) view;
                radioButton.setChecked(true);
                uAnswerList[position] = radioButton.getText().toString();
            }
        };

        for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
            holder.radioGroup.getChildAt(i).setClickable(true);
            ((RadioButton) holder.radioGroup.getChildAt(i)).setText(optionList.get(i));
            holder.radioGroup.getChildAt(i).setOnClickListener(onClickListener);
        }

        optionList.clear();

        if (questionList.get(position).getCleared()) {
            holder.radioGroup.clearCheck();
            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                ((RadioButton) holder.radioGroup.getChildAt(i)).setButtonTintList(mList);
            }
        }

        if (questionList.get(position).getChecked()) {
             for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                   if ( ((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(rAnswerList.get(position))
                           && !Objects.equals(uAnswerList[position], rAnswerList.get(position))){

                       RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                       nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.colorAccent)));
                   }
                    if ( ((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(rAnswerList.get(position))
                        && Objects.equals(uAnswerList[position], rAnswerList.get(position))){

                        RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                        nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.colorAccent)));
                        nBtn.setChecked(true);
                    }
                   if (((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(uAnswerList[position])
                           && !Objects.equals(uAnswerList[position], rAnswerList.get(position))){
                       RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                       nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.red)));
                       nBtn.setChecked(true);
                   }
             }

            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                holder.radioGroup.getChildAt(i).setClickable(false);
            }
        }
    }

    public void displayTestResult() {
        if (!isCorrect.contains(false) && isCorrect.size() == questionList.size()) {
            Toast.makeText(mActivity, "Все ответы правильные", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mActivity, "В тесте " + count2 + " правильных ответов и " + count1 + " неправильных", Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkTest() {
        isCorrect.clear();
        if (Arrays.asList(uAnswerList).contains(null) || uAnswerList == null) {
            setCleared(false);
            Toast.makeText(mActivity, "Необходимо ответить на все вопросы", Toast.LENGTH_SHORT).show();
            setChecked(false);
            mActivity.setIsChecked(false);
        return false;
        } else {
            mActivity.setIsChecked(true);
            for (int i = 0; i < uAnswerList.length; i++) {
                if (!rAnswerList.get(i).equals(uAnswerList[i])) {
                    if(isCorrect.size() > i) {
                        isCorrect.set(i, false);
                    } else {
                        isCorrect.add(false);
                    }
                } else {
                    if(isCorrect.size() > i) {
                        isCorrect.set(i, true);
                    } else {
                        isCorrect.add(true);
                    }
                }
            } return true;
        }
    }
    public void setChecked(boolean value) {
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            q.setChecked(value);
        }
    }

    public void setCleared(boolean value) {
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            q.setCleared(value);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public void  clearUserAnswer() {
        if (uAnswerList != null)
        uAnswerList = new String[questionList.size()];
    }

    public void setuAnswerListSize(int size) {
       uAnswerList = new String[size];
    }
}
