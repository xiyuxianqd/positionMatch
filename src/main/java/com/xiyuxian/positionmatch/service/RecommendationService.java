package com.xiyuxian.positionmatch.service;

import com.xiyuxian.positionmatch.model.dto.recommendation.RecommendationRequest;
import com.xiyuxian.positionmatch.model.dto.recommendation.TrainModelRequest;
import com.xiyuxian.positionmatch.model.vo.recommendation.RecommendationVO;
import com.xiyuxian.positionmatch.model.vo.recommendation.SimilarPositionVO;
import com.xiyuxian.positionmatch.model.vo.recommendation.SimilarUserVO;

import java.util.List;

public interface RecommendationService {

    List<RecommendationVO> getRecommendations(RecommendationRequest request);

    List<SimilarUserVO> getSimilarUsers(Long userId, Integer n);

    List<SimilarPositionVO> getSimilarPositions(Long positionId, Integer n);

    Boolean trainModel(TrainModelRequest request);

    Boolean recordBehaviorToPython(Long userId, Long positionId, String behaviorType, Float rating);

    Boolean submitFeedback(Long userId, Long positionId, Long recommendationId, String feedbackType, String comment);
}
