package com.gc.basicpublicdoctor.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;

public class ClassificationItemLayout extends LinearLayout {

    TextView textViewName;
    ImageView imageView;

    public ClassificationItemLayout(Context context) {
        this(context, null);
    }

    public ClassificationItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassificationItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.classification_item_layout, this, false);
        textViewName = (TextView) view.findViewById(R.id.tv_normal_crowd);
        imageView = (ImageView) view.findViewById(R.id.iv_normal_crowd_corner);
        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewName.isSelected()) {
                    textViewName.setSelected(false);
                    imageView.setVisibility(View.GONE);
                    if (onCheck != null) {
                        onCheck.onCheck(textViewName.getText().toString(), false);
                    }
                } else {
                    textViewName.setSelected(true);
                    imageView.setVisibility(View.VISIBLE);
                    if (onCheck != null) {
                        onCheck.onCheck(textViewName.getText().toString(), true);
                    }
                }
            }
        });
        this.addView(view);
        this.setGravity(Gravity.CENTER);
    }

    public void setTitleName(String name) {
        textViewName.setText(name);
    }

    public String getTitleName() {
        return textViewName.getText().toString();
    }

    public void setSelect(boolean flag) {
        textViewName.setSelected(flag);
        if (onCheck != null) {
            onCheck.onCheck(textViewName.getText().toString(), flag);
        }
        if (flag) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    public void setSelectEnable(boolean flag) {
        if (flag) {
            textViewName.setClickable(true);
            ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.crowd_list_screen_ttcolor_selector);
            textViewName.setTextColor(csl);
        } else {
            textViewName.setClickable(false);
            textViewName.setTextColor(Color.parseColor("#A8A8A8"));
        }
    }

    public boolean isSelect() {
        return textViewName.isSelected();
    }

    public void setOnCheck(OnItemCheck onCheck) {
        this.onCheck = onCheck;
    }

    OnItemCheck onCheck;

    public interface OnItemCheck {
        void onCheck(String name, boolean isCheck);
    }


}
