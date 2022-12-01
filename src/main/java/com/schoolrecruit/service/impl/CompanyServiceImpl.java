package com.schoolrecruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schoolrecruit.entity.Company;
import com.schoolrecruit.mapper.CompanyMapper;
import com.schoolrecruit.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
