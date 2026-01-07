package com.xiyuxian.positionmatch.model.dto.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "公司更新请求")
public class CompanyUpdateRequest implements Serializable {

    @ApiModelProperty(value = "公司ID")
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司简介")
    private String companyDesc;

    @ApiModelProperty(value = "所属行业")
    private String industry;

    @ApiModelProperty(value = "公司规模")
    private String companySize;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "公司Logo")
    private String companyLogo;

    @ApiModelProperty(value = "公司官网")
    private String website;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "公司状态")
    private Integer companyStatus;

    private static final long serialVersionUID = 1L;
}
