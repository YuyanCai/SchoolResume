package com.schoolrecruit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.schoolrecruit.entity.*;
import com.schoolrecruit.ov.*;
import com.schoolrecruit.service.*;
import com.schoolrecruit.utils.EmailUtils;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/job-resume")
@Api(description = "投递记录接口")
public class JobResumeController {

    @Autowired
    public JobResumeService jobResumeService;
    @Autowired
    private JobService jobService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    CollectionService collectionService;
    @Autowired
    InvitationService invitationService;

    @ResponseBody
    @PostMapping("/add")
    @ApiOperation(value = "添加投递记录")
    public Result add(@RequestBody JobResume jobResume, HttpServletRequest request) {
        List<JobResume> jobResumeList =jobResumeService.list(new QueryWrapper<JobResume>()
                .select("COUNT(*) id")
                .eq("companyid",jobResume.getCompanyid())
                .eq("studentid",jobResume.getStudentid())
        );
        if(jobResumeList.get(0).getId()>=3){
            return Result.fail("一个公司只能投递三个岗位");
        }
        if(jobResumeService.getOne(new QueryWrapper<JobResume>()
                .eq("jobid",jobResume.getJobid())
                .eq("studentid",jobResume.getStudentid()))==null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            jobResume.setSendTime(sdf.format(new Date()));
            jobResume.setSendStatus("未查阅");
            if ( jobResumeService.save(jobResume)){
                collectionService.update(new Collection().setLable("已投递"),
                        new QueryWrapper<Collection>()
                                .eq("studentId",jobResume.getStudentid())
                                .eq("jobId",jobResume.getJobid())
                );
                invitationService.remove(new QueryWrapper<Invitation>()
                        .eq("resumeid",jobResume.getResumeid())
                        .eq("jobid",jobResume.getJobid())
                );
                return Result.succ("投递成功");
            }else{
                return Result.succ("投递失败");
            }
        }else{
            return Result.fail("你已投递过该岗位");
        }
    }

    @ResponseBody
    @PostMapping("/student/{id}/{crrPage}/{resumeId}/{companyName}/{selectStatus}")
    @ApiOperation(value = "学生投递记录")
    public Result student(@PathVariable("id") Long id,
                          @PathVariable("crrPage") Integer crrPage,
                          @PathVariable("resumeId") Long resumeId,
                          @PathVariable("companyName") String companyName,
                          @PathVariable("selectStatus") String selectStatus) {
        //学生简历
        List<Resume> resumeList =resumeService.list(new QueryWrapper<Resume>().eq("studentId",id)
                .eq(resumeId!=0,"id",resumeId));
        if (resumeList.size()==0){
            return Result.fail("没有简历");
        }
        //学生简历的投递记录
        List<Long> resumeIdList = resumeList.stream().map(Resume::getId).collect(Collectors.toList());
        List<JobResume> jobResumeList= jobResumeService.list(new QueryWrapper<JobResume>()
                .in("resumeid",resumeIdList)
                .eq(!"null".equals(selectStatus),"send_status",selectStatus)
                .orderByDesc("id")
        );
        if (jobResumeList.size()==0){
            return Result.fail("没有符合的投递记录");
        }
        //学生简历的投递记录里的职位
        List<Long> jobIdList = jobResumeList.stream().map(JobResume::getJobid).collect(Collectors.toList());
        List<Job> jobList = jobService.list(new QueryWrapper<Job>().in("id",jobIdList));
        if (jobList.size()==0){
            return Result.fail("没找到岗位");
        }
        //学生简历的投递记录里的职位对应的公司
        List<Long> companyIdList = jobList.stream().map(Job::getCompanyId).collect(Collectors.toList());
        List<Company>  companyList = companyService.list(new QueryWrapper<Company>()
                .in("id",companyIdList)
                .like(!"null".equals(companyName),"company_name",companyName)
        );
        //返回体

        List<StudentShowJRVO> studentShowJRVOList = new ArrayList<>();
        for (JobResume jobResume:jobResumeList) {
            for (Resume resume:resumeList){
                if(resume.getId()==jobResume.getResumeid()){
                    for (Job job:jobList) {
                        if(jobResume.getJobid()==job.getId()){
                            for (Company company:companyList) {
                                if (job.getCompanyId()==company.getId()){

                                    StudentShowJRVO studentShowJRVO = new StudentShowJRVO();
                                    //公司
                                    studentShowJRVO.setCompanyId(company.getId());
                                    studentShowJRVO.setCompanyName(company.getCompanyName());
                                    studentShowJRVO.setPortrait(company.getPortrait());
                                    studentShowJRVO.setCheckStatus(company.getCheckStatus());
                                    //岗位
                                    studentShowJRVO.setJobId(job.getId());
                                    studentShowJRVO.setJobName(job.getJobName());
                                    studentShowJRVO.setRecruitmentStatus(job.getRecruitmentStatus());
                                    //简历
                                    studentShowJRVO.setResumeName(resume.getResumeName());
                                    studentShowJRVO.setAttachment(resume.getAttachment());
                                    //投递状态
                                    studentShowJRVO.setSendStatus(jobResume.getSendStatus());

                                    studentShowJRVOList.add(studentShowJRVO);
                                }
                            }
                        }
                    }
                }
            }
        }
        //去重
        studentShowJRVOList=studentShowJRVOList.stream().distinct().collect(Collectors.toList());

        int total= studentShowJRVOList.size();
        //(page-1)*pageZise,(page-1)*pageZise+pageZise
        int pageZise = 6;
        if ((crrPage-1)*pageZise+pageZise>total){
            if(total/pageZise<1){
                studentShowJRVOList=studentShowJRVOList.subList(0,total);
            }else{
                studentShowJRVOList=studentShowJRVOList.subList((crrPage-1)*pageZise,total);
            }
        }else{
            studentShowJRVOList=studentShowJRVOList.subList((crrPage-1)*pageZise,(crrPage-1)*pageZise+pageZise);
        }
        return Result.succ(Integer.toString(total),studentShowJRVOList);
    }

    @ResponseBody
    @PostMapping("/company")
    @ApiOperation(value = "企业收到简历记录")
    public Result company(@RequestBody RequestConpanySearchRJVO requestConpanySearchRJVO) {
        //公司岗位
        List<Job> jobList =jobService.list(new QueryWrapper<Job>()
                .eq("company_id",requestConpanySearchRJVO.getCompanyId())
                .like(requestConpanySearchRJVO.getSelectJob()!=null,"job_name",requestConpanySearchRJVO.getSelectJob()));
        //公司岗位的投递记录
        List<Long> jobIdList = jobList.stream().map(Job::getId).collect(Collectors.toList());
        if (jobIdList==null||jobIdList.size()==0){
            return Result.fail("没有岗位数据");
        }
        List<JobResume> jobResumeList= jobResumeService.list(new QueryWrapper<JobResume>()
                .in("jobid",jobIdList)
                .eq(requestConpanySearchRJVO.getSelectStatus()!=null,"send_status",requestConpanySearchRJVO.getSelectStatus()));
        //公司岗位的投递记录里的简历
        List<Long> resumeIdList = jobResumeList.stream().map(JobResume::getResumeid).collect(Collectors.toList());
        if (resumeIdList==null||resumeIdList.size()==0){
            return Result.fail("没有投递记录里的简历数据");
        }
        List<Resume> resumeList = resumeService.list(new QueryWrapper<Resume>()
                .in("id",resumeIdList)
                .orderByDesc("id")
        );
        //公司岗位的投递记录里的简历对应的学生
        List<Long> studentIdList = resumeList.stream().map(Resume::getStudentId).collect(Collectors.toList());
        if (studentIdList==null||studentIdList.size()==0){
            return Result.fail("没有对应的学生数据");
        }
        List<Student>  studentList = studentService.list(new QueryWrapper<Student>()
                .in("id",studentIdList)
                .like(requestConpanySearchRJVO.getStudentName()!=null,"student_name",requestConpanySearchRJVO.getStudentName())
                .eq(requestConpanySearchRJVO.getEducation()!=null,"education",requestConpanySearchRJVO.getEducation())
                .eq(requestConpanySearchRJVO.getLengthOfSchooling()!=null,"lengthOfSchooling",requestConpanySearchRJVO.getLengthOfSchooling())
                .eq(requestConpanySearchRJVO.getForeignLanguages()!=null,"foreignLanguages",requestConpanySearchRJVO.getForeignLanguages())
                .eq(requestConpanySearchRJVO.getSex()!=null,"sex",requestConpanySearchRJVO.getSex())
        );
        //返回体
        List<ResponeConpanyShowRJVO> responeConpanyShowRJVOList = new ArrayList<>();
        for(Resume resume:resumeList){
            for(JobResume jobResume:jobResumeList){
                if(jobResume.getResumeid()==resume.getId()){
                    for(Job job:jobList){
                        if(job.getId()==jobResume.getJobid()){
                            for(Student student:studentList){
                                if(resume.getStudentId()==student.getId()){
                                    ResponeConpanyShowRJVO responeConpanyShowRJVO = new ResponeConpanyShowRJVO();
                                    responeConpanyShowRJVO.setStudentId(student.getId());
                                    responeConpanyShowRJVO.setStudentName(student.getStudentName());
                                    responeConpanyShowRJVO.setJobId(job.getId());
                                    responeConpanyShowRJVO.setJobName(job.getJobName());
                                    responeConpanyShowRJVO.setEducation(student.getEducation());
                                    responeConpanyShowRJVO.setLengthOfSchooling(student.getLengthOfSchooling());
                                    responeConpanyShowRJVO.setForeignLanguages(student.getForeignLanguages());
                                    responeConpanyShowRJVO.setSex(student.getSex());
                                    responeConpanyShowRJVO.setAttachment(resume.getAttachment());
                                    responeConpanyShowRJVO.setResumeName(resume.getResumeName());
                                    responeConpanyShowRJVO.setSendStatus(jobResume.getSendStatus());
                                    responeConpanyShowRJVO.setEmail(student.getEmail());
                                    responeConpanyShowRJVO.setResumeJobId(jobResume.getId());

                                    responeConpanyShowRJVOList.add(responeConpanyShowRJVO);
                                }
                            }
                        }
                    }
                }
            }
        }
        //去重
        responeConpanyShowRJVOList=responeConpanyShowRJVOList.stream().distinct().collect(Collectors.toList());

        int crrPage = requestConpanySearchRJVO.getCrrPage();
        int total= responeConpanyShowRJVOList.size();
        //(page-1)*pageZise,(page-1)*pageZise+pageZise
        int pageZise = 6;
        int pageAll= total%pageZise==0 ? total/pageZise : total/pageZise+1;
        crrPage = pageAll<crrPage?1:crrPage;
        if ((crrPage-1)*pageZise+pageZise>total){
            responeConpanyShowRJVOList=responeConpanyShowRJVOList.subList((crrPage-1)*pageZise,total);
        }else{
            responeConpanyShowRJVOList=responeConpanyShowRJVOList.subList((crrPage-1)*pageZise,(crrPage-1)*pageZise+pageZise);
        }
        return Result.succ(Integer.toString(total),responeConpanyShowRJVOList);
    }

    @ResponseBody
    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改记录状态")
    public String updateStatus(@RequestBody ResponeConpanyShowRJVO responeConpanyShowRJVO){
        boolean flag = jobResumeService.update(new JobResume().setSendStatus(responeConpanyShowRJVO.getSendStatus()),new QueryWrapper<JobResume>().eq("id",responeConpanyShowRJVO.getResumeJobId()));
        /*if (flag&&responeConpanyShowRJVO.getSendStatus().equals("人才库")){
            emailUtils.sendSimpleMail(responeConpanyShowRJVO.getEmail(),"投递状态通知","你的公司审核状态被修改为：");
        }*/
        return "redirect:/job-resume/company";
    }

    @ResponseBody
    @PostMapping("/studentData")
    @ApiOperation(value = "学生投递记录条形图")
    public Result studentData(@RequestBody Student student){
        List resumIdList = resumeService.listObjs(new QueryWrapper<Resume>().eq("studentId",student.getId()));
        if(resumIdList.size()==0){
            return Result.fail(null);
        }
        List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>().select("send_time,count(*) id")
                .in(resumIdList.size() != 0, "resumeid", resumIdList)
                .groupBy("send_time")
        );
        if(jobResumeList.size()==0){
            return Result.fail(null);
        }
        if(jobResumeList.size()>7){
            jobResumeList= jobResumeList.subList(jobResumeList.size()-7,jobResumeList.size());
        }
        XyVO xyVO = new XyVO();
        xyVO.setY(jobResumeList.stream().map(JobResume::getId).collect(Collectors.toList()));
        xyVO.setX(jobResumeList.stream().map(JobResume::getSendTime).collect(Collectors.toList()));
        return  Result.succ(xyVO);
    }

    @ResponseBody
    @PostMapping("/studentDataPing")
    @ApiOperation(value = "学生投递状态饼图")
    public Result studentDataPing(@RequestBody Student student){
        List resumIdList = resumeService.listObjs(new QueryWrapper<Resume>().eq("studentId",student.getId()));
        if(resumIdList.size()==0){
            return Result.fail(null);
        }
        List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>().select("send_status,COUNT(send_status) id")
                .in(resumIdList.size() != 0, "resumeid", resumIdList)
                .groupBy("send_status")
        );
        if(jobResumeList.size()==0){
            return Result.fail(null);
        }
        List<NameValueVO>  nameValueVOList = new ArrayList<>();
        for (JobResume jobResume:jobResumeList) {
            NameValueVO nameValueVO = new NameValueVO();
            nameValueVO.setName(jobResume.getSendStatus());
            nameValueVO.setValue(jobResume.getId());
            nameValueVOList.add(nameValueVO);
        }
        return  Result.succ(nameValueVOList);
    }

    @ResponseBody
    @PostMapping("/studentResumeBar")
    @ApiOperation(value = "学生各简历投递数统计")
    public Result studentResumeBar(@RequestBody Student student){
        /*List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>()
                .select("resumeid,count(*) id")
                .eq("studentid",student.getId())
                .groupBy("resumeid")
        );
        if(jobResumeList.size()==0){
            return Result.fail(null);
        }
        List<Long> jobResumeIdList= jobResumeList.stream().map(JobResume::getResumeid).collect(Collectors.toList());
        List<Resume> resumeList = resumeService.list(new QueryWrapper<Resume>()
                .in("id",jobResumeIdList)
        );
        XyVO xyVO = new XyVO();
        xyVO.setY(jobResumeList.stream().map(JobResume::getId).collect(Collectors.toList()));
        xyVO.setX(resumeList.stream().map(Resume::getResumeName).collect(Collectors.toList()));
        return  Result.succ(xyVO);*/
        XyVO xyVO = new XyVO();
        //select resumeid,count(*) id from tb_job_resume  where studentId='7' and send_status = '人才库' GROUP BY resumeid
        List<Resume> resumeList =resumeService.list(new QueryWrapper<Resume>().eq("studentId",student.getId()));
        if(resumeList.size()==0){
            return Result.fail(null);
        }
        xyVO.setX(resumeList.stream().map(Resume::getResumeName).collect(Collectors.toList()));
        String stastus[] = {"未查阅", "已查阅", "已回复", "人才库"};
        List<Long[]> xy = new ArrayList<>();
        for (String s:stastus) {
            List<JobResume> jobResumeList =jobResumeService.list(new QueryWrapper<JobResume>()
                    .select("resumeid,count(*) id")
                    .eq("studentId",student.getId())
                    .eq("send_status",s)
                    .groupBy("resumeid")
            );
            Long statusNum[] = new Long[resumeList.size()];
            for (int i =0 ;i<resumeList.size();i++) {
                for (JobResume jobResume:jobResumeList){
                    if (resumeList.get(i).getId()==jobResume.getResumeid()){
                        statusNum[i]=jobResume.getId();
                    }
                }
                if (statusNum[i]==null){
                    statusNum[i]=0L;
                }
            }
            xy.add(statusNum);
        }
        xyVO.setXy(xy);
        return Result.succ(xyVO);
    }

    @ResponseBody
    @PostMapping("/companyDataBar")
    @ApiOperation(value = "企业岗位投递记录条形")
    public Result companyDataBar(@RequestBody Company company, HttpServletRequest request){
        /*Company companySessio = (Company) request.getSession().getAttribute("USER");
        System.err.println(companySessio);*/
        //1.获取公司有哪些岗位
        List<Job> jobList= jobService.list(new QueryWrapper<Job>().eq("company_id",company.getId()));
        //2.得到该公司的岗位id
        List<Long> jobIdList = jobList.stream().map(Job::getId).collect(Collectors.toList());
        if (jobIdList.size()==0){
            return Result.fail(null);
        }
        //3.查询该公司各个岗位被投递的次数,
        List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>()
                .select("jobid,count(*) id")
                .in(jobIdList.size()!=0,"jobid",jobIdList)
                .groupBy("jobid")
        );

        if (jobResumeList.size()==0){
            return Result.fail(null);
        }

        //4.构建x轴和y轴,x轴位岗位名称,y轴为该岗位被投递次数
        List<String> x = new ArrayList<>();
        List<Long> y = new ArrayList<>();

        //5.拿该公司被投递过的岗位以及投递次数集合和该公司所有岗位集合进行筛选
        for (JobResume jobResume:jobResumeList) {
            for (Job job:jobList){
                if (jobResume.getJobid()==job.getId()){
                    x.add(job.getJobName());
                    y.add(jobResume.getId());
                }
            }
        }
        XyVO xyVO = new XyVO();
        xyVO.setX(x);
        xyVO.setY(y);
        return Result.succ(xyVO);
    }

    @ResponseBody
    @PostMapping("/companyData")
    @ApiOperation(value = "企业最近7日投递记录条形")
    public Result companyData(@RequestBody Company company){
        List<Job> jobList= jobService.list(new QueryWrapper<Job>().eq("company_id",company.getId()));
        if(jobList.size()==0){
            return Result.fail(null);
        }
        List<Long> jobIdList = jobList.stream().map(Job::getId).collect(Collectors.toList());
        List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>()
                .select("send_time,count(*) id")
                .in(jobIdList.size()!=0,"jobid",jobIdList)
                .groupBy("send_time")
        );
        if(jobResumeList.size()==0){
            return Result.fail(null);
        }
        if(jobResumeList.size()>7){
            jobResumeList= jobResumeList.subList(jobResumeList.size()-7,jobResumeList.size());
        }
        XyVO xyVO = new XyVO();
        xyVO.setY(jobResumeList.stream().map(JobResume::getId).collect(Collectors.toList()));
        xyVO.setX(jobResumeList.stream().map(JobResume::getSendTime).collect(Collectors.toList()));
        return Result.succ(xyVO);
    }

    @ResponseBody
    @PostMapping("/companyDataPing")
    @ApiOperation(value = "岗位投递状态饼图")
    public Result companyDataPing(@RequestBody Company company){
        List<Job> jobList= jobService.list(new QueryWrapper<Job>().eq("company_id",company.getId()));
        if(jobList.size()==0){
            return Result.fail(null);
        }
        List<Long> jobIdList = jobList.stream().map(Job::getId).collect(Collectors.toList());
        List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>()
                .select("send_status,count(*) id")
                .in(jobIdList.size()!=0,"jobid",jobIdList)
                .groupBy("send_status")
        );
        if(jobResumeList.size()==0){
            return Result.fail(null);
        }
        List<NameValueVO>  nameValueVOList = new ArrayList<>();
        for (JobResume jobResume:jobResumeList) {
            NameValueVO nameValueVO = new NameValueVO();
            nameValueVO.setName(jobResume.getSendStatus());
            nameValueVO.setValue(jobResume.getId());
            nameValueVOList.add(nameValueVO);
        }
        return  Result.succ(nameValueVOList);
    }

    @ResponseBody
    @PostMapping("/count")
    @ApiOperation(value = "统计个实体数量")
    public Result count(){
        List<Integer> list = new ArrayList<>();
        list.add(companyService.count());
        list.add(jobService.count());
        list.add(jobResumeService.count());
        list.add(resumeService.count());
        list.add(studentService.count());
        return Result.succ(list);
    }

    @ResponseBody
    @PostMapping("/allDataLine")
    @ApiOperation(value = "统计数量")
    public Result allDataLine(){
        List<JobResume> jobResumeList =  jobResumeService.list(new QueryWrapper<JobResume>().select("send_time,count(*) id").groupBy("send_time"));
        if(jobResumeList.size()>10){
            jobResumeList= jobResumeList.subList(jobResumeList.size()-10,jobResumeList.size());
        }
        XyVO xyVO = new XyVO();
        xyVO.setY(jobResumeList.stream().map(JobResume::getId).collect(Collectors.toList()));
        xyVO.setX(jobResumeList.stream().map(JobResume::getSendTime).collect(Collectors.toList()));
        return Result.succ(xyVO);
    }

    @ResponseBody
    @PostMapping("/indexData")
    @ApiOperation(value = "首页12条热投")
    public Result indexData(){
        List<JobResume> jobResumeList = jobResumeService.list(new QueryWrapper<JobResume>()
                .select("jobid,count(*) id")
                .groupBy("jobid")
                .orderByDesc("id")
        );
        if(jobResumeList.size()==0){
            return Result.fail(null);
        }
        List<Long> jobIdList = jobResumeList.stream().map(JobResume::getJobid).collect(Collectors.toList());
        List<Job> jobList = jobService.list(new QueryWrapper<Job>().in("id",jobIdList));

        List<Long> companyIdList = jobList.stream().map(Job::getCompanyId).collect(Collectors.toList());
        List<Company> companyList = companyService.list(new QueryWrapper<Company>()
                .in("id",companyIdList)
                .eq("check_status","已通过")
        );

        List<SearchVO> searchVOList = new ArrayList<>();
        for (JobResume jobResume : jobResumeList) {
            for(Job job:jobList){
                if (job.getId()==jobResume.getJobid()){
                    for (Company company:companyList){
                        if (job.getCompanyId()==company.getId()){
                            SearchVO search = new SearchVO();

                            search.setJobId(job.getId());
                            search.setJobName(job.getJobName());
                            if (job.getJobName().length()>7){

                                int l2=0;
                                for (char c:job.getJobName().toCharArray()){
                                    int l=c+"".getBytes().length;
                                    if (c+"".getBytes().length<128){
                                        l2++;
                                    }
                                }
                                if(6+l2/2>=job.getJobName().length()) {
                                    search.setPlace(job.getJobName());
                                }else{
                                    search.setPlace(job.getJobName().substring(0,6+l2/2)+"...");
                                }
                            }else {
                                search.setPlace(job.getJobName());
                            }
                            search.setSalary(Long.toString(job.getSalary()));
                            //次数
                            search.setEducation(Long.toString(jobResume.getId()));

                            search.setCompanyId(company.getId());
                            search.setCompanyName(company.getCompanyName());
                            search.setPortrait(company.getPortrait());
                            search.setNature(company.getNature());
                            search.setScale(company.getScale());

                            searchVOList.add(search);
                        }
                    }
                }
            }
        }
        if(searchVOList.size()>12){
            searchVOList=searchVOList.subList(0,12);
        }
            //去重
        //searchVOList=searchVOList.stream().distinct().collect(Collectors.toList());
        return Result.succ(searchVOList);
    }


}
