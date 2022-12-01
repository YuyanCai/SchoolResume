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

/**
 * Created with IntelliJ IDEA.
 *
 * @author: longtf
 * @since: 2022/11/29/20:56
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_collection")
@ApiModel(value="Collection对象", description="")
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生Id")
    @TableField("studentId")
    private Long studentId;

    @ApiModelProperty(value = "岗位Id")
    @TableField("jobId")
    private Long jobId;

    @ApiModelProperty(value = "状态标签")
    @TableField("lable")
    private String lable;
}
