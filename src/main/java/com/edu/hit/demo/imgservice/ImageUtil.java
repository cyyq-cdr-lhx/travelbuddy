package com.edu.hit.demo.imgservice;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class ImageUtil {

    public String accessKey = "vJmez3UEKccpiHcgeVEyZYzDf3p2ohkd0Ocj3CnT";
    public String secretKey = "wYPlFRgZem1bAmuAgiFwAOyLFl2q6LlA88_dSx2w";
    // 空间名称
    public String bucket = "travelbuddyimage";


    public String uploadImage(byte[] imageFile, String email){
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Configuration cfg = new Configuration(Region.region1());
        UploadManager uploadManager = new UploadManager(cfg);
        //将图片md5的值作为图片名，避免重复图片浪费空间
        String key = email + DigestUtils.md5DigestAsHex(imageFile);
        ByteArrayInputStream byteInputStream=new ByteArrayInputStream(imageFile);
        try {
            uploadManager.put(byteInputStream,key,upToken,null, null);
            return key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println("七牛云ERROR:" + r.toString());

            try {
                System.err.println("七牛云ERROR:" + r.bodyString());
                return null;

            } catch (QiniuException ex2) {
                //ignore
                return  null;
            }
        }
    }






    }





