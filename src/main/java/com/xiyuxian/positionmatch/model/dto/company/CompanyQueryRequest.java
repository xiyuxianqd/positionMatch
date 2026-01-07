package com.xiyuxian.positionmatch.model.dto.company;

import com.xiyuxian.positionmatch.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "公司查询请求")
public class CompanyQueryRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "公司ID")
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "所属行业")
    private String industry;

    @ApiModelProperty(value = "公司规模")
    private String companySize;

    @ApiModelProperty(value = "公司状态")
    private Integer companyStatus;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "排序方式")
    private String sortOrder = "descend";

    private static final long serialVersionUID = 1L;
}
