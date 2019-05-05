package com.open.androidtvwidget.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FocusListView extends ListView implements AbsListView.OnScrollListener{

	/**
     * 显示在屏幕上多少个item
     */
    private int showItemCount=6;
    /**
     * 每个item的高度
     */
    private int itemHeight;
    /**
     * 记录第一个显示的item
     */
    /**
     * 从第几行执行动画
     */
    private int scaleFlagIndex = 1;
    /**
     * 是否到了最后一行
     */
    private boolean lastFlag = false;

    /**
     * 步骤
     */
    private int step = 0;

    /**
     * 步骤个数
     */
    private int stepCount;

    public FocusListView(Context context) {
        this(context, null);
    }

    public FocusListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造方法中拿到自定义属性showViewCount，并设置滚动监听
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public FocusListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取showViewCount
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LvListView);
//        showItemCount = typedArray.getInt(R.styleable.LvListView_showViewCount, 0);
//        typedArray.recycle();

        //设置一个滚动监听
        setOnScrollListener(this);
    }

    /**
     * 重写onLayout方法得到LvListview尺寸信息，并根据要显示的item个数计算出item高度并设置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            itemHeight = this.getHeight() / showItemCount;
            for (int i = 0; i < getChildCount(); i++) {
                LinearLayout layout = (LinearLayout) getChildAt(i);
                AbsListView.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
                layout.setLayoutParams(layoutParams);
            }
            stepCount = getAdapter().getCount() - 2;
        }
    }

    /**
     * 禁用手指滑动
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 设置滚动到指定item
     */
    public void scrollToItem() {
        step++;
        if (step == stepCount) {
            step = 0;
            this.lastFlag = true;
            startScaleAnimator(getChildAt(scaleFlagIndex), 1.0f, 0.6f);
            this.smoothScrollToPositionFromTop(step, 0, 1000);
        } else {
            this.smoothScrollToPositionFromTop(step, 0, 500);
        }
    }

    /**
     * 缩放动画
     *
     * @param view
     * @param start
     * @param end
     */
    public void startScaleAnimator(final View view, float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(500);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = Float.parseFloat((String) animation.getAnimatedValue());
                view.setScaleX(value);
                view.setScaleY(0.4f + (0.6f * value));
            }
        });
    }

    @Override
         public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.e("onScrollStateChanged", "scrollState--" + scrollState);
        if (scrollState == OnScrollListener.SCROLL_STATE_FLING) {
            if (!lastFlag) {
                startScaleAnimator(view.getChildAt(scaleFlagIndex), 1.0f, 0.6f);
                startScaleAnimator(view.getChildAt(scaleFlagIndex + 1), 0.6f, 1.0f);
                scaleFlagIndex = 2;
            }
        } else if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastFlag) {
            this.lastFlag = false;
            //this.scaleFlag = true;
            scaleFlagIndex = 1;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.e("onScroll", "firstVisibleItem--" + firstVisibleItem);
    }



	
}
