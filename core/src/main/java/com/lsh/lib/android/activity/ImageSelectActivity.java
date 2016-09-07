package com.lsh.lib.android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.lsh.lib.android.utils.file.FileUtis;
import com.lsh.lib.android.R;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 图片选择
 * author:liush
 * version:2016/4/14  17:45
 */
public class ImageSelectActivity extends BActivity
        implements MediaScannerConnection.MediaScannerConnectionClient, View.OnClickListener {
    private static int TAKE_PHOTO = 100;
    private static int SELECT_PHOTO = 101;
    private static int CUT_PHOTO = 102;

    File img = null;
    Uri imgUri = null;
    //    Uri cutUri = null;
    private MediaScannerConnection connection = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_img);

        img = FileUtis.createSDCardFile(FileUtis.SDCard_DCIM, getPhotoFileName());
        imgUri = Uri.fromFile(img);
        connection = new MediaScannerConnection(this, this);
        connection.connect();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            cropImageUri(imgUri, 200, 200, CUT_PHOTO);
        } else if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    try {
                        InputStream io = getContentResolver().openInputStream(uri);
                        FileUtis.writeOnFile(img, io);
                        connection.scanFile(imgUri.getPath(), null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        FileUtis.writeBitmap(img, bitmap);
                        connection.scanFile(imgUri.getPath(), null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == CUT_PHOTO && resultCode == RESULT_OK) {
            if (imgUri != null) {
                connection.scanFile(imgUri.getPath(), null);
            } else
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onMediaScannerConnected() {

    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        Intent intent = new Intent();
        intent.putExtra("FilePath", path);
        setResult(RESULT_OK, intent);
        finish();

    }

    /**
     * 裁剪图片
     *
     * @param uri     图片Uri
     * @param outputX 宽度
     * @param outputY 高度
     */

    private void cropImageUri(Uri uri, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // no face detection
        startActivityForResult(intent, CUT_PHOTO);
    }

    // 可以看到，无论是拍大图片还是小图片，都是使用的Uri，只是尺寸不同而已。我们将这个操作封装在一个方法里面。

    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 2);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", outputX);

        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, requestCode);

    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.disconnect();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int i = v.getId();
        if (i == R.id.btn_take_photo) {//调用相机
            intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            startActivityForResult(intent, TAKE_PHOTO);

        } else if (i == R.id.btn_pick_photo) {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true); // no face detection
            startActivityForResult(intent, SELECT_PHOTO);

        } else if (i == R.id.btn_cancel) {
            finish();

        }
    }
}
