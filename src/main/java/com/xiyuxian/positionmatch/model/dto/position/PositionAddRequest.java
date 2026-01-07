package com.xiyuxian.positionmatch.model.dto.position;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "职位添加请求")
public class PositionAddRequest implements Serializable {

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司ID")
    private Long companyId;

    @ApiModelProperty(value = "职位描述")
    private String positionDesc;

    @ApiModelProperty(value = "职位要求")
    private String requirements;

    @ApiModelProperty(value = "薪资范围")
    private String salaryRange;

    @ApiModelProperty(value = "工作地点")
    private String workLocation;

    @ApiModelProperty(value = "学历要求")
    private String education;

    @ApiModelProperty(value = "专业要求")
    private String major;

    @ApiModelProperty(value = "技能要求")
    private String skills;

    @ApiModelProperty(value = "工作年限要求")
    private Integer workYears;

    @ApiModelProperty(value = "职位类型")
    private String positionType;

    @ApiModelProperty(value = "职位标签")
    private String tags;

    private static final long serialVersionUID = 1L;
}
