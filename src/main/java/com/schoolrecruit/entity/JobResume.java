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
 * @since: 2022/11/29/21:02
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_job_resume")
@ApiModel(value="JobResume对象", description="")
public class JobResume implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "投递时间")
    private String sendTime;

    @ApiModelProperty(value = "岗位id")
    private Long jobid;

    @ApiModelProperty(value = "简历id")
    private Long resumeid;

    @ApiModelProperty(value = "投递状态")
    private String sendStatus;

    @ApiModelProperty(value = "学生id")
    private Long studentid;

    @ApiModelProperty(value = "公司id")
    private Long companyid;

}
