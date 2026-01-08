package com.xiyuxian.positionmatch.model.dto.recommendation;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrainModelRequest implements Serializable {

    private Boolean forceRetrain;
}
