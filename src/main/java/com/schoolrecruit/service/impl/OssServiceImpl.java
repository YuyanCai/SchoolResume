package com.schoolrecruit.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.schoolrecruit.config.Ali0ssConfig;
import com.schoolrecruit.ov.OssTokenVO;
import com.schoolrecruit.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private Ali0ssConfig ali0ssConfig;

    public OssTokenVO getToken(MultipartFile file){
        //构造请求器
        DefaultProfile profile = DefaultProfile.getProfile(ali0ssConfig.getRegionID(),
                ali0ssConfig.getAccessKeyId(),
                ali0ssConfig.getAccessKeySecret());
        DefaultAcsClient client = new DefaultAcsClient(profile);
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleSessionName("lkj-weboss");
        request.setRoleArn(ali0ssConfig.getRoleArn());
        request.setDurationSeconds(1000L);
        AssumeRoleResponse acsResponse = null;
        try {
            acsResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }

        //获取上传文件的url
        String url;

        try {
            url = testOss(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //拿到前端需要的数据了
        AssumeRoleResponse.Credentials credentials = acsResponse.getCredentials();
        String accessKeyId = credentials.getAccessKeyId();
        String accessKeySecret = credentials.getAccessKeySecret();
        String securityToken = credentials.getSecurityToken();
        //构建前端需要的vo
        return OssTokenVO.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .stsToken(securityToken)
                .region("oss-" + ali0ssConfig.getRegionID())
                .bucket(ali0ssConfig.getBucket())
                .url(url)
                .build();

    }

    public String testOss(MultipartFile file) throws Exception {
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ali0ssConfig.getAccessKeyId();
        String accessKeySecret = ali0ssConfig.getAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ali0ssConfig.getBucket();

        String filename = "";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
//            1. 获取文件输入流
            InputStream inputStream = file.getInputStream();
//            2. 获取文件名称
            filename = UUID.randomUUID().toString().replaceAll("-", "");

//            3. 上传文件
            ossClient.putObject(bucketName, filename, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        String url = "https://pyy-mall.oss-cn-hangzhou.aliyuncs.com/" + filename;
        return url;
    }

}
