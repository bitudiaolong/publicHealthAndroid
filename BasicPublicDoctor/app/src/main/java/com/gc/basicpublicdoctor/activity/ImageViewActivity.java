package com.gc.basicpublicdoctor.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.squareup.picasso.Picasso;

public class ImageViewActivity extends BaseActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        showTitleName("图片", true);
        String path = getIntent().getStringExtra("path");
        boolean isFromNet = getIntent().getBooleanExtra("isFromNet", false);
        imageView = findViewById(R.id.img);
        if (isFromNet) {
            Picasso.with(context).load(path)
                    .placeholder(R.drawable.load_loading_gallery)
                    .error(R.drawable.load_fail_gallery)
                    .into(imageView);
        } else {
            Bitmap bm = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bm);
        }
    }
}
