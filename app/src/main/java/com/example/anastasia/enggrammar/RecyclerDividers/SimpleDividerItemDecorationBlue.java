package com.example.anastasia.enggrammar.RecyclerDividers;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.anastasia.enggrammar.R;

/**
 * Created by anastasia on 12/24/17.
 */

public class SimpleDividerItemDecorationBlue extends RecyclerView.ItemDecoration{
    private Drawable mDivider;

    public SimpleDividerItemDecorationBlue(Resources resources) {
        mDivider = resources.getDrawable(R.drawable.line_divider_blue);
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}