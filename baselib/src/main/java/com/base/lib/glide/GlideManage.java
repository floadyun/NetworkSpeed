package com.base.lib.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.orhanobut.logger.Logger;
import java.io.File;
/**
 * Created by yxliu on 2017/9/1.
 */

public class GlideManage {

    private GlideUtil glideUtil;
    private static GlideManage glideApp;

    private GlideManage() {
        glideUtil = new GlideUtil();
    }

    public static GlideManage getInstance() {
        if (glideApp == null) {
            glideApp = new GlideManage();
        }
        return glideApp;
    }

    /**
     * 使用Glide4.0 显示网络图片
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param path      图片路径
     * @param imageView 图片控件
     */
    public void with(Context context, int placeIcon, String path, final ImageView imageView) {
        glideUtil.with(context, placeIcon, path, imageView,null);
    }
    /**
     * 使用Glide4.0 显示网络图片
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param path      图片路径
     * @param imageView 图片控件
     */
    public void with(Context context, int placeIcon, String path, final ImageView imageView, RequestListener requestListener) {
        glideUtil.with(context, placeIcon, path, imageView,requestListener);
    }
    /**
     * 使用Glide4.0 显示网络图片
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param file      图片文件
     * @param imageView 图片控件
     */
    public void with(Context context, int placeIcon, File file, final ImageView imageView, RequestListener requestListener) {
        glideUtil.with(context, placeIcon, file, imageView,requestListener);
    }
    /**
     * 使用Glide4.0 显示增加签名(适合图片路径一样，每次重新加载)图片
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param path      图片路径
     * @param imageView 图片控件
     */
    public void withSignature(Context context, int placeIcon, String path, final ImageView imageView) {
       glideUtil.withSignature(context,placeIcon,path,imageView);
    }

    /**
     * 使用Glide4.0显示图片 图片加载监听(Glide图片缓存问题，同一url换图片不起作用问题)
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param path      图片路径
     */
    public void withReadySignature(Context context, int placeIcon, String path, final SimpleTargetListener simpleTargetListener) {
        glideUtil.withReadySignature(context,placeIcon,path,simpleTargetListener);
    }

    /**
     * 使用Glide4.0 显示资源图片图片
     *
     * @param context      上下文
     * @param resourceIcon 资源图片路径
     * @param imageView    图片控件
     */
    public void with(Context context, int resourceIcon, final ImageView imageView,RequestListener requestListener) {
        glideUtil.with(context, resourceIcon, imageView,requestListener);
    }

    /**
     * 使用Glide4.0 显示本地Gif图片
     *
     * @param context      上下文
     * @param resourceIcon 图片路径
     * @param imageView    图片控件
     */
    public void withGif(Context context, int resourceIcon, final ImageView imageView) {
        glideUtil.withGif(context, resourceIcon, imageView);
    }

    /**
     * 使用Glide4.0显示图片 图片加载监听
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param path      图片路径
     */
    public void withReady(Context context, int placeIcon, String path, final SimpleTargetListener simpleTargetListener) {
        glideUtil.withReady(context, placeIcon, path, simpleTargetListener);
    }

    /**
     * 使用Glide4.0显示图片 图片加载监听
     *
     * @param context 上下文
     * @param path    图片路径
     */
    public void withReady(Context context, String path, final SimpleTargetListener simpleTargetListener) {
        glideUtil.withReady(context, path, simpleTargetListener);
    }


    /**
     * 使用Glide4.0显示圆形图片
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param path      图片路径
     * @param imageView 图片控件
     */
    public void withGlideCircleTransform(Context context, int placeIcon, String path, ImageView imageView) {
        glideUtil.withGlideCircleTransform(context, placeIcon, path, imageView);
    }

    /**
     * 使用Glide4.0显示带圆角的图片
     *
     * @param context   上下文
     * @param placeIcon 占位图
     * @param path      图片路径
     * @param imageView 图片控件
     */
    public void withGlideRoundTransform(Context context, int placeIcon, String path, ImageView imageView) {
        glideUtil.withGlideRoundTransform(context, placeIcon, path, imageView);
    }


    private class GlideUtil {
        /**
         * 使用Glide4.0 显示网络图片
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param path      图片路径
         * @param imageView 图片控件
         */
        private void with(Context context, int placeIcon, String path, final ImageView imageView,RequestListener requestListener) {
            try {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(placeIcon)
                        .error(placeIcon)
                        .priority(Priority.HIGH);//优先级
                // 调用glide显示图片：
                Glide.with(context).load(path).listener(requestListener).apply(options).into(imageView);
            }catch (Exception e){
            }
        }
        /**
         * 使用Glide4.0 显示网络图片
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param file      图片文件
         * @param imageView 图片控件
         */
        private void with(Context context, int placeIcon, File file, final ImageView imageView,RequestListener requestListener) {
            try {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(placeIcon)
                        .error(placeIcon)
                        .priority(Priority.HIGH);//优先级
                // 调用glide显示图片：
                Glide.with(context).load(file).listener(requestListener).apply(options).into(imageView);
            }catch (Exception e){

            }
        }
        /**
         * 使用Glide4.0 显示增加签名图片 (Glide图片缓存问题，同一url换图片不起作用问题)
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param path      图片路径
         * @param imageView 图片控件
         */
        private void withSignature(Context context, int placeIcon, String path, final ImageView imageView) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeIcon)
                    .error(placeIcon)
                    .diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                    .skipMemoryCache( true )//跳过内存缓存
                    .priority(Priority.HIGH);//优先级
            // 调用glide显示图片：
            Glide.with(context).load(path).apply(options).into(imageView);
        }

        /**
         * 使用Glide4.0 显示资源图片图片
         *
         * @param context      上下文
         * @param resourceIcon 资源图片路径
         * @param imageView    图片控件
         */
        private void with(Context context, int resourceIcon, final ImageView imageView,RequestListener requestListener) {
            // 调用glide显示图片：
            Glide.with(context).load(resourceIcon).listener(requestListener).into(imageView);
        }

        /**
         * 使用Glide4.0 显示本地Gif图片
         *
         * @param context      上下文
         * @param resourceIcon 图片路径
         * @param imageView    图片控件
         */
        private void withGif(Context context, int resourceIcon, final ImageView imageView) {
            // 调用glide显示图片：
            Glide.with(context).asGif().load(resourceIcon).into(imageView);
        }

        /**
         * 使用Glide4.0显示图片 图片加载监听
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param path      图片路径
         */
        private void withReady(Context context, int placeIcon, String path, final SimpleTargetListener simpleTargetListener) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeIcon)
                    .error(placeIcon)
                    .priority(Priority.HIGH);//优先级
            // 调用glide显示图片：
            Glide.with(context).asBitmap().load(path).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onResourceReady(resource, transition);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadFailed(errorDrawable);
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    super.onLoadCleared(placeholder);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadCleared(placeholder);
                    }
                }

                @Override
                public void onLoadStarted(@Nullable Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadStarted(placeholder);
                    }
                }
            });
        }

        /**
         * 使用Glide4.0显示图片 图片加载监听(Glide图片缓存问题，同一url换图片不起作用问题)
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param path      图片路径
         */
        private void withReadySignature(Context context, int placeIcon, String path, final SimpleTargetListener simpleTargetListener) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeIcon)
                    .error(placeIcon)
                    .diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                    .skipMemoryCache( true )//跳过内存缓存
                    .priority(Priority.HIGH);//优先级
            // 调用glide显示图片：
            Glide.with(context).asBitmap().load(path).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onResourceReady(resource, transition);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadFailed(errorDrawable);
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    super.onLoadCleared(placeholder);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadCleared(placeholder);
                    }
                }

                @Override
                public void onLoadStarted(@Nullable Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadStarted(placeholder);
                    }
                }
            });
        }

        /**
         * 使用Glide4.0显示图片 图片加载监听 没有默认图
         *
         * @param context 上下文
         * @param path    图片路径
         */
        private void withReady(Context context, String path, final SimpleTargetListener simpleTargetListener) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH);//优先级
            // 调用glide显示图片：
            Glide.with(context).asBitmap().load(path).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onResourceReady(resource, transition);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadFailed(errorDrawable);
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    super.onLoadCleared(placeholder);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadCleared(placeholder);
                    }
                }

                @Override
                public void onLoadStarted(@Nullable Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    if (null != simpleTargetListener) {
                        simpleTargetListener.onLoadStarted(placeholder);
                    }
                }
            });
        }


        /**
         * 使用Glide4.0显示圆形图片
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param path      图片路径
         * @param imageView 图片控件
         */
        private void withGlideCircleTransform(Context context, int placeIcon, String path, ImageView imageView) {
            try {
                RequestOptions options = new RequestOptions()
                        .circleCrop()//裁剪圆形图片
                        .placeholder(placeIcon)
                        .error(placeIcon)
                        .priority(Priority.HIGH);//优先级
                //调用glide显示图片：
                Glide.with(context).load(path).apply(options).into(imageView);
//                GlideApp.with(context).load(path).apply(options).into(imageView);
            }catch (Exception e){
                Logger.e("load image exception "+e.toString());
            }
        }

        /**
         * 使用Glide4.0显示带圆角的图片radius 默认100
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param path      图片路径
         * @param imageView 图片控件
         */
        private void withGlideRoundTransform(Context context, int placeIcon, String path, ImageView imageView) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeIcon)
                    .error(placeIcon)
                    .priority(Priority.HIGH)//优先级
//                    .transform(new GlideRoundTransform(context,20, GlideRoundTransform.CornerType.ALL));
                    .optionalTransform(new RoundedCorners(20));
            // 调用glide显示图片：
            Glide.with(context).load(path).apply(options).into(imageView);
        }

        /**
         * 使用Glide4.0显示带圆角的图片 radius 自定义
         *
         * @param context   上下文
         * @param placeIcon 占位图
         * @param path      图片路径
         * @param imageView 图片控件
         */
        private void withGlideRoundTransform(Context context, int placeIcon, String path, ImageView imageView,int radius) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeIcon)
                    .error(placeIcon)
                    .priority(Priority.HIGH)
//                    .transform(new GlideRoundTransform(context,radius, GlideRoundTransform.CornerType.ALL));
                    .optionalTransform(new RoundedCorners(radius));
            // 调用glide显示图片：
            Glide.with(context).load(path).apply(options).into(imageView);
        }
    }
}
