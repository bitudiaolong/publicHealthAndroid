package com.gc.basicpublicdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.bean.NewsRecommendListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/17 上午9:37
 * Description:This is NewsRecommandListAdapter
 * 首页新闻列表适配器
 */
public class NewsRecommandListAdapter extends RecyclerView.Adapter<NewsRecommandListAdapter.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<NewsRecommendListResponse.DataBean.ListBean> lstNews;

    public NewsRecommandListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        lstNews = new ArrayList<>();
    }

    public NewsRecommandListAdapter(Context context, List<NewsRecommendListResponse.DataBean.ListBean> lstNews) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lstNews = lstNews;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_news_recommand_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        View itemView = ((LinearLayout) holder.itemView);

        List<String> imgList = lstNews.get(position).getImageList();
        int imgListSize = imgList.size();

        if (imgListSize > 1) {
            holder.ll_type3.setVisibility(View.VISIBLE);
            holder.ll_type1.setVisibility(View.GONE);
            holder.ll_type0.setVisibility(View.GONE);
            holder.mImg1.setVisibility(View.INVISIBLE);
            holder.mImg2.setVisibility(View.INVISIBLE);
            holder.mImg3.setVisibility(View.INVISIBLE);

            holder.mTxtNewsName.setText(lstNews.get(position).getTitle());
            if (imgList != null && imgList.isEmpty() == false) {
                int n = imgListSize > 3 ? 3 : imgListSize;

                switch (n) {
                    case 3:
                        holder.mImg3.setVisibility(View.VISIBLE);
                        setImageUri(holder.mImg3, imgList.get(2));
                    case 2:
                        holder.mImg2.setVisibility(View.VISIBLE);
                        setImageUri(holder.mImg2, imgList.get(1));
                    case 1:
                        holder.mImg1.setVisibility(View.VISIBLE);
                        setImageUri(holder.mImg1, imgList.get(0));

                }
            }
        } else if (imgListSize == 1) {
            holder.ll_type3.setVisibility(View.GONE);
            holder.ll_type1.setVisibility(View.VISIBLE);
            holder.ll_type0.setVisibility(View.GONE);


            holder.mTxtNewsNameType1.setText(lstNews.get(position).getTitle());
            setImageUri(holder.mImg1Type1, imgList.get(0));
        } else if (imgListSize == 0) {
            holder.ll_type3.setVisibility(View.GONE);
            holder.ll_type1.setVisibility(View.GONE);
            holder.ll_type0.setVisibility(View.VISIBLE);

            holder.mTxtNewsNameType0.setText(lstNews.get(position).getTitle());
        }


        if (onItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    private void setImageUri(ImageView iv, String url) {
        if (TextUtils.isEmpty(url) == false) {
            Picasso.with(context).load(url)
                    .placeholder(R.drawable.load_loading_gallery)
                    .error(R.drawable.load_fail_gallery)
                    .into(iv);
        } else {
            Log.e("infoD", "NewsAdapter_setImageUrl_url:" + url);
        }
    }

    @Override
    public int getItemCount() {
        return lstNews.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // 二图三图多图
        TextView mTxtNewsName;
        ImageView mImg1;
        ImageView mImg2;
        ImageView mImg3;
        LinearLayout mLinImg;
        LinearLayout ll_type3;
        TextView tv_line3;
        // 一图
        TextView mTxtNewsNameType1;
        ImageView mImg1Type1;
        LinearLayout ll_type1;
        TextView tv_line1;
        // 无图
        TextView mTxtNewsNameType0;
        LinearLayout ll_type0;
        TextView tv_line0;

        public MyViewHolder(View itemView) {
            super(itemView);
            // 二图三图多图
            mTxtNewsName = (TextView) itemView.findViewById(R.id.txt_news_name);        // 名称
            mImg1 = (ImageView) itemView.findViewById(R.id.img_1);
            mImg2 = (ImageView) itemView.findViewById(R.id.img_2);
            mImg3 = (ImageView) itemView.findViewById(R.id.img_3);
            mLinImg = (LinearLayout) itemView.findViewById(R.id.lin_img);
            ll_type3 = (LinearLayout) itemView.findViewById(R.id.ll_type3);
            tv_line3 = (TextView) itemView.findViewById(R.id.tv_line3);
            // 一图
            mTxtNewsNameType1 = (TextView) itemView.findViewById(R.id.txt_news_name_type1);        // 名称
            mImg1Type1 = (ImageView) itemView.findViewById(R.id.img_type1);
            ll_type1 = (LinearLayout) itemView.findViewById(R.id.ll_type1);
            tv_line1 = (TextView) itemView.findViewById(R.id.tv_line1);
            // 无图
            mTxtNewsNameType0 = (TextView) itemView.findViewById(R.id.txt_news_name_type0);        // 名称
            ll_type0 = (LinearLayout) itemView.findViewById(R.id.ll_type0);
            tv_line0 = (TextView) itemView.findViewById(R.id.tv_line0);
        }
    }


    public void refreshNews(List<NewsRecommendListResponse.DataBean.ListBean> lstTemp) {
        lstNews.clear();
        lstNews.addAll(lstTemp);
        notifyDataSetChanged();
    }

    public void loadMore(List<NewsRecommendListResponse.DataBean.ListBean> lstTemp) {
        lstNews.addAll(lstTemp);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NewsRecommendListResponse.DataBean.ListBean getItemByPosition(int position) {
        return lstNews.get(position);
    }
}
