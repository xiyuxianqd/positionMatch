package com.xiyuxian.positionmatch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuxian.positionmatch.exception.BusinessException;
import com.xiyuxian.positionmatch.exception.ErrorCode;
import com.xiyuxian.positionmatch.mapper.PositionMapper;
import com.xiyuxian.positionmatch.model.dto.position.PositionAddRequest;
import com.xiyuxian.positionmatch.model.dto.position.PositionQueryRequest;
import com.xiyuxian.positionmatch.model.dto.position.PositionUpdateRequest;
import com.xiyuxian.positionmatch.model.entity.Position;
import com.xiyuxian.positionmatch.model.vo.PositionVO;
import com.xiyuxian.positionmatch.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Override
    public Long addPosition(PositionAddRequest positionAddRequest) {
        if (positionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        if (StrUtil.isBlank(positionAddRequest.getPositionName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "职位名称不能为空");
        }
        if (StrUtil.isBlank(positionAddRequest.getCompanyName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "公司名称不能为空");
        }

        Position position = new Position();
        BeanUtil.copyProperties(positionAddRequest, position);
        position.setViewCount(0L);
        position.setApplyCount(0L);
        position.setCreateTime(new Date());
        position.setUpdateTime(new Date());

        boolean saveResult = this.save(position);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "发布职位失败");
        }
        return position.getId();
    }

    @Override
    public Boolean updatePosition(PositionUpdateRequest positionUpdateRequest) {
        if (positionUpdateRequest == null || positionUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }

        Position position = this.getById(positionUpdateRequest.getId());
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "职位不存在");
        }

        BeanUtil.copyProperties(positionUpdateRequest, position);
        position.setUpdateTime(new Date());

        return this.updateById(position);
    }

    @Override
    public Boolean deletePosition(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "职位ID错误");
        }

        Position position = this.getById(id);
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "职位不存在");
        }

        return this.removeById(id);
    }

    @Override
    public PositionVO getPositionById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "职位ID错误");
        }

        Position position = this.getById(id);
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "职位不存在");
        }

        return getPositionVO(position);
    }

    @Override
    public PositionVO getPositionVO(Position position) {
        if (position == null) {
            return null;
        }
        PositionVO positionVO = new PositionVO();
        BeanUtil.copyProperties(position, positionVO);
        return positionVO;
    }

    @Override
    public List<PositionVO> getPositionVOList(List<Position> positionList) {
        if (positionList == null || positionList.isEmpty()) {
            return new ArrayList<>();
        }
        return positionList.stream()
                .map(this::getPositionVO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PositionVO> listPositionVOByPage(PositionQueryRequest positionQueryRequest) {
        if (positionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        long current = positionQueryRequest.getCurrent();
        long pageSize = positionQueryRequest.getPageSize();
        Page<Position> positionPage = this.page(new Page<>(current, pageSize),
                getQueryWrapper(positionQueryRequest));
        Page<PositionVO> positionVOPage = new Page<>(current, pageSize, positionPage.getTotal());
        List<PositionVO> positionVOList = getPositionVOList(positionPage.getRecords());
        positionVOPage.setRecords(positionVOList);
        return positionVOPage;
    }

    @Override
    public QueryWrapper<Position> getQueryWrapper(PositionQueryRequest positionQueryRequest) {
        if (positionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotNull(positionQueryRequest.getId()), "id", positionQueryRequest.getId());
        queryWrapper.like(StrUtil.isNotBlank(positionQueryRequest.getPositionName()), "position_name", positionQueryRequest.getPositionName());
        queryWrapper.like(StrUtil.isNotBlank(positionQueryRequest.getCompanyName()), "company_name", positionQueryRequest.getCompanyName());
        queryWrapper.eq(ObjUtil.isNotNull(positionQueryRequest.getCompanyId()), "company_id", positionQueryRequest.getCompanyId());
        queryWrapper.like(StrUtil.isNotBlank(positionQueryRequest.getEducation()), "education", positionQueryRequest.getEducation());
        queryWrapper.like(StrUtil.isNotBlank(positionQueryRequest.getMajor()), "major", positionQueryRequest.getMajor());
        queryWrapper.like(StrUtil.isNotBlank(positionQueryRequest.getSkills()), "skills", positionQueryRequest.getSkills());
        queryWrapper.eq(StrUtil.isNotBlank(positionQueryRequest.getPositionType()), "position_type", positionQueryRequest.getPositionType());
        queryWrapper.like(StrUtil.isNotBlank(positionQueryRequest.getTags()), "tags", positionQueryRequest.getTags());
        queryWrapper.eq(ObjUtil.isNotNull(positionQueryRequest.getPositionStatus()), "position_status", positionQueryRequest.getPositionStatus());

        String sortField = positionQueryRequest.getSortField();
        String sortOrder = positionQueryRequest.getSortOrder();
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
    public Boolean incrementViewCount(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "职位ID错误");
        }

        Position position = this.getById(id);
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "职位不存在");
        }

        Long viewCount = position.getViewCount();
        if (viewCount == null) {
            viewCount = 0L;
        }
        position.setViewCount(viewCount + 1);
        return this.updateById(position);
    }

    @Override
    public Boolean incrementApplyCount(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "职位ID错误");
        }

        Position position = this.getById(id);
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "职位不存在");
        }

        Long applyCount = position.getApplyCount();
        if (applyCount == null) {
            applyCount = 0L;
        }
        position.setApplyCount(applyCount + 1);
        return this.updateById(position);
    }
}
