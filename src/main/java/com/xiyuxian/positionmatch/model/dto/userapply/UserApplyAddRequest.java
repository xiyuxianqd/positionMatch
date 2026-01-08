package com.xiyuxian.positionmatch.model.dto.userapply;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserApplyAddRequest implements Serializable {

    private Long userId;

    private Long positionId;

    private String resumeUrl;

    private String coverLetter;
}
