package com.base.lib.http.base;

/**
 * Created by yixiaofei on 2017/3/11 0011.
 */

public interface UploadProgressListener {
    /**
     * 上传进度
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}
