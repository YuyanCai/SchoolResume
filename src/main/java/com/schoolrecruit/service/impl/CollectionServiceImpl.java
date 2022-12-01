package com.schoolrecruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schoolrecruit.entity.Collection;
import com.schoolrecruit.mapper.CollectionMapper;
import com.schoolrecruit.service.CollectionService;
import org.springframework.stereotype.Service;


@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
}
