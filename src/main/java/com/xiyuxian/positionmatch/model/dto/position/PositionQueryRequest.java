package com.xiyuxian.positionmatch.model.dto.position;

import com.xiyuxian.positionmatch.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "职位查询请求")
public class PositionQueryRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "职位ID")
    private Long id;

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司ID")
    private Long companyId;

    @ApiModelProperty(value = "学历要求")
    private String education;

    @ApiModelProperty(value = "专业要求")
    private String major;

    @ApiModelProperty(value = "技能要求")
    private String skills;

    @ApiModelProperty(value = "职位类型")
    private String positionType;

    @ApiModelProperty(value = "职位标签")
    private String tags;

    @ApiModelProperty(value = "职位状态")
    private Integer positionStatus;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "排序方式")
    private String sortOrder = "descend";

    private static final long serialVersionUID = 1L;
}
