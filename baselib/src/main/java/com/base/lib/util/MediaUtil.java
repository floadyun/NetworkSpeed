package com.base.lib.util;

import android.app.Activity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.List;

/*
 *
 * @copyright : 深圳市腾飞科技有限公司版权所有
 *
 * @author : ${Author}
 *
 * @version :1.0
 *
 * @creation date: 2017/6/29
 *
 * @description:个人中心
 *
 * @update date :
 */
public class MediaUtil {

    public static void selectPicture(Activity activity, int selectType, int selectModel, int maxPicture, List<LocalMedia> localMedias, boolean isCrop,int cropWidth,int cropHeight){
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                .openGallery(selectType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxPicture)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(selectModel)// 多选 or 单选 PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(isCrop)// 是否裁剪
                .compress(true)// 是否压缩
                .cropCompressQuality(50)
                .cropWH(cropWidth,cropHeight)
                .glideOverride(480, 480)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(localMedias)// 是否传入已选图片
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}
