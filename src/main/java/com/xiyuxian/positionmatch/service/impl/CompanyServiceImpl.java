package com.xiyuxian.positionmatch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuxian.positionmatch.exception.BusinessException;
import com.xiyuxian.positionmatch.exception.ErrorCode;
import com.xiyuxian.positionmatch.mapper.CompanyMapper;
import com.xiyuxian.positionmatch.model.dto.company.CompanyAddRequest;
import com.xiyuxian.positionmatch.model.dto.company.CompanyQueryRequest;
import com.xiyuxian.positionmatch.model.dto.company.CompanyUpdateRequest;
import com.xiyuxian.positionmatch.model.entity.Company;
import com.xiyuxian.positionmatch.model.vo.CompanyVO;
import com.xiyuxian.positionmatch.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public Long addCompany(CompanyAddRequest companyAddRequest) {
        if (companyAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        if (StrUtil.isBlank(companyAddRequest.getCompanyName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "公司名称不能为空");
        }

        Company company = new Company();
        BeanUtil.copyProperties(companyAddRequest, company);
        company.setCreateTime(new Date());
        company.setUpdateTime(new Date());

        boolean saveResult = this.save(company);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加公司失败");
        }
        return company.getId();
    }

    @Override
    public Boolean updateCompany(CompanyUpdateRequest companyUpdateRequest) {
        if (companyUpdateRequest == null || companyUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }

        Company company = this.getById(companyUpdateRequest.getId());
        if (company == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "公司不存在");
        }

        BeanUtil.copyProperties(companyUpdateRequest, company);
        company.setUpdateTime(new Date());

        return this.updateById(company);
    }

    @Override
    public Boolean deleteCompany(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "公司ID错误");
        }

        Company company = this.getById(id);
        if (company == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "公司不存在");
        }

        return this.removeById(id);
    }

    @Override
    public CompanyVO getCompanyById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "公司ID错误");
        }

        Company company = this.getById(id);
        if (company == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "公司不存在");
        }

        return getCompanyVO(company);
    }

    @Override
    public CompanyVO getCompanyVO(Company company) {
        if (company == null) {
            return null;
        }
        CompanyVO companyVO = new CompanyVO();
        BeanUtil.copyProperties(company, companyVO);
        return companyVO;
    }

    @Override
    public List<CompanyVO> getCompanyVOList(List<Company> companyList) {
        if (companyList == null || companyList.isEmpty()) {
            return new ArrayList<>();
        }
        return companyList.stream()
                .map(this::getCompanyVO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CompanyVO> listCompanyVOByPage(CompanyQueryRequest companyQueryRequest) {
        if (companyQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        long current = companyQueryRequest.getCurrent();
        long pageSize = companyQueryRequest.getPageSize();
        Page<Company> companyPage = this.page(new Page<>(current, pageSize),
                getQueryWrapper(companyQueryRequest));
        Page<CompanyVO> companyVOPage = new Page<>(current, pageSize, companyPage.getTotal());
        List<CompanyVO> companyVOList = getCompanyVOList(companyPage.getRecords());
        companyVOPage.setRecords(companyVOList);
        return companyVOPage;
    }

    @Override
    public QueryWrapper<Company> getQueryWrapper(CompanyQueryRequest companyQueryRequest) {
        if (companyQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotNull(companyQueryRequest.getId()), "id", companyQueryRequest.getId());
        queryWrapper.like(StrUtil.isNotBlank(companyQueryRequest.getCompanyName()), "company_name", companyQueryRequest.getCompanyName());
        queryWrapper.like(StrUtil.isNotBlank(companyQueryRequest.getIndustry()), "industry", companyQueryRequest.getIndustry());
        queryWrapper.like(StrUtil.isNotBlank(companyQueryRequest.getCompanySize()), "company_size", companyQueryRequest.getCompanySize());
        queryWrapper.eq(ObjUtil.isNotNull(companyQueryRequest.getCompanyStatus()), "company_status", companyQueryRequest.getCompanyStatus());

        String sortField = companyQueryRequest.getSortField();
        String sortOrder = companyQueryRequest.getSortOrder();
        if (StrUtil.isNotEmpty(sortField)) {
            boolean isAscend = "ascend".equals(sortOrder);
            String dbSortField = StrUtil.toUnderlineCase(sortField);
            queryWrapper.orderBy(true, isAscend, dbSortField);
        } else {
            queryWrapper.orderByDesc("create_time");
        }

        return queryWrapper;
    }

    @Override
    public List<CompanyVO> getAllCompanies() {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_status", 0);
        queryWrapper.orderByDesc("create_time");
        List<Company> companyList = this.list(queryWrapper);
        return getCompanyVOList(companyList);
    }
}
