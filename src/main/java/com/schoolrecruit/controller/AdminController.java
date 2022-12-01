package com.schoolrecruit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.schoolrecruit.entity.Admin;
import com.schoolrecruit.service.AdminService;
import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@Api(description = "管理员接口")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestBody Admin admin, HttpServletRequest request){
        admin = adminService.getOne(new QueryWrapper<Admin>().eq("account",admin.getAccount())
                .eq("a_password",admin.getAPassword()));
        if (admin!=null){
            //记录用户session
            HttpSession session = request.getSession();
            session.setAttribute("USER",admin);
            return Result.succ("登录成功",admin);
        }else{
            return Result.fail("登录失败");
        }
    }

    @ResponseBody
    @PostMapping("/logout/{id}")
    @ApiOperation(value = "退出")
    public Result logout(@PathVariable("id")String id, HttpServletRequest request){
        request.getSession().removeAttribute("USER");
        return Result.succ(200,"退出成功",null);
    }
}
