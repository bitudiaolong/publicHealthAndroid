package com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recycleview绘制分割线
 * Created by Administrator on 2017/11/15/015.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;
    boolean flags = false;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            if (flags) {
                outRect.top = 0;
            } else {
                outRect.top = mSpace;
            }
        }

    }

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }

    public SpaceItemDecoration(int space, boolean flags) {
        this.flags = flags;
        this.mSpace = space;
    }
}
