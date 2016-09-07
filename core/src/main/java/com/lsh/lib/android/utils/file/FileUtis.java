package com.lsh.lib.android.utils.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.lsh.lib.android.utils.log.CLog;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * 文件操作类
 * 利用Okio的Sink和Source处理
 * author：liush
 * version：2016/3/2  21:20
 */
public class FileUtis {
    public static String TAG = FileUtis.class.getName();
    public static final int SDCard_DOWNLOAD = 0;
    public static final int SDCard_DCIM = 1;
    public static final int SDCard_DOCUMENTS = 2;
    public static final int SDCard_MOVIES = 3;
    public static final int SDCard_MUSIC = 4;
    public static final int SDCard_PICTURES = 5;

    /**
     * 读取文件，查看内容
     *
     * @param filePath
     */
    public static void readFile(String filePath) {
        Source source = null;
        BufferedSource bufferedSource = null;
        try {
            File file = new File(filePath);
            source = Okio.source(file);
            bufferedSource = Okio.buffer(source);
            String result = bufferedSource.readUtf8();
            CLog.e(TAG, result);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            close(bufferedSource);
        }

    }

    /**
     * 读取assets目录下的文件
     *
     * @param path
     */
    public static String readAssetsFile(Context context, String path) {
        InputStream is = null;
        try {
            is = context.getAssets().open(path);
            return streamToString(is);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 流转换成string
     *
     * @param is InputStream
     * @return
     */
    public static String streamToString(InputStream is) {
        Source source = null;
        BufferedSource bufferedSource = null;
        try {
            source = Okio.source(is);
            bufferedSource = Okio.buffer(source);
            String content = bufferedSource.readUtf8();
            CLog.e(TAG, "streamToString:" + content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            close(is);
            close(bufferedSource);
        }
    }

    /**
     * 写入文件
     *
     * @param file   文件
     * @param source 资源
     * @return
     */
    public static boolean writeOnFile(File file, byte[] source) {
        Sink sink = null;
        BufferedSink bufferedSink = null;
        try {
            sink = Okio.sink(file);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.write(source);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(bufferedSink);
        }
    }

    /**
     * 写入文件
     *
     * @param file 文件
     * @param is
     * @return
     */
    public static boolean writeOnFile(File file, InputStream is) {
        Sink sink = null;
        Source source = null;
        BufferedSink bufferedSink = null;
        try {
            source = Okio.source(is);
            sink = Okio.sink(file);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.writeAll(source);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(bufferedSink);
        }
    }

    /**
     * 写入图片
     *
     * @param file   文件
     * @param bitmap 图片
     * @return
     */
    public static void writeBitmap(File file, Bitmap bitmap) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param from 资源文件
     * @param to   目标文件
     */
    public static void writeOnFile(File from, File to) {
        Source source = null;
        BufferedSource bufferedSource = null;
        Sink sink = null;

        BufferedSink bufferedSink = null;
        try {
            source = Okio.source(from);
            bufferedSource = Okio.buffer(source);
            sink = Okio.sink(to);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.writeAll(bufferedSource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bufferedSource);
            close(bufferedSink);
        }
    }


    /**
     * 在SDCard中的DownLoad、music、movie、pictures等目录下创建一个文件
     *
     * @param type     类型
     * @param fileName 文件名
     * @return 文件
     */
    public static File createSDCardFile(int type, String fileName) {
        File folder = new File("/storage/emulated/0/themaker");//文件夹
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            if (type == SDCard_DCIM) {
                folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            } else if (type == SDCard_DOCUMENTS) {
                folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            } else if (type == SDCard_DOWNLOAD) {
                folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            } else if (type == SDCard_MOVIES) {
                folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            } else if (type == SDCard_MUSIC) {
                folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            } else if (type == SDCard_PICTURES) {
                folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            } else {
                folder = Environment.getExternalStorageDirectory();
            }
            if (!folder.mkdirs()) {
                folder.mkdirs();
            }
        }
        return new File(folder, fileName);
    }


    /**
     * 创建缓存目录文件
     *
     * @param context
     * @param name    文件名
     * @return 缓存文件
     */
    public static File createCacheFile(Context context, String name) {
        File folder = new File("/data/data/0/themaker");//文件夹
        if (Environment.getExternalStorageState().endsWith(Environment.MEDIA_MOUNTED)) {
            folder = context.getExternalCacheDir();//外部存储z中应用程序的缓存目录
        } else {
            folder = context.getCacheDir();//程序指定的缓存目录
        }
        if (!folder.mkdirs()) {
            folder.mkdirs();
        }
        return new File(folder, name);
    }

    // 使用系统当前日期加以调整作为照片的名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 类似关闭流
     *
     * @param closeable BufferedSource、BufferedSink
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
