package com.base.lib.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.transition.Transition;

/**
 * Created by yxliu on 2017/9/1.
 */

public interface SimpleTargetListener {
    void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition);
    void onLoadFailed(@Nullable Drawable errorDrawable);
    void onLoadCleared(@Nullable Drawable placeholder);
    void onLoadStarted(@Nullable Drawable placeholder);
}
