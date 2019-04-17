package com.cloudring.arrobot.gelin.download;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.cloudring.arrobot.gelin.R;

import java.util.ArrayList;
import java.util.List;

//wang fangchen  万能的Dialog
public class UniversalDialog extends Dialog {

    /**
     * @描述 构造方法
     * @参数 [context]
     */
    public UniversalDialog(Context context) {
        this(context, 0);
    }

    /**
     * @描述 构造方法
     * @参数 [context, themeResId]
     */
    public UniversalDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public enum DialogItemAnimation {
        ROTATION
    }


    public interface OnDialogItemClickListener {
        void setOnDialogItemClickListener(UniversalDialog dialog, View view);
    }


    public interface OnHaveEtDialogItemClickListener {
        void setOnHaveEtDialogItemClickListener(UniversalDialog dialog, View view, List<EditText> editTexts);
    }

    public static class Builder {
        private View mLayoutView;

        private int                             mAnimResId;
        private int                             mImageViewId;
        private boolean                         isCancel;
        private boolean                         isVague;
        private Object                          mGifResPath;
        private DialogItemAnimation             mAnimation;
        private Context                         mContext;
        private ImageLoaderProxy<ImageView>     mImageLoader;
        private OnDialogItemClickListener       mOnDialogItemClickListener;
        private OnHaveEtDialogItemClickListener mOnHaveEtDialogItemClickListener;
        private OnCancelListener mOnCancelListener;

        private boolean isEt;
        private int[] mLayoutResIds;
        private int[] mLayoutResTextIds;
        private int[] mLayoutResEditIds;
        private int[] mResContents;
        private String[] mContents;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        private void setAnimation() {
            if (mAnimation != null) {
                ObjectAnimator objectAnimator = null;
                switch (mAnimation) {
                    case ROTATION:
                        objectAnimator = ObjectAnimator
                                .ofFloat(mLayoutView.findViewById(mAnimResId),
                                        "rotation", 0f, 360f);
                        objectAnimator.setRepeatCount(-1);
                        objectAnimator.setInterpolator(new LinearInterpolator());
                        objectAnimator.setDuration(1500L);
                        break;
                }
                objectAnimator.start();
            }
        }

        public Builder setLayoutView(int layoutResId) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mLayoutView = inflater.inflate(layoutResId, null, true);
            return this;
        }

        public Builder setText(boolean isEt, int[] layoutResTextIds, int[] contents) {
            this.isEt = isEt;
            this.mLayoutResTextIds = layoutResTextIds;
            this.mResContents = contents;
            return this;
        }

        public Builder setText(boolean isEt, int[] layoutResTextIds, String[] contents) {
            this.isEt = isEt;
            this.mLayoutResTextIds = layoutResTextIds;
            this.mContents = contents;
            return this;
        }

        /**
         * @描述 提供给外界设置点击Dialog以外的部分时是否消失
         * @参数 [isCancel]
         * @返回 com.songjie.custom.widget.dialog.UniversalDialog.Builder
         */
        public Builder setCanceledOnTouchOutside(boolean isCancel) {
            this.isCancel = isCancel;
            return this;
        }

        /**
         * @描述 提供给外界设置点击Dialog以外的部分时是否消失
         * @参数 [isCancel]
         * @返回 com.songjie.custom.widget.dialog.UniversalDialog.Builder
         */
        public Builder setVagueBackground(boolean isVague) {
            this.isVague = isVague;
            return this;
        }

        /**
         * @描述 设置图片加载器
         * @参数 [imageLoader]
         * @返回 com.songjie.custom.widget.dialog.UniversalDialog.Builder
         */
        public Builder setGifImageLoader(ImageLoaderProxy<ImageView> imageLoader) {
            this.mImageLoader = imageLoader;
            return this;
        }

        /**
         * @描述 设置ImageView和gif图片
         * @参数 [imagViewId, gifResPath]
         * @返回 com.songjie.custom.widget.dialog.UniversalDialog.Builder
         */
        public Builder setGifImageOnDialog(int imageViewId, Object gifResPath) {
            this.mImageViewId = imageViewId;
            this.mGifResPath = gifResPath;
            return this;
        }

        /**
         * @描述 提供给外界设置视图动画效果
         * @参数 [animResId, animation]
         * @返回 com.songjie.custom.widget.dialog.UniversalDialog.Builder
         */
        public Builder setDialogItemAnimation(int animResId, DialogItemAnimation animation) {
            this.mAnimResId = animResId;
            this.mAnimation = animation;
            return this;
        }

        /**
         * @描述 提供给外界设置视图点击事件
         * @参数 [layoutResIds, listener]
         * @返回 com.songjie.custom.widget.dialog.UniversalDialog.Builder
         */
        public Builder setDialogItemClickListener(int[] layoutResIds, OnDialogItemClickListener listener) {
            this.mLayoutResIds = layoutResIds;
            this.mOnDialogItemClickListener = listener;
            return this;
        }

        public Builder setHaveEtDialogItemClickListener(int[] layoutResEditIds,
                                                        int[] layoutResIds,
                                                        OnHaveEtDialogItemClickListener listener) {
            this.mLayoutResIds = layoutResIds;
            this.mLayoutResEditIds = layoutResEditIds;
            this.mOnHaveEtDialogItemClickListener = listener;
            return this;
        }

        public UniversalDialog create() {
            if (!isVague) {
                final UniversalDialog dialog = new UniversalDialog(mContext,
                        R.style.DialogDefaultStyle);
                dialog.addContentView(mLayoutView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dialog.setCanceledOnTouchOutside(isCancel);
                if (mLayoutResEditIds != null && mLayoutResIds != null &&
                        mOnHaveEtDialogItemClickListener != null) {
                    for (int id : mLayoutResIds) {
                        mLayoutView.findViewById(id).setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        List<EditText> editTexts =
                                                new ArrayList<>(mLayoutResEditIds.length);
                                        for (int id : mLayoutResEditIds) {
                                            EditText et = mLayoutView.findViewById(id);
                                            String text = et.getText().toString();
//                                            if (StringUtil.isEmpty(text)) {
//                                                return;
//                                            }
                                            editTexts.add(et);
                                        }
                                        mOnHaveEtDialogItemClickListener
                                                .setOnHaveEtDialogItemClickListener(dialog, view, editTexts);
//                                        dialog.dismiss();
                                    }
                                });
                    }

                } else {
                    if (mLayoutResIds != null && mOnDialogItemClickListener != null) {
                        for (int id : mLayoutResIds) {
                            mLayoutView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mOnDialogItemClickListener
                                            .setOnDialogItemClickListener(dialog, view);
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                }

                setAnimation();

                if (mImageLoader != null) {
                    ImageView imageView = mLayoutView.findViewById(mImageViewId);
                    mImageLoader.displayGifImage(mContext, mGifResPath, imageView);
                }

                if (mLayoutResTextIds != null
                        && mContents != null
                        && mLayoutResTextIds.length == mContents.length) {
                    if (isEt) {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            EditText editText = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            editText.setText(mContents[i]);
                        }
                    } else {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            TextView textView = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            textView.setText(mContents[i]);
                        }
                    }
                }

                if (mLayoutResTextIds != null
                        && mResContents != null
                        && mLayoutResTextIds.length == mResContents.length) {
                    if (isEt) {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            EditText editText = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            editText.setText(mResContents[i]);
                        }
                    } else {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            TextView textView = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            textView.setText(mResContents[i]);
                        }
                    }
                }

                return dialog;
            } else {
                final UniversalDialog dialog = new UniversalDialog(mContext,
                        R.style.DialogDefaultStyle2);
                dialog.addContentView(mLayoutView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dialog.setCanceledOnTouchOutside(isCancel);
                if (mLayoutResEditIds != null && mLayoutResIds != null &&
                        mOnHaveEtDialogItemClickListener != null) {
                    for (int id : mLayoutResIds) {
                        mLayoutView.findViewById(id).setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        List<EditText> editTexts =
                                                new ArrayList<>(mLayoutResEditIds.length);
                                        for (int id : mLayoutResEditIds) {
                                            EditText et = mLayoutView.findViewById(id);
                                            String text = et.getText().toString();
//                                            if (StringUtil.isEmpty(text)) {
//                                                return;
//                                            }
                                            editTexts.add(et);
                                        }
                                        mOnHaveEtDialogItemClickListener
                                                .setOnHaveEtDialogItemClickListener(dialog, view, editTexts);
//                                        dialog.dismiss();
                                    }
                                });
                    }

                } else {
                    if (mLayoutResIds != null && mOnDialogItemClickListener != null) {
                        for (int id : mLayoutResIds) {
                            mLayoutView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mOnDialogItemClickListener
                                            .setOnDialogItemClickListener(dialog, view);
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                }

                setAnimation();

                if (mImageLoader != null) {
                    ImageView imageView = mLayoutView.findViewById(mImageViewId);
                    mImageLoader.displayGifImage(mContext, mGifResPath, imageView);
                }

                if (mLayoutResTextIds != null
                        && mContents != null
                        && mLayoutResTextIds.length == mContents.length) {
                    if (isEt) {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            EditText editText = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            editText.setText(mContents[i]);
                        }
                    } else {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            TextView textView = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            textView.setText(mContents[i]);
                        }
                    }
                }

                if (mLayoutResTextIds != null
                        && mResContents != null
                        && mLayoutResTextIds.length == mResContents.length) {
                    if (isEt) {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            EditText editText = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            editText.setText(mResContents[i]);
                        }
                    } else {
                        for (int i = 0; i < mLayoutResTextIds.length; i++) {
                            TextView textView = mLayoutView.findViewById(mLayoutResTextIds[i]);
                            textView.setText(mResContents[i]);
                        }
                    }
                }

                return dialog;

            }
        }
    }
}
