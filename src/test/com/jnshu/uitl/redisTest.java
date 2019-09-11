package com.jnshu.uitl;

import com.google.gson.Gson;
import com.jnshu.uitl.encoder.DateUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath*:*applicationContext.xml")//加载配置文件
public class redisTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /*@Test
     void redis(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");

        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set("name_1","名字1");
        System.out.println(jedis.get("name_1"));
    }*/

    @Test
    void redisSpring() {
        redisTemplate.opsForValue().set("name_2", "名字2");
        System.out.println(redisTemplate.opsForValue().get("name_2"));
        /*System.out.println(redisTemplate.opsForValue().get("1"));*/
    }

    @Test
    void timeTest() {
        System.out.println(DateUtil.dateFormat());
    }

    @Test
    void ssl() throws IOException {
        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个get请求
        HttpGet httpget = new HttpGet("http://49.235.29.239:8080/hello");
        //3.执行get请求并返回结果
        CloseableHttpResponse response = httpclient.execute(httpget);
        /*返回获取到的页面*/
        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        /*不管程序运行成不成功，都要把连接关闭*/
        response.close();
    }

    /**
     * post请求，添加请求参数
     */
    @Test
    void postHttp() throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost("http://49.235.29.239:8080/hello/registered");
        // 设置2个post参数，一个是scope、一个是q
        List parameters = new ArrayList<>(0);
        parameters.add(new BasicNameValuePair("account", "zms"));
        parameters.add(new BasicNameValuePair("pwd", "123456"));
        parameters.add(new BasicNameValuePair("name", "8-21-httpClient"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        //伪装浏览器
        //httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            /*网页状态码*/
            System.out.println(response.getStatusLine().getStatusCode());

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                //内容写入文件
                //FileUtils.writeStringToFile(new File("E:\\devtest\\oschina-param.html"), content, "UTF-8");
                System.out.println("内容长度：" + content.length());
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }

    /**
     * 随机数
     */
    @Test
    void sui() {
        /*1000-9999的随机数*/
        //logger.info("{}", (int) ((Math.random()*9+1)*1000));

        /*四位随机数，可用0开头*/
        for (int i = 0; i < 100; i++) {
            double randomNum = Math.random();
            String sub = String.valueOf(randomNum);
            logger.info("取到的随机数：{}--------转后的随机数：{}", randomNum, sub.substring(2, 6));
            if (randomNum < 0.1) {
                break;
            }
        }
    }

    /**
     * Base64转码
     */
    @Test
    void base() {
        String a = "aaaaaaabbbbbbbccccc";
        /*使用Base64编码*/
        Base64.Encoder encoder = Base64.getEncoder();
        /*转码结果*/
        String headerBase64 = encoder.encodeToString(a.getBytes());
        logger.info("转码结果{}", headerBase64);
    }

    /**
     * 讲求第三方接口发送短信验证码
     */
    @Test
    void sms() throws IOException {
        String accountSid = "8aaf07086c8a1560016c951d7f2008ba";
        String accountToken = "6c946f14dbe94699a360da38bcc7e901";
        /*格式化后的当前时间*/
        String dateSms = DateUtil.dateFormat();
        /*要加密的内容*/
        String urlSms = accountSid + accountToken + dateSms;
        /*进行MD5加密转大写*/
        String sigParameter = DigestUtils.md5DigestAsHex(urlSms.getBytes()).toUpperCase();
        logger.info("加密后的请求包头SigParameter:{}", sigParameter);
        /*请求包头？url*/
        String url = "https://app.cloopen.com:8883/2013-12-26/Accounts/" + accountSid + "/SMS/TemplateSMS?sig=" + sigParameter;
        /*包头字段的组合*/
        String header = accountSid + ":" + dateSms;
        /*获取四位以千为单位的随机数字，1000-9999*/
        //String randomNum=String.valueOf((int) ((Math.random()*9+1)*1000));
        /*获取四们随机数字*/
        String randomNum = String.valueOf(Math.random()).substring(2, 6);
        /*使用Base64转码*/
        Base64.Encoder encoder = Base64.getEncoder();
        /*转码结果*/
        String headerBase64 = encoder.encodeToString(header.getBytes());
        logger.info("转码结果{}", headerBase64);
        /*body的内容*/
        HashMap<String, Object> body = new HashMap<>();
        body.put("to", "18811055925");
        body.put("appId", "8a216da86c8a1a54016c9543e91a0836");
        body.put("templateId", "1");
        body.put("datas", new String[]{randomNum, "3"});

        /*body转json格式*/
        Gson gson = new Gson();
        String bodyJson = gson.toJson(body);
        logger.info("转成json后的内容：{}", bodyJson);

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        /*设置头字段*/
        /*httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("Content-Length", "Content-Length");*/
        httpPost.setHeader("Authorization", headerBase64);

        /*创建传输的实体，设置编码格式，设置数据类型*/
        StringEntity stringEntity = new StringEntity(bodyJson);
        stringEntity.setContentEncoding("utf-8");
        stringEntity.setContentType("application/json");
        /*将要传输的实体填充至httpPost中*/
        httpPost.setEntity(stringEntity);


        try {
            /*开始进行请求，获取响应内容*/
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            Validate.isTrue(code==200,"连接服务器不成功");
            /*获取响应内容实体*/
            HttpEntity httpEntity= response.getEntity();
            /*转换响应内容*/
            String responseEntiy=EntityUtils.toString(httpEntity);
            /*创建Document对象，对要解析的内容操作*/
            Document document = DocumentHelper.parseText(responseEntiy);
            /*获取根节点*/
            Element rootElement = document.getRootElement();
            /*提取名为statusMsg（节点元素）标签里的内容*/
            Element msgElement = rootElement.element("statusMsg");
            Element codeElement = rootElement.element("statusCode");
            logger.info("msg节点:{}，code节点:{}",msgElement.getText(),codeElement.getTextTrim());
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ExceptionInInitializerError e){
            String err=e.getMessage();
            logger.info("{}",err);
        }finally {
            /*关闭连接*/
            httpclient.close();
        }
    }

    /*通过spring配置文件引入config.properties里的参数*/
    @Value("${api_user}")
    private String apiUser;

    @Test
    void testValue() {
        System.out.println(apiUser);
    }

    @Test
    void mail() throws IOException {
        /*请求的接口*/
        String url = "http://api.sendcloud.net/apiv2/mail/send";
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);

        /*请求参数列表*/
        List parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("apiUser", "zms514930185_test_m7Iydd"));
        parameters.add(new BasicNameValuePair("apiKey", "9uKJMjae3JovrpB4"));
        parameters.add(new BasicNameValuePair("from", "zhangmingshun1991@foxmail.com"));//发送地址
        parameters.add(new BasicNameValuePair("to", "704559364@qq.com"));//接收地址
        parameters.add(new BasicNameValuePair("subject", "注册验证码"));//标题
        parameters.add(new BasicNameValuePair("html", "<html><p>您的验证码是：1314</p></html>"));//邮件内容
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "utf-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            /*网页状态码*/
            System.out.println(response.getStatusLine().getStatusCode());
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                //内容写入文件
                //FileUtils.writeStringToFile(new File("E:\\devtest\\oschina-param.html"), content, "UTF-8");
                System.out.println("内容：" + content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }


    /*七牛云
    @Test
    void qiniu() {
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create("4rNcKV-yYwg3VnMYXkNRMJmsHjFPo-FVIpokcxLl", "NElCyBmB9TRHpqhtg476y5elJp1hUphZn6r8JLE-");
        String token = auth.uploadToken("jnshu_img");
        System.out.println(token);
        Response response = uploadManager.put("hello world".getBytes(), "yourkey", token);
    }*/

    @Value("${COS_SECRETID}")
    public String COS_SECRETID;
    @Value("${COS_SECRETKEY}")
    private String COS_SECRETKEY;
    @Value("${COS_region}")
    private String COS_region;
    @Value("${COS_bucketName}")
    private String COS_bucketName;

    @Test
    void qCloud() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = COS_SECRETID;
        String secretKey = COS_SECRETKEY;
        /*区域*/
        String cosRegion = COS_region;

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(cosRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            // 指定要上传的文件
            File localFile = new File("D:/1.jpg");
            // 指定要上传到的存储桶 /*bucketName：存储桶名称，即存储空间名称*/
            String bucketName = COS_bucketName;
            // 指定要上传到 COS 上对象键,用UUID
            String key = UUID.randomUUID().toString();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key+".jpg", localFile);

           /* // 方法2 从输入流上传(需提前告知输入流的长度, 否则可能导致 oom)
            FileInputStream fileInputStream = new FileInputStream(localFile);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度为500
            objectMetadata.setContentLength(500);
            // 设置 Content type, 默认是 application/octet-stream
            objectMetadata.setContentType("application/pdf");
            PutObjectResult putObjectResult = cosClient.putObject(bucketName, key, fileInputStream, objectMetadata);
            String etag = putObjectResult.getETag();
            // 关闭输入流...*/



            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        } catch (CosClientException serverException) {
            serverException.printStackTrace();
        }
    }

    /**
     * UUID
     * */
    @Test
    void uuid() {
        String key = UUID.randomUUID().toString();
        System.out.println(key);
    }

    /*@Test
    void validate(){
        Validate.matchesPattern("^1(3|4|5|7|8)\\d{9}$");
    }*/

    @Test
    void date235959(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        long tt = calendar.getTime().getTime();
        System.out.println(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH)+""+calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(tt);
        System.out.println(System.currentTimeMillis());

        LocalDate dat= new LocalDate(1567425346000L);
        System.out.println(dat);
        boolean da=dat.equals(new LocalDate());
        System.out.println(da);
    }

    @Test
    void pattern(){
        String account = "a514930";
        String pwd;
        String name;
        String phone;
        String code;
        Validate.matchesPattern(account,"^([A-Za-z]+[\\w]*){5,15}","帐号不可用");
    }



}
