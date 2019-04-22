package com.cloudring.arrobot.gelin.utils.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by luoren on 2017/3/1.
 */

public class ScaledTransformation implements Transformation<Bitmap> {

    private Context mContext;
    private BitmapPool mBitmapPool;
    private float mScaled;

    public ScaledTransformation(Context context, float scaled) {
        this(context, Glide.get(context).getBitmapPool(), scaled);
    }

    public ScaledTransformation(Context context, BitmapPool pool, float scaled) {
        this.mContext = context;
        this.mBitmapPool = pool;
        this.mScaled = scaled;
    }


    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {

        Bitmap source = resource.get();
        Bitmap bitmap = Bitmap.createScaledBitmap(source, (int) (source.getWidth() * mScaled), (int) (source.getHeight() * mScaled), false);
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "Scaled " + mScaled;
    }
}
