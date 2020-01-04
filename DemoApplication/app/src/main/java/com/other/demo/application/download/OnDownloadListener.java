package com.other.demo.application.download;

import java.io.File;


/**
 * @author: hedianbo.
 * @date: 2019-11-23 11:43.
 * @desc:  正在下载的回调
 */
public interface OnDownloadListener {

    /**
     * 开始下载
     */
    void start();

    /**
     * 下载中
     *
     * @param max      总进度
     * @param progress 当前进度
     */
    void downloading(int max, int progress);

    /**
     * 下载完成
     *
     * @param apk 下载好的apk
     */
    void done(File apk);

    /**
     * 下载出错
     *
     * @param e 错误信息
     */
    void error(Exception e);
}
