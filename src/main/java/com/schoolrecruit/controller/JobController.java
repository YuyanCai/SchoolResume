package com.schoolrecruit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.schoolrecruit.entity.Company;
import com.schoolrecruit.entity.Job;
import com.schoolrecruit.ov.SearchVO;
import com.schoolrecruit.service.CompanyService;
import com.schoolrecruit.service.JobService;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/job")
@Api(description = "岗位接口")
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;

    private final int pageZise = 7;

    @ResponseBody
    @PostMapping("/getJobByCompanyId")
    @ApiOperation(value = "获取公司所岗位")
    public Result getJobByCompanyId(@RequestBody Company company) {
        List<Job> job = jobService.list(new QueryWrapper<Job>().eq("company_id", company.getId()));
        return Result.succ(200, "查询成功", job);
    }

    @ResponseBody
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "添加/修改岗位")
    public Result addOrUpdate(@RequestBody Job job) {

        if (job.getId() == null) {
            //添加
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            job.setSendTime(sdf.format(date));
            job.setRecruitmentStatus("正在招聘");
            if (jobService.save(job)) {
                return Result.succ(jobService.page(new Page<Job>(1, pageZise), new QueryWrapper<Job>()
                        .eq("company_id", job.getCompanyId())
                        .orderByDesc("id")));
            } else {
                return Result.fail("操作失败");
            }
        } else {
            //修改
            if (jobService.updateById(job)) {
                return Result.succ(jobService.page(new Page<Job>(1, pageZise), new QueryWrapper<Job>()
                        .eq("company_id", job.getCompanyId())
                        .orderByDesc("id"))
                );
            } else {
                return Result.fail("操作失败");
            }
        }

    }

    @ResponseBody
    @PostMapping("/delete")
    @ApiOperation(value = "删除岗位")
    public Result delete(@RequestBody Job job) {
        if (jobService.removeById(job.getId())) {
            return Result.succ(200, "删除成功", jobService.page(new Page<Job>(1, pageZise), new QueryWrapper<Job>()
                    .eq("company_id", job.getCompanyId())
                    .orderByDesc("id")));
        } else {
            return Result.fail("删除失败");
        }
    }

    @ResponseBody
    @PostMapping("/find/{company_id}/{job_name}/{crrPage}/{status}")
    @ApiOperation(value = "查询公司岗位分页")
    public Result find(@PathVariable("company_id") String company_id, @PathVariable("job_name") String job_name, @PathVariable("crrPage") Integer crrPage, @PathVariable("status") String status) {
        /*int count = jobService.count(new QueryWrapper<Job>()
                .eq("company_id",company_id)
                .like(!"null".equals(job_name),"job_name",job_name)
                .eq("正在招聘".equals(status),"recruitment_status",status)
                .orderByDesc("id"));
        int pageAll= count%pageZise==0 ? count/pageZise : count/pageZise+1;
        crrPage = pageAll<crrPage?1:crrPage;*/

        return Result.succ(jobService.page(new Page<Job>(crrPage, pageZise), new QueryWrapper<Job>()
                .eq("company_id", company_id)
                .like(!"null".equals(job_name), "job_name", job_name)
                .eq("正在招聘".equals(status), "recruitment_status", status)
                .orderByDesc("id")));
    }

    @ResponseBody
    @PostMapping("/findSearch")
    @ApiOperation(value = "条件查询岗位")
    public Result findSearch(@RequestBody SearchVO searchVO) {
        List<Company> companyList = companyService.list(new QueryWrapper<Company>()
                .eq("check_status", "已通过")
                .like(searchVO.getScale() != null, "scale", searchVO.getScale())
                .like(searchVO.getNature() != null, "nature", searchVO.getNature()));
        List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                .eq("recruitment_status", "正在招聘")
                .like(searchVO.getPlace() != null, "place", searchVO.getPlace())
                .like(searchVO.getEducation() != null, "education", searchVO.getEducation())
                .like(searchVO.getJobName() != null, "job_name", searchVO.getJobName()));
        List<SearchVO> searchVOList = new ArrayList<>();
        for (Company company : companyList) {
            for (Job job : jobList) {
                if (company.getId().equals(job.getCompanyId())) {
                    SearchVO search = new SearchVO();
                    search.setJobId(job.getId());
                    search.setJobName(job.getJobName());
                    search.setPlace(job.getPlace());
                    search.setEducation(job.getEducation());

                    search.setCompanyId(company.getId());
                    search.setCompanyName(company.getCompanyName());
                    search.setPortrait(company.getPortrait());
                    search.setNature(company.getNature());
                    search.setScale(company.getScale());

                    searchVOList.add(search);
                }
            }
        }
        //去重
        searchVOList = searchVOList.stream().distinct().collect(Collectors.toList());
        return Result.succ(searchVOList);
    }

    @ResponseBody
    @PostMapping("/showById/{id}")
    @ApiOperation(value = "通过id岗位详情")
    public Result delete(@PathVariable("id") Long id) {
        return Result.succ(jobService.getById(id));
    }

    //ResponseBody的作用就是将处理完的结果封装成json数据给前端
    @ResponseBody
    @PostMapping("/findSearchPage")
    @ApiOperation(value = "条件查询岗位")
//    RequestBody的作用就是将前端传来的json数据转换为Java对象(JavaBean)的形式
    public Result findSearchPage(@RequestBody SearchVO searchVO) {
        int min = 0, max = Integer.MAX_VALUE;
        if (!(searchVO.getSalary() == null || searchVO.getSalary() == "")) {
            String salary = searchVO.getSalary();
            String salarys[] = salary.split("-");
            if (salarys.length == 1) {
                if (salarys[0].contains("以上")) {
                    min = Integer.parseInt(String.valueOf(salarys[0].charAt(0))) * 1000;
                } else {
                    max = Integer.parseInt(String.valueOf(salarys[0].charAt(0))) * 1000;
                }
            } else {
                min = Integer.parseInt(String.valueOf(salarys[0].charAt(0))) * 1000;
                max = Integer.parseInt(String.valueOf(salarys[1].charAt(0))) * 1000;
            }
        }

        List<Company> companyList = companyService.list(new QueryWrapper<Company>()
                .eq("check_status", "已通过")
                .like(searchVO.getScale() != null, "scale", searchVO.getScale())
                .like(searchVO.getNature() != null, "nature", searchVO.getNature()));

        List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                .eq("recruitment_status", "正在招聘")
                .like(searchVO.getPlace() != null, "place", searchVO.getPlace())
                .like(searchVO.getEducation() != null, "education", searchVO.getEducation())
                .like(searchVO.getJobName() != null, "job_name", searchVO.getJobName())
                .between(searchVO.getSalary() != null, "salary", min, max)
                .orderByDesc("id")
        );

        List<SearchVO> searchVOList = new ArrayList<>();

        for (Job job : jobList) {
            for (Company company : companyList) {
                if (company.getId().equals(job.getCompanyId())) {
                    SearchVO search = new SearchVO();
                    search.setJobId(job.getId());
                    search.setJobName(job.getJobName());
                    search.setPlace(job.getPlace());
                    search.setSalary(Long.toString(job.getSalary()));
                    search.setEducation(job.getEducation());

                    search.setCompanyId(company.getId());
                    search.setCompanyName(company.getCompanyName());
                    search.setPortrait(company.getPortrait());
                    search.setNature(company.getNature());
                    search.setScale(company.getScale());

                    searchVOList.add(search);
                }
            }
        }

        //去重
        searchVOList = searchVOList.stream().distinct().collect(Collectors.toList());
        int total = searchVOList.size();

        //固定写法
        int pageZise = 5;
        int crrPage = searchVO.getCrrPage();
        int pageAll = total % pageZise == 0 ? total / pageZise : total / pageZise + 1;
        crrPage = pageAll < crrPage ? 1 : crrPage;
        if ((crrPage - 1) * pageZise + pageZise > total) {
            searchVOList = searchVOList.subList((crrPage - 1) * pageZise, total);
        } else {
            searchVOList = searchVOList.subList((crrPage - 1) * pageZise, (crrPage - 1) * pageZise + pageZise);
        }
        return Result.succ(Integer.toString(total), searchVOList);
    }

}
