package com.gc.basicpublicdoctor.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;

public class SignInfoAddLayout extends RelativeLayout {
    Context context;
    TextView textViewTitle;
    TextView textViewAdd;
    TextView textViewTotalPriceTitle;
    TextView textViewTotalPrice;
    TextView textViewRead;

    public SignInfoAddLayout(Context context) {
        this(context, null);
    }

    public SignInfoAddLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignInfoAddLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.sign_info_add_layout, null);
        textViewTitle = view.findViewById(R.id.txt_title);
        textViewAdd = view.findViewById(R.id.txt_add);
        textViewTotalPriceTitle = view.findViewById(R.id.txt_total_price_title);
        textViewTotalPrice = view.findViewById(R.id.txt_total_price);
        textViewRead = view.findViewById(R.id.txt_read);
        this.addView(view);
    }

    public void setTitle(String name) {
        textViewTitle.setText(name);
    }

    public void setBtnName(String name) {
        textViewAdd.setText(name);
    }

    public void setBtnBg(int resource, int color) {
        textViewAdd.setBackgroundResource(resource);
        textViewAdd.setTextColor(color);
    }

    public String getBtnName() {
        return textViewAdd.getText().toString();
    }

    public void setAddOnClickListener(OnClickListener onClickListener) {
        textViewAdd.setOnClickListener(onClickListener);
    }

    public void setPrice(String value) {
        if (TextUtils.isEmpty(value)) {
            value = "";
        }
        textViewTotalPriceTitle.setVisibility(VISIBLE);
        textViewTotalPrice.setVisibility(VISIBLE);
        textViewTotalPrice.setText("￥" + value);
    }

    public void setBtnGone() {
        textViewAdd.setVisibility(GONE);
    }

    public void setDetailsGone(){
        textViewRead.setVisibility(GONE);
    }
    public void setTextViewReadShow(OnClickListener clickListener) {
        textViewRead.setVisibility(VISIBLE);
        textViewRead.setOnClickListener(clickListener);
    }
}
