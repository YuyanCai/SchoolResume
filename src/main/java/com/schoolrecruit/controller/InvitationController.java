package com.schoolrecruit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.schoolrecruit.entity.*;
import com.schoolrecruit.ov.CollectionResponeVO;
import com.schoolrecruit.ov.InvitationResponeVO;
import com.schoolrecruit.service.*;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invitation")
@Api(description = "邀请接口")
public class InvitationController {
    @Autowired
    InvitationService invitationService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    JobService jobService;
    @Autowired
    CompanyService companyService;
    @Autowired
    JobResumeService jobResumeService;
    @Autowired
    StudentService studentService;

    @ResponseBody
    @PostMapping("/add/{jobid}/{resumeid}/{studentId}")
    @ApiOperation(value = "添加邀请")
    public Result logout(@PathVariable("jobid")Long jobid, @PathVariable("resumeid")Long resumeid,@PathVariable("studentId")Long studentId, HttpServletResponse response) throws IOException {
        Invitation invitation = new Invitation();
        invitation.setJobid(jobid);
        invitation.setResumeid(resumeid);
        invitation.setStatus("已邀请");
        if(invitationService.save(invitation)){
            return Result.succ("邀请成功");
        }else{
            return Result.succ("邀请失败");
        }
    }

    @ResponseBody
    @PostMapping("/listByStudentIdTJ/{studentId}/{jobid}")
    @ApiOperation(value = "推荐：通过学生ID查询简历")
    public Result listByStudentIdTJ(@PathVariable("studentId")Long studentId,
                                    @PathVariable("jobid")Long jobid
    ){
        List<Resume> resumeList = resumeService.list(new QueryWrapper<Resume>().eq("studentId",studentId));
        List<Long> resumeId = resumeList.stream().map(Resume::getId).collect(Collectors.toList());
        List<Invitation> invitationList = invitationService.list(new QueryWrapper<Invitation>()
                .in("resumeid",resumeId)
                .eq("jobid",jobid)
        );
        List<InvitationResponeVO>  invitationResponeVOList = new ArrayList<>();
        for(Resume resume : resumeList){
            InvitationResponeVO invitationResponeVO = new InvitationResponeVO();
            invitationResponeVO.setId(resume.getId());
            invitationResponeVO.setStudentId(resume.getStudentId());
            invitationResponeVO.setResumeName(resume.getResumeName());
            invitationResponeVO.setAttachment(resume.getAttachment());
            invitationResponeVO.setUpdateTime(resume.getUpdateTime());
            for(Invitation invitation :invitationList){
                if(resume.getId()==invitation.getResumeid()){
                    invitationResponeVO.setStatus(invitation.getStatus());
                    break;
                }
            }
            if (invitationResponeVO.getStatus()==null||"".equals(invitationResponeVO.getStatus())){
                invitationResponeVO.setStatus("未邀请");
            }
            invitationResponeVOList.add(invitationResponeVO);
        }
        return Result.succ(invitationResponeVOList);
    }


    @ResponseBody
    @PostMapping("/recommendJob/{crrPage}/{companyName}/{status}")
    @ApiOperation(value = "推荐岗位")
    public Result recommendJob(@PathVariable("crrPage") Integer crrPage,
                               @PathVariable("companyName") String companyName,
                               @PathVariable("status") String status,HttpServletRequest request){
        Student student =(Student) request.getSession().getAttribute("USER");
        //查询专业匹配的岗位
        List<Job> jobList = jobService.list(new QueryWrapper<Job>()
                .like("majors",student.getMajor())
        );
        //查询学生的投递记录
        List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>()
                .eq("studentid",student.getId()));
        //移除已投递的岗位
        Iterator<Job> iterator = jobList.iterator();
        while (iterator.hasNext()){
            Job job = iterator.next();
            for (JobResume jobResume:jobResumeList) {
                if(job.getId()==jobResume.getJobid()){
                    iterator.remove();
                }
            }
        }
        if(jobList.size()==0){
            return Result.fail("没有合适的岗位可推荐");
        }
        //查找可以推荐岗位所属企业
        List<Long> companyIdList = jobList.stream().map(Job::getCompanyId).collect(Collectors.toList());
        List<Company> companyList = companyService.list(new QueryWrapper<Company>()
                .in("id",companyIdList)
                .like(!"null".equals(companyName),"company_name",companyName)
        );

        List<CollectionResponeVO> list = new ArrayList<>();
        for (Job job:jobList) {
            for(Company company:companyList){
                if(job.getCompanyId()==company.getId()){
                    CollectionResponeVO collectionResponeVO =  new CollectionResponeVO();

                    collectionResponeVO.setCompanyId(company.getId());
                    collectionResponeVO.setCompanyName(company.getCompanyName());
                    collectionResponeVO.setPortrait(company.getPortrait());
                    collectionResponeVO.setCheckStatus(company.getCheckStatus());

                    collectionResponeVO.setJobId(job.getId());
                    collectionResponeVO.setJobName(job.getJobName());
                    collectionResponeVO.setRecruitmentStatus(job.getRecruitmentStatus());
                    collectionResponeVO.setMajors(job.getMajors());

                    list.add(collectionResponeVO);
                }
            }
        }

        //查找学生简历
        List<Resume> resumeList = resumeService.list(new QueryWrapper<Resume>().in("studentId",student.getId()));
        List<Long> resumeIdList = resumeList.stream().map(Resume::getId).collect(Collectors.toList());
        //查询简历被邀请记录
        List<Invitation> invitationList = invitationService.list(new QueryWrapper<Invitation>().in("resumeid",resumeIdList));

        for (CollectionResponeVO collectionResponeVO:list){
            collectionResponeVO.setTip("");
            for (Invitation invitation:invitationList){
                //如果简历被邀约记录中的岗位ID等于可推荐岗位ID，显示邀约状态
                if(collectionResponeVO.getJobId()==invitation.getJobid()){
                    for (Resume resume : resumeList){
                        if(resume.getId()==invitation.getResumeid()){
                            collectionResponeVO.setTip(collectionResponeVO.getTip()+resume.getResumeName()+"、");
                        }
                    }
                    collectionResponeVO.setLable(invitation.getStatus());
                }
            }
            if(collectionResponeVO.getLable()==null){
                collectionResponeVO.setLable("未邀请");
            }else{
                collectionResponeVO.setTip(collectionResponeVO.getTip().substring(0,collectionResponeVO.getTip().length()-1));
            }
        }

        if(!"null".equals(status)){
            Iterator<CollectionResponeVO> iteratorResponce = list.iterator();
            while (iteratorResponce.hasNext()){
                CollectionResponeVO collectionResponeVO = iteratorResponce.next();
                if(!collectionResponeVO.getLable().equals(status)){
                    iteratorResponce.remove();
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
    @PostMapping("/recommendStudent/{crrPage}/{jobId}/{college}/{studentName}/{status}")
    @ApiOperation(value = "推荐学生")
    public Result recommendStudent(@PathVariable("crrPage") Integer crrPage,
                                   @PathVariable("jobId") Long jobId,
                                   @PathVariable("college")String college,
                                   @PathVariable("studentName")String studentName,
                                   @PathVariable("status")String status,
                                   HttpServletRequest request) {

        Job job = jobService.getById(jobId);
        List<Student> studentList = studentService.list(new QueryWrapper<Student>()
                .in("major",job.getMajors().split(","))
                .eq(!"null".equals(college),"college",college)
                .eq(!"null".equals(studentName),"student_name",studentName)
        );
        if(studentList.size()==0){
            return Result.fail("没有合适的学生可推荐");
        }
        List<Long> studentId = studentList.stream().map(Student::getId).collect(Collectors.toList());
        List<Resume> resumeList = resumeService.list(new QueryWrapper<Resume>().in("studentId",studentId));
        List<Long> resumeIdList = resumeList.stream().map(Resume::getId).collect(Collectors.toList());
        //查询已投递的岗位
        List<JobResume> jobResumeList= jobResumeService.list(new QueryWrapper<JobResume>().in("resumeid",resumeIdList));
        for(JobResume  jobResume:jobResumeList){
            if(jobResume.getJobid()==job.getId()){
                for (Resume resume : resumeList){
                    if(resume.getId()==jobResume.getResumeid()){
                        //移除掉已投递的学生
                        Iterator<Student> iterator = studentList.iterator();
                        while (iterator.hasNext()){
                            Student student = iterator.next();
                            if(student.getId()==resume.getStudentId()){
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        }

        List<Invitation> invitationList = invitationService.list(new QueryWrapper<Invitation>()
                .in("resumeid",resumeIdList)
        );
        for(Student student : studentList){
            student.setLoginPassword("");
            for(Resume resume : resumeList){
                if(student.getId()==resume.getStudentId()){
                    for(Invitation invitation: invitationList){
                        if(invitation.getResumeid()==resume.getId()&&invitation.getJobid()==job.getId()){
                            /*student.setLoginPassword("已邀请");
                            break;*/
                            student.setLoginPassword(student.getLoginPassword()+resume.getResumeName()+"、");
                        }
                    }
                }
            }
            if("".equals(student.getLoginPassword())){
                student.setPhone("未邀请");
            }else{
                student.setPhone("已邀请");
                student.setLoginPassword("["+student.getLoginPassword().substring(0,student.getLoginPassword().length()-1)+"]");
            }
        }

        if(!"null".equals(status)){
            Iterator<Student> iteratorResponce = studentList.iterator();
            while (iteratorResponce.hasNext()){
                Student student = iteratorResponce.next();
                if(!student.getPhone().equals(status)){
                    iteratorResponce.remove();
                }
            }
        }

        int total= studentList.size();
        //(page-1)*pageZise,(page-1)*pageZise+pageZise
        int pageZise = 1;
        if ((crrPage-1)*pageZise+pageZise>total){
            if(total/pageZise<1){
                studentList=studentList.subList(0,total);
            }else{
                studentList=studentList.subList((crrPage-1)*pageZise,total);
            }
        }else{
            studentList=studentList.subList((crrPage-1)*pageZise,(crrPage-1)*pageZise+pageZise);
        }
        return Result.succ(Integer.toString(total),studentList);
    }
}
