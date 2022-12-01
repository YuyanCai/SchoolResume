package com.schoolrecruit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.schoolrecruit.entity.*;
import com.schoolrecruit.ov.CollectionResponeVO;
import com.schoolrecruit.service.CollectionService;
import com.schoolrecruit.service.CompanyService;
import com.schoolrecruit.service.JobResumeService;
import com.schoolrecruit.service.JobService;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/collection")
@Api(description = "收藏接口")
public class CollectionController {

    @Autowired
    CollectionService collectionService;
    @Autowired
    JobResumeService jobResumeService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;

    @ResponseBody
    @PostMapping("/add/{jobId}")
    @ApiOperation(value = "添加收藏")
    public Result logout(@PathVariable("jobId")Long jobId, HttpServletRequest request){
        Student student = new Student();
        try{
            student = (Student) request.getSession().getAttribute("USER");
        }catch (Exception e){
            return Result.fail("只有学生才可以收藏岗位");
        }
        Collection collectionOne = collectionService.getOne(new QueryWrapper<Collection>()
                .eq("studentId",student.getId())
                .eq("jobId",jobId)
        );
        if(collectionOne!=null){
            return Result.fail("你已收藏过该岗位");
        }
        Collection collection = new Collection();
        collection.setStudentId(student.getId());
        collection.setJobId(jobId);
        JobResume jobResume = jobResumeService.getOne(new QueryWrapper<JobResume>()
                .eq("studentid",student.getId())
                .eq("jobid",jobId)
        );
        if (jobResume==null){
            collection.setLable("未投递");
        }else{
            collection.setLable("已投递");
        }
        if(collectionService.save(collection)){
            return Result.succ(200,"收藏成功",null);
        }else{
            return Result.succ(400,"收藏失败",null);
        }
    }

    @ResponseBody
    @PostMapping("/find/{crrPage}/{jobName}/{lable}")
    @ApiOperation(value = "条件分页查询收藏")
    public Result logout(@PathVariable("crrPage")int crrPage,@PathVariable("jobName")String jobName,@PathVariable("lable")String lable, HttpServletRequest request){
        Student student = (Student) request.getSession().getAttribute("USER");
        List<Collection> collectionList = collectionService.list(new QueryWrapper<Collection>()
                .eq(!"null".equals(lable),"lable",lable)
                .eq("studentId",student.getId())
                .orderByDesc("id")
        );
        if(collectionList.size()==0){
            return Result.fail("没有符合的记录");
        }
        List<Long> jobId = collectionList.stream().map(Collection::getJobId).collect(Collectors.toList());
        //由岗位id查询岗位名称(岗位详情信息)
        List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                .like(!"null".equals(jobName),"job_name",jobName)
                .in("id",jobId)
        );

        ////由岗位详情信息得到的公司id,去查询公司的详情信息
        List<Long> companyId = jobList.stream().map(Job::getCompanyId).collect(Collectors.toList());
        List<Company> companyList = companyService.list(new QueryWrapper<Company>()
                .in("id",companyId)
        );

        List<CollectionResponeVO> list = new ArrayList<>();
        for (Collection collection : collectionList){
            for (Job job : jobList) {
                if(collection.getJobId()==job.getId()){
                    for (Company company : companyList){
                        if(job.getCompanyId()==company.getId()){
                            CollectionResponeVO collectionResponeVO =  new CollectionResponeVO();
                            //collectionResponeVO= (CollectionResponeVO) collection;
                            collectionResponeVO.setId(collection.getId());
                            collectionResponeVO.setLable(collection.getLable());

                            collectionResponeVO.setCompanyId(company.getId());
                            collectionResponeVO.setCompanyName(company.getCompanyName());
                            collectionResponeVO.setPortrait(company.getPortrait());
                            collectionResponeVO.setCheckStatus(company.getCheckStatus());

                            collectionResponeVO.setJobId(job.getId());
                            collectionResponeVO.setJobName(job.getJobName());
                            collectionResponeVO.setRecruitmentStatus(job.getRecruitmentStatus());

                            list.add(collectionResponeVO);
                        }
                    }
                }
            }
        }

        int total= list.size();
        //(page-1)*pageZise,(page-1)*pageZise+pageZise
        int pageZise = 6;
        if ((crrPage-1)*pageZise+pageZise>total){
            if(total/pageZise<1){
                list=list.subList(0,total);
            }else{
                list=list.subList((crrPage-1)*pageZise,total);
            }
        }else{
            list=list.subList((crrPage-1)*pageZise,(crrPage-1)*pageZise+pageZise);
        }
        return Result.succ(Integer.toString(total),list);
    }

    @ResponseBody
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除收藏")
    private Result delete(@PathVariable("id")Long id,HttpServletRequest request){
        Student student =(Student) request.getSession().getAttribute("USER");
        if(collectionService.removeById(id)){
            return Result.succ("删除成功");
        }else{
            return Result.fail("删除失败");
        }
    }

}
