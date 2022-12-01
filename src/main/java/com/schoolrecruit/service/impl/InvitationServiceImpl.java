package com.schoolrecruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schoolrecruit.entity.Invitation;
import com.schoolrecruit.mapper.InvitationMapper;
import com.schoolrecruit.service.InvitationService;
import org.springframework.stereotype.Service;

@Service
public class InvitationServiceImpl extends ServiceImpl<InvitationMapper, Invitation> implements InvitationService {

}
