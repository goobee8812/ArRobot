package com.cloudring.arrobot.gelin.download;

import android.content.Context;
import android.view.View;

import java.io.Serializable;


public interface ImageLoaderProxy<T extends View> extends Serializable {

    T createImageView(Context mContext);

    void displayImage(Context context, Object path, T imageView);

    void displayAlphaImage(Context context, Object path, T imageView);

    void displayCircleImage(Context context, Object path, T imageView);

    void displayGifImage(Context context, Object path, T imageView);
}
