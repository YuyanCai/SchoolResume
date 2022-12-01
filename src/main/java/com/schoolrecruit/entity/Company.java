package com.schoolrecruit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: longtf
 * @since: 2022/11/29/20:57
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_company")
@ApiModel(value="Company对象", description="")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "登录密码")
    private String loginPassword;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "电话")
    private String tellCall;

    @ApiModelProperty(value = "头像")
    private String portrait;

    @ApiModelProperty(value = "首页链接")
    private String homeLink;

    @ApiModelProperty(value = "公司信息")
    private String infomation;

    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "审核状态")
    private String checkStatus;

    @ApiModelProperty(value = "信用代码")
    private String creditCode;

    @ApiModelProperty(value = "法人")
    private String legalPerson;

    @ApiModelProperty(value = "公司地址")
    private String address;

    @ApiModelProperty(value = "性质")
    private String nature;

    @ApiModelProperty(value = "规模")
    private String scale;


}
