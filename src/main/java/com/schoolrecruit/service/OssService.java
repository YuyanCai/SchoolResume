package com.schoolrecruit.service;

import com.schoolrecruit.ov.OssTokenVO;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    OssTokenVO getToken(MultipartFile file);
}
