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
 * @since: 2022/11/29/20:58
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_job")
@ApiModel(value="Job对象", description="")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "岗位名称")
    private String jobName;

    @ApiModelProperty(value = "任职要求")
    private String qualifications;

    @ApiModelProperty(value = "工作内容")
    private String jobContent;

    @ApiModelProperty(value = "薪资待遇")
    private Integer salary;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "工作地点")
    private String place;

    @ApiModelProperty(value = "学历要求")
    private String education;

    @ApiModelProperty(value = "招聘人数")
    private String number;

    @ApiModelProperty(value = "发布时间")
    private String sendTime;

    @ApiModelProperty(value = "招聘状态")
    private String recruitmentStatus;

    @ApiModelProperty(value = "推荐专业")
    private String majors;

}

