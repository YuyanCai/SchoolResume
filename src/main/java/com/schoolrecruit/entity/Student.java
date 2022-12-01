package com.schoolrecruit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_student")
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "登录密码")
    private String loginPassword;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "头像")
    private String portrait;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "学制")
    @TableField("lengthOfSchooling")
    private String lengthOfSchooling;

    @ApiModelProperty(value = "外语")
    @TableField("foreignLanguages")
    private String foreignLanguages;

    @ApiModelProperty(value = "高校")
    @TableField("college")
    private String college;

    @ApiModelProperty(value = "专业")
    @TableField("major")
    private String major;
}
