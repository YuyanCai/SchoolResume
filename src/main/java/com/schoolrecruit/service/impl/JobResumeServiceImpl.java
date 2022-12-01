package com.schoolrecruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schoolrecruit.entity.JobResume;
import com.schoolrecruit.mapper.JobResumeMapper;
import com.schoolrecruit.service.JobResumeService;
import org.springframework.stereotype.Service;

@Service
public class JobResumeServiceImpl extends ServiceImpl<JobResumeMapper, JobResume> implements JobResumeService {

}
