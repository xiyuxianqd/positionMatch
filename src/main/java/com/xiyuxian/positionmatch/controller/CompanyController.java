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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @PostMapping("/add")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Long> addCompany(@RequestBody CompanyAddRequest companyAddRequest) {
        ThrowUtils.throwIf(companyAddRequest == null, ErrorCode.PARAMS_ERROR);
        Long result = companyService.addCompany(companyAddRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Boolean> updateCompany(@RequestBody CompanyUpdateRequest companyUpdateRequest) {
        ThrowUtils.throwIf(companyUpdateRequest == null || companyUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        Boolean result = companyService.updateCompany(companyUpdateRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Boolean> deleteCompany(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = companyService.deleteCompany(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    @GetMapping("/get")
    public BaseResponse<CompanyVO> getCompanyById(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        CompanyVO result = companyService.getCompanyById(id);
        return ResultUtils.success(result);
    }

    @GetMapping("/list")
    public BaseResponse<List<CompanyVO>> getAllCompanies() {
        List<CompanyVO> result = companyService.getAllCompanies();
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Page<CompanyVO>> listCompanyVOByPage(@RequestBody CompanyQueryRequest companyQueryRequest) {
        ThrowUtils.throwIf(companyQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<CompanyVO> result = companyService.listCompanyVOByPage(companyQueryRequest);
        return ResultUtils.success(result);
    }
}
