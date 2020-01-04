package com.other.demo.application.download;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


public class DownloadManager {

    private static final String TAG = Constant.TAG + "DownloadManager";

    /**
     * 上下文
     */
    private static Context context;
    private static DownloadManager manager;

    private String downLoadUrl = "";

    private String pdfName = "";
    /**
     * 下载存放的位置
     */
    private String downloadPath;
    /**
     * 当前下载状态
     */
    private boolean state = false;
    private OnDownloadListener onDownloadListeners;

    /**
     * 框架初始化
     *
     * @param context 上下文
     * @return {@link DownloadManager}
     */
    public static DownloadManager getInstance(Context context) {
        DownloadManager.context = context;
        if (manager == null) {
            synchronized (DownloadManager.class) {
                if (manager == null) {
                    manager = new DownloadManager();
                }
            }
        }
        return manager;
    }

    /**
     * 供此依赖库自己使用.
     *
     * @return {@link DownloadManager}
     * @hide
     */
    public static DownloadManager getInstance() {
        return manager;
    }


    public String getUrl() {
        return downLoadUrl;
    }

    /**
     * 设置apk下载地址
     */
    public DownloadManager setdownLoadurl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
        return this;
    }


    public String getPdfName() {
        return pdfName;
    }


    public DownloadManager setpdfName(String pdfName) {
        this.pdfName = pdfName;
        return this;
    }


    public OnDownloadListener getOnDownloadListener() {
        return onDownloadListeners;
    }


    public DownloadManager setOnDownloadListener(OnDownloadListener OnDownloadListener) {
        this.onDownloadListeners = OnDownloadListener;
        return this;
    }


    public String getDownloadPath() {
        return downloadPath;
    }


    public DownloadManager setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
        return this;
    }


    /**
     * 设置当前状态
     *
     * @hide
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * 当前是否正在下载
     */
    public boolean isDownloading() {
        return state;
    }

    /**
     * 开始下载
     */
    public void download() {

        if (TextUtils.isEmpty(downloadPath)) {
            downloadPath = context.getExternalCacheDir().getPath();
        }

        //使用缓存目录不申请权限
        if (!downloadPath.equals(context.getExternalCacheDir().getPath())) {
            //检查权限
            if (!PermissionUtil.checkStoragePermission(context)) {
                context.startActivity(new Intent(context, PermissionActivity.class));
                return;
            }
        }
        context.startService(new Intent(context, DownloadService.class));
    }


    /**
     * 释放资源
     */
    public void release() {
        context = null;
        manager = null;
    }
}
