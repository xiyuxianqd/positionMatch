package com.xiyuxian.positionmatch.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyuxian.positionmatch.model.dto.company.CompanyAddRequest;
import com.xiyuxian.positionmatch.model.dto.company.CompanyQueryRequest;
import com.xiyuxian.positionmatch.model.dto.company.CompanyUpdateRequest;
import com.xiyuxian.positionmatch.model.entity.Company;
import com.xiyuxian.positionmatch.model.vo.CompanyVO;

import java.util.List;

public interface CompanyService extends IService<Company> {

    Long addCompany(CompanyAddRequest companyAddRequest);

    Boolean updateCompany(CompanyUpdateRequest companyUpdateRequest);

    Boolean deleteCompany(Long id);

    CompanyVO getCompanyById(Long id);

    CompanyVO getCompanyVO(Company company);

    List<CompanyVO> getCompanyVOList(List<Company> companyList);

    Page<CompanyVO> listCompanyVOByPage(CompanyQueryRequest companyQueryRequest);

    QueryWrapper<Company> getQueryWrapper(CompanyQueryRequest companyQueryRequest);

    List<CompanyVO> getAllCompanies();
}
