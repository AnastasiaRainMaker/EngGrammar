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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anastasia on 12/25/17.
 */

public class SingleTestAdapter extends RecyclerView.Adapter<SingleTestAdapter.MyViewHolder> {

    public ArrayList<String> optionList = new ArrayList<>();
    public ArrayList<String> rAnswerList = new ArrayList<>();
    public ArrayList<String> uAnswerList = new ArrayList<>();
    public List<Question> questionList;
    public Boolean isCleared = false;
    SingleTestActivity mActivity = new SingleTestActivity();
    public ArrayList<Boolean> isCorrect = new ArrayList<>();
    public boolean isChecked = false;
    public ColorStateList mList;


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


    public SingleTestAdapter(SingleTestActivity mActivity, List<Question> questionList, boolean isCleared) {
        this.questionList = questionList;
        this.isCleared = isCleared;
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

        for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
            holder.radioGroup.getChildAt(i).setEnabled(true);
            ((RadioButton) holder.radioGroup.getChildAt(i)).setText(optionList.get(i));
        }

        if (isCleared) {
            holder.radioGroup.clearCheck();
            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                ((RadioButton) holder.radioGroup.getChildAt(i)).setButtonTintList(mList);
            }
        }

        optionList.clear();
         holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (!isCleared) {
                        RadioButton radioButton = radioGroup.findViewById(i);
                        if (radioButton != null) {
                            if (uAnswerList.size() > position) {
                                uAnswerList.set(position, radioButton.getText().toString());
                            } else {
                                uAnswerList.add(radioButton.getText().toString());
                            }
                        }
                    }
                }
            });

        if (isChecked) {
            if (!isCorrect.get(position)) {
                int selectedId = holder.radioGroup.getCheckedRadioButtonId();
                RadioButton rButton = holder.radioGroup.findViewById(selectedId);
                rButton.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.red)));
                for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                   if ( ((RadioButton) holder.radioGroup.getChildAt(i)).getText().equals(rAnswerList.get(position))){
                       RadioButton nBtn = (RadioButton) holder.radioGroup.getChildAt(i);
                       nBtn.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.colorAccent)));
                   }
                }
            } else {
                int selectedId = holder.radioGroup.getCheckedRadioButtonId();
                RadioButton rButton = holder.radioGroup.findViewById(selectedId);
                rButton.setButtonTintList(ColorStateList.valueOf(mActivity.getResources().getColor(R.color.colorAccent)));
            }
            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                holder.radioGroup.getChildAt(i).setEnabled(false);
            }
        }
        if (position == questionList.size()-1) {
            isCleared = false;
        }

    }

    public void checkTest() {
        if (uAnswerList.size() != questionList.size()) {
            Toast.makeText(mActivity, "Необходимо ответить на все вопросы", Toast.LENGTH_SHORT).show();
        } else {
            isChecked = true;
            for (int i = 0; i < uAnswerList.size(); i++) {
                if (!rAnswerList.get(i).equals(uAnswerList.get(i))) {
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
            }
        }
    }
    public void setIsChecked(boolean value) {
        isChecked = value;
    }

    public void setIsCleared(boolean value) {
        isCleared = value;
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public void  clearUserAnswer() {
        if (uAnswerList != null)
        uAnswerList.clear();
    }

}
