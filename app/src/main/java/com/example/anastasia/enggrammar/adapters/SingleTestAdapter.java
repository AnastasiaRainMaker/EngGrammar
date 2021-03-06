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

    private ArrayList<String> optionList = new ArrayList<>();
    private ArrayList<String> rAnswerList = new ArrayList<>();
    private String[] uAnswerList;
    private List<Question> questionList;
    private SingleTestActivity mActivity = new SingleTestActivity();
    private ArrayList<Boolean> isCorrect = new ArrayList<>();
    private ColorStateList mList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionView;
        RadioGroup radioGroup;

        MyViewHolder(View view) {
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
        if (rAnswerList.size() != questionList.size()) {
            rAnswerList.add(question.getAnswer());
        }

        for (Map.Entry entry : map.entrySet()) {
             optionList.add(entry.getValue().toString());
        }

        for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
            holder.radioGroup.getChildAt(i).setClickable(true);
            ((RadioButton) holder.radioGroup.getChildAt(i)).setText(optionList.get(i));

            holder.radioGroup.getChildAt(i).setOnClickListener(view -> {
                RadioButton radioButton = (RadioButton) view;
                radioButton.setChecked(true);
                uAnswerList[position] = radioButton.getText().toString();
                if (uAnswerList[position] != null) {
                    mActivity.writeToRoom(questionList.get(position).getId(), uAnswerList[position]);
                }
            });
        }

        optionList.clear();

        if (questionList.get(position).getIsCleared() || !mActivity.checkRoom()) {
            holder.radioGroup.clearCheck();
            mActivity.deleteFromRoom (questionList.get(position).getId());
            mActivity.updateCheckedRoom(false, questionList.get(position).getId());
            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                ((RadioButton) holder.radioGroup.getChildAt(i)).setButtonTintList(mList);
            }
        }

        if (mActivity.checkRoom()) {
            mActivity.setIsChecked(true);
            questionList.get(position).setChecked(true);
            if (uAnswerList[position] == null)
            mActivity.readFromRoom(questionList.get(position).getId(), position);

        }

        if (questionList.get(position).getChecked()) {
            if (!mActivity.checkRoom()) {
                mActivity.updateCheckedRoom(true, questionList.get(position).getId());
            }
            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                    if (!((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(rAnswerList.get(position))
                            && !((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(uAnswerList[position])) {

                        RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                        nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.colorPrimary)));
                    }
                    if (((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(rAnswerList.get(position))
                            && !Objects.equals(uAnswerList[position], rAnswerList.get(position))) {

                        RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                        nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.colorAccent)));
                    }
                    if (((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(rAnswerList.get(position))
                            && Objects.equals(uAnswerList[position], rAnswerList.get(position))) {

                        RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                        nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.colorAccent)));
                        nBtn.setChecked(true);
                    }
                    if (((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(uAnswerList[position])
                            && !Objects.equals(uAnswerList[position], rAnswerList.get(position))) {
                        RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                        nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.red)));
                        nBtn.setChecked(true);
                    }
            }
            disableRadioG(holder); // todo
        }
    }

    public boolean getTestResult() {
        if (!isCorrect.contains(false) && isCorrect.size() == questionList.size()) {
            Toast.makeText(mActivity, mActivity.getString(R.string.all_answers_are_correct_str), Toast.LENGTH_SHORT).show();
            return true;
        } else if (isCorrect.contains(false) && isCorrect.size() == questionList.size()) {
            return false;
        }
        return false;
    }

    public boolean checkTest() {
        isCorrect.clear();
        if (uAnswerList == null || Arrays.asList(uAnswerList).contains(null)) {
            setCleared();
            Toast.makeText(mActivity, mActivity.getString(R.string.need_to_answer_str), Toast.LENGTH_SHORT).show();  // todo
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

    private void setCleared() {
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            q.setCleared();
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

    public void setUserAnswerListSize(int size) {
       uAnswerList = new String[size];
    }  // todo

    private void disableRadioG(MyViewHolder holder) {
        for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
            holder.radioGroup.getChildAt(i).setClickable(false);
        }
    }

    public void setUAnswerFromRoom(String uAnswer, int position){
        uAnswerList[position] = uAnswer;
        notifyItemChanged(position);

    }
}
