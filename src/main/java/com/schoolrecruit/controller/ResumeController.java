package com.schoolrecruit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.schoolrecruit.entity.Resume;
import com.schoolrecruit.service.ResumeService;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/resume")
@Api(description = "简历接口")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @ResponseBody
    @PostMapping("/add")
    @ApiOperation(value = "添加/修改简历")
    public Result add(@RequestBody Resume resume){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        resume.setUpdateTime(sdf.format(date));
        if (resume.getId()==null){
            List<Resume> resumeList = resumeService.list(new QueryWrapper<Resume>().eq("studentId",resume.getStudentId()));
            if(resumeList.size()<3){
                if(resumeService.save(resume)){
                    return Result.succ("添加成功",resumeService.list(new QueryWrapper<Resume>().eq("studentId",resume.getStudentId())));
                }else{
                    return Result.fail("添加失败");
                }
            }else{
                return Result.fail("每个学生只能有3份简历");
            }
        }else{
            if(resumeService.updateById(resume)){
                return Result.succ("修改成功",resumeService.list(new QueryWrapper<Resume>().eq("studentId",resume.getStudentId())));
            }else{
                return Result.fail("修改失败");
            }
        }
    }
    @ResponseBody
    @PostMapping("/listByStudentId/{studentId}")
    @ApiOperation(value = "通过学生ID查询简历")
    public Result listByStudentId(@PathVariable("studentId")Long studentId){
        return Result.succ(resumeService.list(new QueryWrapper<Resume>().eq("studentId",studentId)));
    }

    @ResponseBody
    @PostMapping("/deleteBuyId")
    @ApiOperation(value = "通过ID删除简历")
    public Result deleteBuyId(@RequestBody Resume resume){
        if (resumeService.removeById(resume.getId())){
            return Result.succ(200,"删除成功",resumeService.list(new QueryWrapper<Resume>().eq("studentId",resume.getStudentId())));
        }else{
            return Result.fail("删除失败");
        }
    }
}
