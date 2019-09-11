package com.jnshu.uitl.encoder;

import com.google.gson.Gson;
import org.apache.commons.lang3.Validate;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 容联发送短信验证码方法
 */
@Component
public class SMS {

    @Value("${sms_accountSid}")
    public String accountSid;
    @Value("${sms_accountToken}")
    public String accountToken;
    @Value("${sms_appId}")
    public String appId;

    /**
     * @param phone 手机号
     * @return map
     * map.get("num") 四位随机验证码
     * map.get("code")响应的状态码
     * map.get("msg")响应的状态说明
     */
    public Map sms(String phone) throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        /*要返回的Map*/
        Map<String, String> map = new HashMap<String, String>();

        /*生成四位验证码的两种方式*/
        /*获取四位以千为单位的随机数字，1000-9999*/
        //String randomNum=String.valueOf((int) ((Math.random()*9+1)*1000));
        /*获取四位随机数字*/
        String randomNum = String.valueOf(Math.random()).substring(2, 6);
        map.put("num", randomNum);

        try {
            /*格式化后的当前时间*/
            String dateSms = DateUtil.dateFormat();
            /*要加密的内容*/
            String urlSms = accountSid + accountToken + dateSms;
            /*进行MD5加密并且转为大写*/
            String sigParameter = DigestUtils.md5DigestAsHex(urlSms.getBytes()).toUpperCase();
            /*组装的url*/
            String url = "https://app.cloopen.com:8883/2013-12-26/Accounts/" + accountSid + "/SMS/TemplateSMS?sig=" + sigParameter;
            /*包头字段的组合*/
            String header = accountSid + ":" + dateSms;
            /*对包头字段按要求使用Base64转码*/
            Base64.Encoder encoder = Base64.getEncoder();
            /*转码结果*/
            String headerBase64 = encoder.encodeToString(header.getBytes());
            /*body的内容,手机号，应用对应ID，模板编号，要发送的内容（验证码与过期时间）*/
            HashMap<String, Object> body = new HashMap<>();
            body.put("to", phone);
            body.put("appId", appId);
            body.put("templateId", "1");
            body.put("datas", new String[]{randomNum, "3"});

            /*body转json格式*/
            Gson gson = new Gson();
            String bodyJson = gson.toJson(body);

            // 创建http POST请求
            HttpPost httpPost = new HttpPost(url);
            /*设置头字段*/
            httpPost.setHeader("Authorization", headerBase64);

            /*创建传输的实体，设置编码格式，设置数据类型*/
            StringEntity stringEntity = new StringEntity(bodyJson);
            stringEntity.setContentEncoding("utf-8");
            stringEntity.setContentType("application/json");
            /*将要传输的实体填充至httpPost中*/
            httpPost.setEntity(stringEntity);

            /*开始进行请求，获取响应内容*/
            CloseableHttpResponse response = httpclient.execute(httpPost);
            /*响应码*/
            int code = response.getStatusLine().getStatusCode();
            Validate.isTrue(code == 200, "连接服务器不成功");
            /*获取响应内容实体,并转换响应内容类型*/
            String entity = EntityUtils.toString(response.getEntity());
            /*创建Document对象，对要解析的内容操作*/
            Document document = DocumentHelper.parseText(entity);
            /*获取根节点*/
            Element rootElement = document.getRootElement();
            /*提取指定节点里的内容，*/
            Element codeElement = rootElement.element("statusCode");
            String responseCode = codeElement.getTextTrim();
            String trueCode="000000";
            if(trueCode.equals(responseCode)){
                map.put("msg", "请求发送短信成功");
            }else {
                map.put("msg", rootElement.element("statusMsg").getTextTrim());
            }
            map.put("code", responseCode);
            return map;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ExceptionInInitializerError e) {
            map.put("msg", e.getMessage());
        } finally {
            /*关闭连接*/
            httpclient.close();
        }
        return map;
    }
}
