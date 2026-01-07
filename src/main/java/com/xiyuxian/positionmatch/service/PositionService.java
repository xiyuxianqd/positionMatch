package com.xiyuxian.positionmatch.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyuxian.positionmatch.model.dto.position.PositionAddRequest;
import com.xiyuxian.positionmatch.model.dto.position.PositionQueryRequest;
import com.xiyuxian.positionmatch.model.dto.position.PositionUpdateRequest;
import com.xiyuxian.positionmatch.model.entity.Position;
import com.xiyuxian.positionmatch.model.vo.PositionVO;

import java.util.List;

public interface PositionService extends IService<Position> {

    Long addPosition(PositionAddRequest positionAddRequest);

    Boolean updatePosition(PositionUpdateRequest positionUpdateRequest);

    Boolean deletePosition(Long id);

    PositionVO getPositionById(Long id);

    PositionVO getPositionVO(Position position);

    List<PositionVO> getPositionVOList(List<Position> positionList);

    Page<PositionVO> listPositionVOByPage(PositionQueryRequest positionQueryRequest);

    QueryWrapper<Position> getQueryWrapper(PositionQueryRequest positionQueryRequest);

    Boolean incrementViewCount(Long id);

    Boolean incrementApplyCount(Long id);
}
