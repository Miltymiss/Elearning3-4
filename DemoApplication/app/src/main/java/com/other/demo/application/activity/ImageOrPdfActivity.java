package com.other.demo.application.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.other.demo.application.R;
import com.other.demo.application.bean.Coursevideo;
import com.other.demo.application.dao.DaoHelper;
import com.other.demo.application.dao.FileBean;
import com.other.demo.application.download.DownloadManager;
import com.other.demo.application.download.FileUtil;
import com.other.demo.application.download.OnDownloadListener;
import com.other.demo.application.utlis.BaseUrl;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

public class ImageOrPdfActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.pdfView)
    PDFView pdfView;

    private Coursevideo coursevideo;
    private String localPath;

    @Override
    protected int getContentView(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        return R.layout.activity_image_or_pdf;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        coursevideo = (Coursevideo) getIntent().getSerializableExtra("list");

        findViewById(R.id.share).setOnClickListener(view -> {
            if (coursevideo != null && coursevideo.getMediatype().equals("image")) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add(localPath);
                shareMoreImages(strings);
            }
        });

        if (coursevideo != null) {
            String url = BaseUrl.baseImage + coursevideo.getMaterialUrl();
            Log.e("hedb", "initData:图片 PDF地址：：  " + url);
            FileBean fileBean = DaoHelper.getInstance(this).queryFileIsExist(coursevideo.getMaterialUrl());
            if (fileBean == null) {
                downLoadFile(coursevideo);
                return;
            }

            localPath = fileBean.getLocalPath();
            File file = FileUtil.fileExists(localPath);

            if (coursevideo.getMediatype().equals("image")) {
                pdfView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                if (file.exists()) {
                    Glide.with(this).load(file).into(imageView);
                } else {
                    downLoadFile(coursevideo);
                }
            } else if (coursevideo.getMediatype().equals("pdf")) {
                pdfView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                if (file.exists()) {
                    initPDf(file);
                } else {
                    downLoadFile(coursevideo);
                }
            }
        } else {
            Toast.makeText(mContext, "地址异常，联系管理员", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void downLoadFile(Coursevideo coursevideo) {

        DownloadManager instance = DownloadManager.getInstance(this);
        if (coursevideo.getMediatype().equals("image")) {
            instance.setpdfName(System.currentTimeMillis() + ".png");

        } else if (coursevideo.getMediatype().equals("pdf")) {
            instance.setpdfName(System.currentTimeMillis() + ".pdf");

        }
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
                localPath  = file.getAbsolutePath();
                Log.e("hedb", "downloading: " + localPath);
                FileBean fileBean = new FileBean();
                fileBean.setCourse_id(coursevideo.getCourseId());
                fileBean.setLocalPath(localPath);
                fileBean.setMaterial_id(coursevideo.getId());
                fileBean.setMediatype(coursevideo.getMediatype());
                fileBean.setMaterial_url(coursevideo.getMaterialUrl());

                DaoHelper.getInstance(ImageOrPdfActivity.this).addFileDown(fileBean);
                if (coursevideo.getMediatype().equals("image")) {
                    pdfView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(ImageOrPdfActivity.this).load(file).into(imageView);
                } else if (coursevideo.getMediatype().equals("pdf")) {
                    pdfView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    initPDf(file);
                }
            }

            @Override
            public void error(Exception e) {
                Log.e("hedb", "error: " + e.getMessage());

            }
        });
    }

    private void initPDf(File file) {
        Uri uri = Uri.fromFile(file);
        pdfView.fromUri(uri)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(false)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
                .load();

    }

    /**
     * 分享图片
     */
    private void shareMoreImages(ArrayList<String> images) {

        ArrayList<Uri> imageUris = new ArrayList<>();
        for (String path : images) {
            imageUris.add(Uri.parse(path));
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        //其中fileUri为文件的标识符
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
        startActivity(shareIntent);
    }

}
