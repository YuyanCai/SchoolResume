package com.schoolrecruit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.schoolrecruit.entity.Student;
import com.schoolrecruit.service.StudentService;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
@Api(description = "学生接口")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ResponseBody
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestBody Student student, HttpServletRequest request) {
        //VerificationCode-->loginPassword
        //1.获取用户的验证码
        String VerificationCode = (String) request.getSession().getAttribute(student.getEmail() + "VerificationCode");

        //2.查询当前用户是否在数据库存在
        Student studentGetOne = studentService.getOne(new QueryWrapper<Student>().eq("email", student.getEmail()));

        //3.如果存在,验证密码
        if (studentGetOne != null && (student.getLoginPassword().equals(studentGetOne.getLoginPassword()) || student.getLoginPassword().equals(VerificationCode))) {
            //3.1 登录成功，删除验证码session
            request.getSession().removeAttribute(studentGetOne.getEmail() + "VerificationCode");
            //3.2 记录用户session
            HttpSession session = request.getSession();
            session.setAttribute("USER", studentGetOne);
            return Result.succ(200, "登录成功", studentGetOne);
        } else {
            if (studentGetOne == null) {
                return Result.fail("登录失败!邮箱未注册");
            }
            return Result.fail("登录失败!密码、验证码错误");
        }
    }

    @ResponseBody
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public Result register(@RequestBody Student student, HttpServletRequest request) {
        //VerificationCode-->phone
        String VerificationCode = (String) request.getSession().getAttribute(student.getEmail() + "VerificationCode");
        Student studentGetOne = studentService.getOne(new QueryWrapper<Student>().eq("email", student.getEmail()));
        if (studentGetOne == null || VerificationCode.equals(student.getPhone())) {
            //注册成功，删除验证码session
            request.getSession().removeAttribute(student.getEmail() + "VerificationCode");
            //记录用户session
            HttpSession session = request.getSession();
            session.setAttribute("USER", student);
            student.setPhone(null);
            student.setPortrait("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
            studentService.save(student);
            return Result.succ(200, "注册成功！", student);
        } else {
            if (studentGetOne != null) {
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
    @PostMapping("/get/{id}")
    @ApiOperation(value = "通过id获取学生信息")
    public Result getById(@PathVariable("id") Long id) {
        return Result.succ(200, "查询成功", studentService.getById(id));
    }

    @ResponseBody
    @PostMapping("/update")
    @ApiOperation(value = "修改信息")
    public Result update(@RequestBody Student student, HttpServletRequest request) {
        if (studentService.updateById(student)) {
            HttpSession session = request.getSession();
            session.setAttribute("USER", student);
            return Result.succ(200, "修改成功", studentService.getById(student));
        } else {
            return Result.succ(400, "修改失败", null);
        }
    }

}
