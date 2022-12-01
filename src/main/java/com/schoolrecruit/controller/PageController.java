package com.schoolrecruit.controller;

import com.schoolrecruit.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/page")
@Api(description = "页面接口")
public class PageController {
    @GetMapping("/test")
    @ApiOperation(value = "测试")
    public String test(){
        return "test";
    }

    @GetMapping("/school_info")
    @ApiOperation(value = "测试")
    public String school_info(){
        return "school_info";
    }

    @GetMapping("/detail")
    @ApiOperation(value = "测试")
    public String detail(){
        return "detail";
    }

    @GetMapping("/friend_link")
    @ApiOperation(value = "测试")
    public String friend_link(){
        return "friend_link";
    }

    @GetMapping("/index")
    @ApiOperation(value = "主页")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    @ApiOperation(value = "首页")
    public String home(){
        return "home";
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索")
    public String search(){
        return "search";
    }

    @GetMapping("/student")
    @ApiOperation(value = "学生主页")
    public String student(){
        return "student";
    }

    @GetMapping("/student_info")
    @ApiOperation(value = "学生信息")
    public String student_info(){
        return "student_info";
    }

    @GetMapping("/student_collection")
    @ApiOperation(value = "学生收藏")
    public String student_collection(){
        return "student_collection";
    }

    @GetMapping("/student_resume")
    @ApiOperation(value = "简历管理")
    public String student_resume(){
        return "student_resume";
    }

    @GetMapping("/student_send")
    @ApiOperation(value = "投递记录")
    public String student_send(){
        return "student_send";
    }

    @GetMapping("/company")
    @ApiOperation(value = "公司主页")
    public String company(){
        return "company";
    }

    @GetMapping("/company_info")
    @ApiOperation(value = "公司信息")
    public String company_info(){
        return "company_info";
    }

    @GetMapping("/company_job")
    @ApiOperation(value = "岗位管理")
    public String company_job(){
        return "company_job";
    }

    @GetMapping("/company_send")
    @ApiOperation(value = "收到简历")
    public String company_send(){
        return "company_send";
    }

    @GetMapping("/company_data")
    @ApiOperation(value = "公司数据")
    public String company_data(){
        return "company_data";
    }

    @GetMapping("/job")
    @ApiOperation(value = "岗位详情")
    public String job(){
        return "job";
    }

    @GetMapping("/admin")
    @ApiOperation(value = "管理员")
    public String admin(){
        return "admin";
    }

    @GetMapping("/admin_company")
    @ApiOperation(value = "管理员管理公司")
    public String admin_company(){
        return "admin_company";
    }

    @GetMapping("/admin_data")
    @ApiOperation(value = "管理员数据")
    public String admin_data(){
        return "admin_data";
    }

    @GetMapping("/com")
    @ApiOperation(value = "公司页")
    public String com(){
        return "com";
    }

    @GetMapping("/company_recommend")
    @ApiOperation(value = "推荐学生")
    public String companyRecommend(){
        return "company_recommend";
    }

    @GetMapping("/student_recommend")
    @ApiOperation(value = "推荐岗位")
    public String studentRecommend(){
        return "student_recommend";
    }



    @GetMapping("/err")
    @ApiOperation(value = "异常测试")
    public String err(){
        int a=1/0;
        return "err";
    }

/*    @RequestMapping("/wdl")
    @ApiOperation(value = "请先登录")
    public String wdl(){
        return "wdl";
    }*/
    @ResponseBody
    @RequestMapping("/wdl")
    @ApiOperation(value = "请先登录")
    public Result wdl(){
        return Result.fail("请先登录");
    }

    @GetMapping("/500")
    @ApiOperation(value = "500")
    public String wu00(){
        return "500";
    }

    @GetMapping("/404")
    @ApiOperation(value = "404")
    public String si0si(){
        return "404";
    }

    @GetMapping("/stu")
    @ApiOperation(value = "学生主页")
    public String stu(){
        return "stu";
    }
}
