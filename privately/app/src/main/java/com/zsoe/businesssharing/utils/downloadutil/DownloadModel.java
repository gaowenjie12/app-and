package com.zsoe.businesssharing.utils.downloadutil;

/**
 * Description: 下载实体类
 */
public class DownloadModel {

    private int progress;
    private long currentFileSize;
    private long totalFileSize;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(long currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(long totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public String toString() {
        return "DownloadModel{" +
                "progress=" + progress +
                ", currentFileSize=" + currentFileSize +
                ", totalFileSize=" + totalFileSize +
                '}';
    }
}
