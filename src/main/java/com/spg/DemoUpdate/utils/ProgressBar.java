package com.spg.DemoUpdate.utils;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressListener;

public class ProgressBar implements ProgressListener{

    private long bytesWritten = 0;
    private long totalBytes = -1;
    private boolean succeed = false;

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        switch (progressEvent.getEventType()){
            case TRANSFER_STARTED_EVENT:
                // 开始传输
                System.out.println("Uploading start");
                break;
            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                break;
            case REQUEST_BYTE_TRANSFER_EVENT:
                // 传输中
                break;
            case TRANSFER_COMPLETED_EVENT:
                // 传输完成
                break;
            case TRANSFER_FAILED_EVENT:
                // 传输失败
                break;
            default:
                break;
        }
    }
}
