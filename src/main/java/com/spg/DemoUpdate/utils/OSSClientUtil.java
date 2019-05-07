package com.spg.DemoUpdate.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author SPG
 * @version 1.0
 * @time 2019-05-07-15:29
 */
public class OSSClientUtil {


    public static String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    // LTAIsiYRN51xsn0b
    public static String ACCESSKEYID = "LTAIsiYRN51xsn0b";
    // Sz6jBb39XIHJHniCHsJ6wvWDnW1HAP
    public static String ACCESSKEYSECRET = "Sz6jBb39XIHJHniCHsJ6wvWDnW1HAP";
    // spg-test
    public static String BUCKETNAME = "spg-test";

    // 获取ossClient
    public static OSSClient ossClientInit() {
        return new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
    }

    // 是否存在该Bucket
    public static boolean hasBucket(OSSClient ossClient) {
        return ossClient.doesBucketExist(BUCKETNAME);
    }

    /**
     * 创建文件名
     *
     * @param name 名称
     * @return 完整文件名
     */
    public static String createFileName(String name) {
        return name;
    }

    /**
     * 上传文件
     * @param is 文件输入流
     * @param fileName 文件名
     * @return
     */
    public static String uploadFile(InputStream is, String fileName) {
        String result = "";
        try {
            OSSClient ossClient = ossClientInit();
            // 创建上传对象的元数据
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传文件的长度
            metadata.setContentLength(is.available());
            //上传文件
            PutObjectResult putResult = ossClient.putObject(BUCKETNAME, fileName, is);
            //解析结果
            result = putResult.getETag();
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建存储空间
     */
    public static void init() {
        String temp_bucketName = "";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 创建存储空间。
        ossClient.createBucket(temp_bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     *  列举所有的文件
     */
    public static String showFiles(){

        // 文件名前缀，这里列举所有的文件
        String KeyPrefix = "";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 列举文件
        ObjectListing objectListing = ossClient.listObjects(BUCKETNAME, KeyPrefix);
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();

        StringBuilder sb = new StringBuilder();
        sb.append("<br/>");
        sb.append("当前文件总数: ").append(sums.size()).append(" KB").append("<br/><br/>:");
        // 所有文件名
        for (OSSObjectSummary s : sums) {
            sb.append(s.getKey()).append(" ").append(s.getSize()).append("<br/>");
        }
        // 关闭OSSClient
        ossClient.shutdown();
        return sb.toString();
    }

}
