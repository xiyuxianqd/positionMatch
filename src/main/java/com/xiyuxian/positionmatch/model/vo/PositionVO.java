package com.xiyuxian.positionmatch.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "职位视图对象")
public class PositionVO implements Serializable {

    @ApiModelProperty(value = "职位ID")
    private Long id;

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

    @ApiModelProperty(value = "职位状态")
    private Integer positionStatus;

    @ApiModelProperty(value = "浏览次数")
    private Long viewCount;

    @ApiModelProperty(value = "申请次数")
    private Long applyCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
