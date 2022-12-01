package com.schoolrecruit.controller;

import com.schoolrecruit.ov.OssTokenVO;
import com.schoolrecruit.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss")
@Api(description = "OSS接口")
public class OssController {
    @Autowired
    private OssService ossService;
    
    @PostMapping("/getToken")
    @ApiOperation(value = "登录")
    public OssTokenVO getToken(MultipartFile file) {
        //获取上传文件 MultipartFile file

        return ossService.getToken(file);
    }
}
