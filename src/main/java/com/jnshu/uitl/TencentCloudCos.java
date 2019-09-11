package com.jnshu.uitl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

@Component
public class TencentCloudCos {

    @Value("${COS_SECRETID}")
    public String COS_SECRETID;
    @Value("${COS_SECRETKEY}")
    private String COS_SECRETKEY;
    @Value("${COS_region}")
    private String COS_region;
    @Value("${COS_bucketName}")
    private String COS_bucketName;

    public String tencentCos(InputStream inputStream, long fileSize, String cntentType) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(COS_SECRETID, COS_SECRETKEY);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        Region region = new Region(COS_region);
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传到 COS 上对象键,用UUID
        String key = UUID.randomUUID().toString();
        try {
            // 指定要上传的文件
            //File localFile = new File("D:/1.jpg");
            // 指定要上传到的存储桶 /*bucketName：存储桶名称，即存储空间名称*/
            String bucketName = COS_bucketName;
            // 方法2 从输入流上传(需提前告知输入流的长度, 否则可能导致 oom)
            //FileInputStream fileInputStream = new FileInputStream(file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度为500
            objectMetadata.setContentLength(fileSize);
            // 设置 Content type, 默认是 application/octet-stream
            objectMetadata.setContentType(cntentType);
            PutObjectResult putObjectResult = cosClient.putObject(bucketName, key, inputStream, objectMetadata);
        } catch (CosClientException serverException) {
            serverException.printStackTrace();
        }
        return key;
    }
}
