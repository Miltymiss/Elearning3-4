package com.other.demo.application.download;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.File;


public final class DownloadService extends Service implements OnDownloadListener {

    private static final String TAG = Constant.TAG + "DownloadService";
    private String downloadUrl;
    private String pdfName;
    private String downloadPath;
    private int lastProgress;
    private DownloadManager downloadManager;
    private OnDownloadListener onDownloadListener;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DownloadService.this, "下载文件中...", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    onDownloadListener.start();
                    break;
                case 2:
                    onDownloadListener.downloading(msg.arg1, msg.arg2);
                    break;
                case 3:
                    onDownloadListener.done((File) msg.obj);
                    releaseResources();
                    break;
                case 5:
                    onDownloadListener.error((Exception) msg.obj);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_STICKY;
        }
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        downloadManager = DownloadManager.getInstance();
        if (downloadManager == null) {
            Log.e(TAG, "init DownloadManager.getInstance() = null ,请先调用 getInstance(Context context) !");
            return;
        }
        onDownloadListener = downloadManager.getOnDownloadListener();
        downloadUrl = downloadManager.getUrl();
        pdfName = downloadManager.getPdfName();
        downloadPath = downloadManager.getDownloadPath();
        //创建apk文件存储文件夹
        FileUtil.createDirDirectory(downloadPath);
        download();
    }

    /**
     * 获取下载管理者
     */
    private synchronized void download() {
        if (downloadManager.isDownloading()) {
            Log.e(TAG, "download: 当前正在下载，请务重复下载！");
            return;
        }

        HttpDownloadManager manager = new HttpDownloadManager(downloadPath);
        manager.download(downloadUrl, pdfName, this);
        downloadManager.setState(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void start() {
        handler.sendEmptyMessage(1);
    }

    @Override
    public void downloading(int max, int progress) {
        Log.i(TAG, "max: " + max + " --- progress: " + progress);
        handler.obtainMessage(2, max, progress).sendToTarget();
    }

    @Override
    public void done(File file) {
        Log.d(TAG, "done: 文件已下载至" + file.toString());
        downloadManager.setState(false);
        //如果用户设置了回调 则先处理用户的事件 在执行自己的
        handler.obtainMessage(3, file).sendToTarget();
    }

    @Override
    public void error(Exception e) {
        Log.e(TAG, "error: " + e);
        downloadManager.setState(false);
        handler.obtainMessage(5, e).sendToTarget();
    }

    /**
     * 下载完成释放资源
     */

    private void releaseResources() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        stopSelf();
        downloadManager.release();
    }
}
