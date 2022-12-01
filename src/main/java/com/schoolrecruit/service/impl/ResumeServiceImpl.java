package com.schoolrecruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schoolrecruit.entity.Resume;
import com.schoolrecruit.mapper.ResumeMapper;
import com.schoolrecruit.service.ResumeService;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

}
