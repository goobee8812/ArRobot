package com.cloudring.arrobot.gelin.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.cloudring.arrobot.gelin.utils.imageloader.glide.GlideImageLoader;

/**
 * Created by luoren
 * on 2017/2/24.
 */
public class ImageUtils {

    private static final ImageLoaderInterface DEFAULT_LOADER = new GlideImageLoader();
    private ImageLoaderInterface mLoaderInterface;

    private ImageUtils() {
        mLoaderInterface = DEFAULT_LOADER;
    }

    public static ImageUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static final ImageUtils INSTANCE = new ImageUtils();
    }

    public void init(ImageLoaderInterface imageLoaderInterface) {
        mLoaderInterface = imageLoaderInterface;
    }

    public void display(String url, ImageView imageView) {

        mLoaderInterface.display(imageView, url);
    }

    public void display(ImageView imageView, String url, int loadingImg, int errorImg) {

        mLoaderInterface.display(imageView, url, loadingImg, errorImg);
    }

    public void display(ImageView imageView, int resId) {
        mLoaderInterface.display(imageView, resId);
    }

    public void displayCircleImg(ImageView imageView, int resId) {
        mLoaderInterface.displayCircleImg(imageView, resId);
    }

    public void displayCircleImg(ImageView imageView, String url) {
        mLoaderInterface.displayCircleImg(imageView, url);
    }

    public void displayCircleImg(Context context, String url, GlideDrawableImageViewTarget glideDrawableImageViewTarget) {
        mLoaderInterface.displayCircleImg(context, url, glideDrawableImageViewTarget);
    }

    public void displayAsBitmap(Context context, String url, Target<Bitmap> bitmapTarget) {
        mLoaderInterface.displayAsBitmap(context, url, bitmapTarget);
    }

}
