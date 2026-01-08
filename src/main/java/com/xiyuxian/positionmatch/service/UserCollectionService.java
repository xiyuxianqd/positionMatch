package com.xiyuxian.positionmatch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyuxian.positionmatch.model.dto.usercollection.UserCollectionAddRequest;
import com.xiyuxian.positionmatch.model.entity.UserCollection;

import java.util.List;

public interface UserCollectionService extends IService<UserCollection> {

    Long addCollection(UserCollectionAddRequest collectionAddRequest);

    Boolean removeCollection(Long userId, Long positionId);

    List<UserCollection> listCollectionsByUserId(Long userId);

    Boolean isCollected(Long userId, Long positionId);
}
