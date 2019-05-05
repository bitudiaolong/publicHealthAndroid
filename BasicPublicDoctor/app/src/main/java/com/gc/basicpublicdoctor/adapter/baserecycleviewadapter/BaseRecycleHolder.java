package com.gc.basicpublicdoctor.adapter.baserecycleviewadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.squareup.picasso.Picasso;


/**
 * Author:Created by zhurui
 * Time:2018/7/17 上午9:02
 * Description:This is BaseRecycleHolder
 */
public class BaseRecycleHolder extends RecyclerView.ViewHolder {

    private Context context;
    private SparseArray<View> mViews;   // 弱引用,内存不足时自动释放

    public BaseRecycleHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.mViews = new SparseArray<View>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }


    public BaseRecycleHolder setImageViewURI(int viewId, String url, int resId) {
        ImageView iv = getView(viewId);
        setImageViewURI(iv, url, resId);
        return this;
    }

    public BaseRecycleHolder setImageViewURI(int viewId, String url, int resId, int defRes) {
        ImageView iv = getView(viewId);
        setImageViewURI(iv, url, resId, defRes);
        return this;
    }

    public BaseRecycleHolder setImageViewURI(ImageView iv, String url, int resId, int defRes) {
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,resId);
//        new ImageViewUtils(context).setImageViewURL(iv,url,resId);
        if (resId == 0) {
            resId = R.color.transparent;
        }
        if (TextUtils.isEmpty(url) == false) {
            Picasso.with(context).load(url)
                    .placeholder(resId)
                    .error(defRes)
                    .into(iv);
        }
        return this;
    }

    public BaseRecycleHolder setImageViewURI(ImageView iv, String url, int resId) {
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,resId);
//        new ImageViewUtils(context).setImageViewURL(iv,url,resId);
        if (TextUtils.isEmpty(url) == false) {
            Picasso.with(context).load(url)
                    .placeholder(R.color.transparent)
                    .error(resId)
                    .into(iv);
        }
        return this;
    }

    public BaseRecycleHolder setImageViewResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        setImageViewResource(iv, resId);
        return this;
    }

    public BaseRecycleHolder setImageViewResource(ImageView iv, int resId) {
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,drawable);
//        new ImageViewUtils(context).setImageViewURL(iv,url,drawable);
        iv.setImageResource(resId);
        return this;
    }

    public BaseRecycleHolder setImageViewURI(int viewId, String url, Drawable drawable) {
        ImageView iv = getView(viewId);
        setImageViewURI(iv, url, drawable);
        return this;
    }

    public BaseRecycleHolder setImageViewURI(ImageView iv, String url, Drawable drawable) {
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,drawable);
//        new ImageViewUtils(context).setImageViewURL(iv,url,drawable);
        return this;
    }

    public BaseRecycleHolder setImageViewBitmap(int viewId, Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    public BaseRecycleHolder setTextViewBackground(int viewId, int id) {
        ((TextView) getView(viewId)).setBackgroundResource(id);
        return this;
    }

    public BaseRecycleHolder setTextViewText(int viewId, String txt) {
        TextView tv = getView(viewId);
        tv.setText(txt);
        return this;
    }

    public BaseRecycleHolder setViewTag(int viewId, Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    public BaseRecycleHolder setVisable(int viewId, int visible) {
        getView(viewId).setVisibility(visible);
        return this;
    }

    public BaseRecycleHolder setTextViewTextColor(int viewId, int id) {
        ((TextView) getView(viewId)).setTextColor(context.getResources().getColor(id));
        return this;
    }

    public BaseRecycleHolder setTextViewTextColor(int viewId, String colorVar) throws Exception {
        ((TextView) getView(viewId)).setTextColor(Color.parseColor(colorVar));
        return this;
    }

    public BaseRecycleHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    public BaseRecycleHolder setOnCheckListener(int viewId, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        CheckBox checkBox = getView(viewId);
        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }

    public BaseRecycleHolder setCheckBox(int viewId, boolean isCheck) {
        CheckBox checkBox = getView(viewId);
        checkBox.setChecked(isCheck);
        return this;
    }

}
