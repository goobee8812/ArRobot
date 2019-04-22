package com.cloudring.arrobot.gelin.utils.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by luoren on 2017/3/21.
 */

public class BackgroudColorTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private int mColor;

    public BackgroudColorTransformation(Context context, int color) {
        this(context, Glide.get(context).getBitmapPool(), color);
    }

    public BackgroudColorTransformation(Context context, BitmapPool pool, int color) {
        this.mBitmapPool = pool;
        this.mColor = color;
    }


    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap.Config config = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = mBitmapPool.get(width, height, config);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, config);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawColor(mColor);
//        canvas.drawARGB(180, 0, 0, 0);
        canvas.drawBitmap(source, 0, 0, paint);
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "BGmColor " + mColor;
    }
}
