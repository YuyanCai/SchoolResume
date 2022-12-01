package com.schoolrecruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schoolrecruit.entity.Job;
import com.schoolrecruit.mapper.JobMapper;
import com.schoolrecruit.service.JobService;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}
