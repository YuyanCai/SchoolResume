package com.schoolrecruit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.schoolrecruit.entity.Company;
import com.schoolrecruit.entity.Job;
import com.schoolrecruit.ov.SearchVO;
import com.schoolrecruit.service.CompanyService;
import com.schoolrecruit.service.JobService;
import com.schoolrecruit.utils.EmailUtils;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/company")
@Api(description = "公司接口")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @Autowired
    private EmailUtils emailUtils;

    private final int pageZise = 3;

    @ResponseBody
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestBody Company company, HttpServletRequest request) {
        //VerificationCode-->loginPassword
        String VerificationCode = (String) request.getSession().getAttribute(company.getEmail() + "VerificationCode");
        Company companyGetOne = companyService.getOne(new QueryWrapper<Company>().eq("email", company.getEmail()));
        //密码或者验证码正确
        if (companyGetOne != null && (company.getLoginPassword().equals(companyGetOne.getLoginPassword()) || company.getLoginPassword().equals(VerificationCode))) {
            if (companyGetOne.getCheckStatus().contains("已禁用")) {
                return Result.fail("该账号已被禁用");
            }
            //登录成功，删除验证码session
            request.getSession().removeAttribute(companyGetOne.getEmail() + "VerificationCode");
            //记录用户session
            HttpSession session = request.getSession();
            session.setAttribute("USER", companyGetOne);
            return Result.succ(200, "登录成功", companyGetOne);
        } else {
            if (companyGetOne == null) {
                return Result.fail("登录失败!邮箱未注册");
            }
            return Result.fail("登录失败!密码、验证码错误");
        }
    }

    @ResponseBody
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public Result register(@RequestBody Company company, HttpServletRequest request) {
        //VerificationCode-->phone
        String VerificationCode = (String) request.getSession().getAttribute(company.getEmail() + "VerificationCode");
        Company companyGetOne = companyService.getOne(new QueryWrapper<Company>().eq("email", company.getEmail()));
        if (companyGetOne == null || VerificationCode.equals(company.getPhone())) {
            //注册成功，删除验证码session
            request.getSession().removeAttribute(company.getEmail() + "VerificationCode");
            //记录用户session
            HttpSession session = request.getSession();
            session.setAttribute("USER", company);
            company.setPhone(null);
            company.setCheckStatus("未完善");
            company.setPortrait("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
            companyService.save(company);
            return Result.succ(200, "注册成功！", company);
        } else {
            if (companyGetOne != null) {
                return Result.fail("该邮箱已注册过了");
            }
            return Result.fail("验证码错误");
        }
    }

    @ResponseBody
    @PostMapping("/logout/{email}")
    @ApiOperation(value = "退出")
    public Result logout(@PathVariable("email") String email, HttpServletRequest request) {
        request.getSession().removeAttribute("USER");
        return Result.succ(200, "退出成功", null);
    }


    @ResponseBody
    @PostMapping("/update")
    @ApiOperation(value = "修改信息")
    public Result update(@RequestBody Company company, HttpServletRequest request) {
        if (!company.getBusinessLicense().equals("") || (company.getCheckStatus().contains("已退回") || company.getCheckStatus().contains("未完善"))) {
            company.setCheckStatus("待审核");
        } else {
            company.setCheckStatus(null);
        }
        if (companyService.updateById(company)) {
            HttpSession session = request.getSession();
            session.setAttribute("USER", company);
            return Result.succ(200, "修改成功", companyService.getById(company.getId()));
        } else {
            return Result.succ(400, "修改失败", null);
        }
    }

    @ResponseBody
    @PostMapping("/showCompanyById/{id}")
    @ApiOperation(value = "通过id获取公司信息")
    public Result showCompanyById(@PathVariable("id") Long id) {
        return Result.succ(companyService.getById(id));
    }

    @ResponseBody
    @PostMapping("/manageCompany/{page}/{checkStatus}/{companyName}")
    @ApiOperation(value = "管理公司用户")
    public Result manageCompany(@PathVariable("page") Integer page, @PathVariable("checkStatus") String checkStatus, @PathVariable("companyName") String companyName) {
        int count = companyService.count(new QueryWrapper<Company>()
                .like(!"null".equals(checkStatus), "check_status", checkStatus)
                .like(!"null".equals(companyName), "company_name", companyName));
        int pageAll = count % 5 == 0 ? count / 5 : count / 5 + 1;
        page = pageAll < page ? 1 : page;
        return Result.succ(companyService.page(new Page<Company>(page, 5), new QueryWrapper<Company>()
                .like(!"null".equals(checkStatus), "check_status", checkStatus)
                .like(!"null".equals(companyName), "company_name", companyName)
        ));
    }

    @ResponseBody
    @PostMapping("/updateByAdmin")
    @ApiOperation(value = "管理员审核企业信息")
    public Result updateByAdmin(@RequestBody Company company) {
        if (companyService.updateById(company)) {
            emailUtils.sendSimpleMail(company.getEmail(), "校园招聘系统审核通知", "你的公司审核状态被修改为：" + company.getCheckStatus());
            if (company.getCheckStatus().contains("已禁用")) {
                jobService.update(new Job().setRecruitmentStatus("停止招聘"), new QueryWrapper<Job>().eq("company_id", company.getId()));
            }
            if (company.getCheckStatus().contains("已通过")) {
                jobService.update(new Job().setRecruitmentStatus("正在招聘"), new QueryWrapper<Job>().eq("company_id", company.getId()));
            }
            return Result.succ("审批成功");
        } else {
            return Result.succ(400, "审批失败", null);
        }
    }

    @ResponseBody
    @PostMapping("/findCompany")
    @ApiOperation(value = "模糊检索公司")
    public Result findCompany(@RequestBody SearchVO searchVO) {
        List<Company> companyList = companyService.list(new QueryWrapper<Company>()
                .like(searchVO.getJobName() != null, "company_name", searchVO.getJobName())
                .eq("check_status", "已通过")
                .like(searchVO.getScale() != null, "scale", searchVO.getScale())
                .like(searchVO.getNature() != null, "nature", searchVO.getNature()));
        int pageAll = companyList.size() % 5 == 0 ? pageAll = companyList.size() / 5 : companyList.size() / 5 + 1;
        if (pageAll < searchVO.getCrrPage()) {
            searchVO.setCrrPage(pageAll);
        }
        Page<Company> companyPage = (Page<Company>) companyService.page(new Page<Company>(searchVO.getCrrPage(), 5), new QueryWrapper<Company>()
                .like(searchVO.getJobName() != null, "company_name", searchVO.getJobName())
                .eq("check_status", "已通过")
                .like(searchVO.getScale() != null, "scale", searchVO.getScale())
                .like(searchVO.getNature() != null, "nature", searchVO.getNature())
                .orderByDesc("id"));
        List<SearchVO> searchVOList = new ArrayList<>();

        for (Company company : companyPage.getRecords()) {
            SearchVO search = new SearchVO();
            search.setCompanyId(company.getId());
            search.setCompanyName(company.getCompanyName());
            search.setPortrait(company.getPortrait());
            search.setNature(company.getNature());
            search.setScale(company.getScale());

            searchVOList.add(search);
        }
        return Result.succ(Long.toString(companyPage.getTotal()), searchVOList);
    }

}
