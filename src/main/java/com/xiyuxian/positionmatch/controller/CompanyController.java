package com.xiyuxian.positionmatch.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuxian.positionmatch.annotation.AuthCheck;
import com.xiyuxian.positionmatch.common.BaseResponse;
import com.xiyuxian.positionmatch.common.DeleteRequest;
import com.xiyuxian.positionmatch.common.ResultUtils;
import com.xiyuxian.positionmatch.exception.BusinessException;
import com.xiyuxian.positionmatch.exception.ErrorCode;
import com.xiyuxian.positionmatch.exception.ThrowUtils;
import com.xiyuxian.positionmatch.model.dto.company.CompanyAddRequest;
import com.xiyuxian.positionmatch.model.dto.company.CompanyQueryRequest;
import com.xiyuxian.positionmatch.model.dto.company.CompanyUpdateRequest;
import com.xiyuxian.positionmatch.model.entity.Company;
import com.xiyuxian.positionmatch.model.vo.CompanyVO;
import com.xiyuxian.positionmatch.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "公司管理")
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @PostMapping("/add")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "添加公司")
    public BaseResponse<Long> addCompany(@Valid @RequestBody CompanyAddRequest companyAddRequest) {
        Long result = companyService.addCompany(companyAddRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "更新公司信息")
    public BaseResponse<Boolean> updateCompany(@Valid @RequestBody CompanyUpdateRequest companyUpdateRequest) {
        ThrowUtils.throwIf(companyUpdateRequest == null || companyUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        Boolean result = companyService.updateCompany(companyUpdateRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "删除公司")
    public BaseResponse<Boolean> deleteCompany(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = companyService.deleteCompany(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取公司详情")
    public BaseResponse<CompanyVO> getCompanyById(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        CompanyVO result = companyService.getCompanyById(id);
        return ResultUtils.success(result);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取所有公司列表（下拉选择用）")
    public BaseResponse<List<CompanyVO>> getAllCompanies() {
        List<CompanyVO> result = companyService.getAllCompanies();
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "分页查询公司（HR）")
    public BaseResponse<Page<CompanyVO>> listCompanyVOByPage(@Valid @RequestBody CompanyQueryRequest companyQueryRequest) {
        ThrowUtils.throwIf(companyQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<CompanyVO> result = companyService.listCompanyVOByPage(companyQueryRequest);
        return ResultUtils.success(result);
    }
}
