package com.other.demo.application.activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.other.demo.application.R;
import com.other.demo.application.bean.Coursevideo;
import com.other.demo.application.download.DownloadManager;
import com.other.demo.application.download.FileUtil;
import com.other.demo.application.download.OnDownloadListener;
import com.other.demo.application.room.AppDatabase;
import com.other.demo.application.room.VideoBean;
import com.other.demo.application.room.VideoDao;
import com.other.demo.application.utlis.BaseUrl;

import java.io.File;

import butterknife.BindView;
import chuangyuan.ycj.videolibrary.listener.VideoInfoListener;
import chuangyuan.ycj.videolibrary.video.ExoUserPlayer;
import chuangyuan.ycj.videolibrary.video.VideoPlayerManager;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.videoplayer)
    VideoPlayerView videoplayer;
    private ExoUserPlayer exoPlayerManager;
    private Coursevideo coursevideo;
    private VideoDao videoDao;
    private Disposable subscribe;
    private ProgressDialog progressDialog;

    @Override
    protected int getContentView(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        return R.layout.activity_play_video;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        getWindow().setEnterTransition(new Explode().setDuration(1000));
        initPlay();

        coursevideo = (Coursevideo) getIntent().getSerializableExtra("list");
        if (coursevideo != null) {
            Log.e("hedb", "initData:音视频播放地址：：  " + BaseUrl.baseImage + coursevideo.getMaterialUrl());
            videoDao = AppDatabase.getInstance(this).videoDao();

            subscribe = Observable.create((ObservableOnSubscribe<VideoBean>) emitter -> {
                VideoBean videoBean = videoDao.queryVideo(coursevideo.getMaterialUrl());
                if (videoBean == null) {
                    emitter.onNext(new VideoBean());
                } else {
                    emitter.onNext(videoBean);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(videoBean -> {
                        if (TextUtils.isEmpty(videoBean.getMaterial_url())){
                            downLoadFile(coursevideo);
                        }else {
                            startPlay(videoBean.getVideoPath(), true);
                        }
                    });
        } else {
            Toast.makeText(mContext, "地址异常，联系管理员", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (exoPlayerManager != null) {
            exoPlayerManager.onDestroy();
        }
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayerManager != null) {
            exoPlayerManager.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayerManager != null) {
            exoPlayerManager.onResume();
        }
    }

    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("数据下载中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void cancelProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }

    private void downLoadFile(Coursevideo coursevideo) {

        buildProgressDialog();
        DownloadManager instance = DownloadManager.getInstance(this);
        instance.setpdfName(System.currentTimeMillis() + ".mp4");
        instance.setdownLoadurl(BaseUrl.baseImage + coursevideo.getMaterialUrl());
        instance.download();

        instance.setOnDownloadListener(new OnDownloadListener() {
            @Override
            public void start() {
                Log.e("hedb", "start: ");
            }

            @Override
            public void downloading(int max, int progress) {
                Log.e("hedb", "downloading: " + progress);
            }

            @Override
            public void done(File file) {
                Log.e("hedb", "downloading: " + file.getAbsolutePath());
                cancelProgressDialog();

                subscribe = Observable.create((ObservableOnSubscribe<VideoBean>) emitter -> {
                    VideoBean videoBean = new VideoBean();
                    videoBean.setMaterial_url(coursevideo.getMaterialUrl());
                    videoBean.setVideoPath(file.getAbsolutePath());
                    videoDao.insterVideo(videoBean);
                }).subscribeOn(Schedulers.io())
                        .subscribe();
                startPlay(BaseUrl.baseImage + coursevideo.getMaterialUrl(), false);
            }

            @Override
            public void error(Exception e) {
                Log.e("hedb", "error: " + e.getMessage());
                cancelProgressDialog();
            }
        });
    }


    private void initPlay() {
        exoPlayerManager = new VideoPlayerManager.Builder(this, VideoPlayerManager.TYPE_PLAY_GESTURE, R.id.videoplayer)
                .setPlayerGestureOnTouch(true)
                .setShowVideoSwitch(false)
                .addVideoInfoListener(new VideoInfoListener() {

                    @Override
                    public void onPlayStart(long currPosition) {
                    }

                    @Override
                    public void onLoadingChanged() {
                    }

                    @Override
                    public void onPlayerError(ExoPlaybackException e) {
                        showToast("播放出现错误,网络异常");
                    }

                    @Override
                    public void onPlayEnd() {
                        onBackPressed();
                    }

                    @Override
                    public void isPlaying(boolean playWhenReady) {
                    }
                })
                .create();

    }

    private void startPlay(String source1, boolean isloacl) {
        Uri uri = null;
        if (isloacl) {
            Log.e("hedb", "initPlay: " + source1);
            File file = FileUtil.fileExists(source1);
            if (file.exists()) {
                uri = Uri.fromFile(file);
            } else {
                downLoadFile(coursevideo);
                return;
            }
        } else {
            uri = Uri.parse(source1);
        }

        if (exoPlayerManager != null) {
            exoPlayerManager.setPlayUri(uri);
            exoPlayerManager.startPlayer();
        }

    }

    @Override
    public void onBackPressed() {
        if (exoPlayerManager != null) {
            if (exoPlayerManager.onBackPressed()) {
                ActivityCompat.finishAfterTransition(this);
            }
            exoPlayerManager.onDestroy();
        }
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (exoPlayerManager != null) {
            exoPlayerManager.onConfigurationChanged(newConfig);//横竖屏切换
        }
    }


}
