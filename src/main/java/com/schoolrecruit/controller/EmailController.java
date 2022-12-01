package com.schoolrecruit.controller;

import com.schoolrecruit.entity.Company;
import com.schoolrecruit.ov.SendEmailVO;
import com.schoolrecruit.service.JobResumeService;
import com.schoolrecruit.utils.EmailUtils;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/email")
@Api(description = "邮件接口")
public class EmailController {
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private JobResumeService jobResumeService;

    @PostMapping("/getverificationcode/{email}")
    @ApiOperation(value = "获取验证码")
    public Boolean getverificationcode(@PathVariable("email")String email, HttpServletRequest request){
        try{
            int VerificationCode = (int)((Math.random()*9+1)*1000);
            System.err.println(email+"---"+VerificationCode);
            emailUtils.sendSimpleMail(email,"校园招聘系统验证码","验证码为["+VerificationCode+"],打死都不要告诉别人哦!");
            System.err.println("send Email Ok!");
            HttpSession session = request.getSession();
            session.setAttribute(email+"VerificationCode", DigestUtils.md5DigestAsHex(Integer.toString(VerificationCode).getBytes()));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @PostMapping("/reply")
    @ApiOperation(value = "回复邮件")
    public Result reply(@RequestBody SendEmailVO sendEmailVO, HttpServletRequest request){
        Company company = (Company) request.getSession().getAttribute("USER");
        sendEmailVO.setContent(sendEmailVO.getContent().replace("[公司]","["+company.getCompanyName()+"]"));
        System.err.println(sendEmailVO);
        try{
            emailUtils.sendSimpleMail(sendEmailVO.getEmail(),sendEmailVO.getTitle(),sendEmailVO.getContent()+"[该邮件由企业操作系统代发，请勿回复！]");
        }catch (Exception e){
            return Result.fail("邮件发送失败");
        }
        //jobResumeService.update(new JobResume().setSendStatus("已回复"),new QueryWrapper<JobResume>().eq("id",sendEmailVO.getId()));
        return Result.succ("邮件发送成功");
    }
}
