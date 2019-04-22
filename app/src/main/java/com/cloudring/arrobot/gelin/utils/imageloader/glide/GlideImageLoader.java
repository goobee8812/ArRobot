package com.cloudring.arrobot.gelin.utils.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cloudring.arrobot.gelin.utils.imageloader.ImageLoaderInterface;

/**
 * Created by luoren
 * on 2017/2/24.
 */
public class GlideImageLoader implements ImageLoaderInterface {


    @Override
    public void display(ImageView imageView, String url) {
//        display(imageView,url, R.drawable.ic_photo_size_select_actual_white_24dp, R.drawable.ic_photo_size_select_actual_white_24dp);
        display(imageView, url, 0, 0);
    }

    @Override
    public void display(ImageView imageView, int resId) {
//        display(imageView, resId, R.drawable.ic_photo_size_select_actual_white_24dp, R.drawable.ic_photo_size_select_actual_white_24dp);
        display(imageView, resId, 0, 0);
    }

    @Override
    public void display(ImageView imageView, String url, int loadingImg, int errorImg) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        DrawableTypeRequest request = Glide.with(imageView.getContext()).load(url);
        if(0 != loadingImg) {
            request.placeholder(loadingImg);
        }
        if(0 != errorImg) {
            request.error(errorImg);
        }
        request
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //用原图的1/10作为缩略图，如果缩略图先被加载出来则先显示缩略图
//                .thumbnail(0.1f)
                // 设置加载动画,默认
                .crossFade()
                .centerCrop()
                //解决加载出来的瞬间闪一下的问题
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void display(ImageView imageView, int resId, int loadingImg, int errorImg) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        DrawableTypeRequest request = Glide.with(imageView.getContext()).load(resId);
        // 设置加载中以及加载失败图片
        if(0 != loadingImg) {
            request.placeholder(loadingImg);
        }
        if(0 != errorImg) {
            request.error(errorImg);
        }
        request
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.1f)
                .crossFade()
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void displayCircleImg(ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext())
                .load(resId)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    @Override
    public void displayCircleImg(Context context, String url, GlideDrawableImageViewTarget glideDrawableImageViewTarget) {
        if (glideDrawableImageViewTarget == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .transform(new GlideCircleTransform(context))
                .into(glideDrawableImageViewTarget);
    }


    // 设置动态转换， 圆形
    @Override
    public void displayCircleImg(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext())
                .load(url)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    @Override
    public void displayAsBitmap(Context context, String url, com.bumptech.glide.request.target.Target<Bitmap> bitmapTarget) {
        if (context == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .into(bitmapTarget);
    }
}
