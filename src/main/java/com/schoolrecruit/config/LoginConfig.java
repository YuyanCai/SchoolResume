package com.schoolrecruit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new MyInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        registration.excludePathPatterns(//添加不拦截路径
                "/",
                "/page/index",            //主页
                "/oss/getToken",            //主页
                "/page/home",            //首页
                "/job-resume/indexData",            //首页热投推荐
                "/page/search",            //搜索页
                "/job/findSearchPage",            //分页搜索
                "/page/com",            //公司详情页
                "/job/find/**",            //查询岗位
                "/company/findCompany",
                "/company/showCompanyById/*",            //公司详细信息
                "/page/job",            //岗位性情页
                "/job/showById/*",            //岗位性情
                "/page/err",
                "/student/login",            //学生登录
                "/company/login",            //企业登录
                "/student/register",            //学生注册
                "/company/register",            //企业注册
                "/email/getverificationcode/*",  //获取验证码

                "/page/admin",            //管理员
                "/admin/login",            //管理员登录
                "/logout/*",            //管理员登录


                "/page/500",            //500
                "/page/404",            //404
                "/page/wdl",            //未登录
                "/page/err",            //err

                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.jpg",
                "/**/*.png"
        );
    }
}
