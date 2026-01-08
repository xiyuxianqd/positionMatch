package com.xiyuxian.positionmatch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuxian.positionmatch.mapper.UserCollectionMapper;
import com.xiyuxian.positionmatch.model.dto.usercollection.UserCollectionAddRequest;
import com.xiyuxian.positionmatch.model.entity.UserCollection;
import com.xiyuxian.positionmatch.service.UserBehaviorService;
import com.xiyuxian.positionmatch.service.UserCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {

    @Resource
    private UserBehaviorService userBehaviorService;

    @Override
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Long addCollection(UserCollectionAddRequest collectionAddRequest) {
        Long userId = collectionAddRequest.getUserId();
        Long positionId = collectionAddRequest.getPositionId();

        if (isCollected(userId, positionId)) {
            throw new RuntimeException("该职位已收藏");
        }

        UserCollection userCollection = new UserCollection();
        BeanUtils.copyProperties(collectionAddRequest, userCollection);
        boolean result = this.save(userCollection);

        if (!result) {
            throw new RuntimeException("收藏失败");
        }

        userBehaviorService.recordBehavior(userId, positionId, "collect", 0.8f);

        return userCollection.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeCollection(Long userId, Long positionId) {
        QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("position_id", positionId);

        boolean result = this.remove(queryWrapper);
        return result;
    }

    @Override
    public List<UserCollection> listCollectionsByUserId(Long userId) {
        QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    @Override
    public Boolean isCollected(Long userId, Long positionId) {
        QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("position_id", positionId);
        return this.count(queryWrapper) > 0;
    }
}
