package com.cloudring.arrobot.gelin.utils.imageloader.glide;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cloudring.arrobot.gelin.download.Check;
import com.youth.banner.loader.ImageLoaderInterface;

/**
 * Created by slx on 2017/2/21.
 */

public class GlideLoader implements ImageLoaderInterface {

    @Override
    public void displayImage(Context context, Object path, View imageView) {
        if (Check.isNull(imageView))return;
        Glide.with(context)
                .load(path)
                .centerCrop()
                .crossFade()
                .into((ImageView) imageView);
    }

    @Override
    public View createImageView(Context context) {
        return new ImageView(context);
    }
}