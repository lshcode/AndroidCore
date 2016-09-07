package com.lsh.lib.android.utils.rx.event;


import com.lsh.lib.android.utils.rx.BusEvent;

/**
 * 文件下载事件
 * author:liush
 * version: 2016/7/11  9:48
 */
public class FileLoadEvent extends BusEvent {
    //文件总长度
    private long total = 0;
    //读取大小
    private long read = 0;

    public FileLoadEvent(long total, long read) {
        this.total = total;
        this.read = read;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRead() {
        return read;
    }

    public void setRead(long read) {
        this.read = read;
    }
}
