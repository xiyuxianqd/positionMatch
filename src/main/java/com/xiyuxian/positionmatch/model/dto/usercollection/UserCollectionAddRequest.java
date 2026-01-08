package com.xiyuxian.positionmatch.model.dto.usercollection;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCollectionAddRequest implements Serializable {

    private Long userId;

    private Long positionId;
}
