package com.xiyuxian.positionmatch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xiyuxian.positionmatch.common.RestClientUtil;
import com.xiyuxian.positionmatch.constant.UserConstant;
import com.xiyuxian.positionmatch.manager.auth.StpKit;
import com.xiyuxian.positionmatch.mapper.PositionMapper;
import com.xiyuxian.positionmatch.mapper.UserMapper;
import com.xiyuxian.positionmatch.model.dto.recommendation.RecommendationRequest;
import com.xiyuxian.positionmatch.model.dto.recommendation.TrainModelRequest;
import com.xiyuxian.positionmatch.model.entity.Position;
import com.xiyuxian.positionmatch.model.entity.User;
import com.xiyuxian.positionmatch.model.vo.recommendation.RecommendationVO;
import com.xiyuxian.positionmatch.model.vo.recommendation.SimilarPositionVO;
import com.xiyuxian.positionmatch.model.vo.recommendation.SimilarUserVO;
import com.xiyuxian.positionmatch.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    @Resource
    private RestClientUtil restClientUtil;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${recommendation.python.api.url:http://localhost:8000}")
    private String pythonApiUrl;

    private static final String RECOMMENDATION_CACHE_PREFIX = "recommendation:";
    private static final String SIMILAR_USERS_CACHE_PREFIX = "similar_users:";
    private static final String SIMILAR_POSITIONS_CACHE_PREFIX = "similar_positions:";
    private static final String MODEL_TRAINING_LOCK = "model:training:lock";
    private static final long CACHE_EXPIRE_HOURS = 1;

    @Override
    public List<RecommendationVO> getRecommendations(RecommendationRequest request) {
        Long userId = request.getUserId();
        String algorithmType = StrUtil.isBlank(request.getAlgorithmType()) ? "hybrid" : request.getAlgorithmType();
        Integer n = request.getN() == null ? 10 : request.getN();

        String cacheKey = RECOMMENDATION_CACHE_PREFIX + userId + ":" + algorithmType + ":" + n;

        List<RecommendationVO> cachedResult = (List<RecommendationVO>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedResult != null && !cachedResult.isEmpty()) {
            log.info("从缓存获取推荐结果: userId={}, algorithmType={}", userId, algorithmType);
            return cachedResult;
        }

        try {
            String url = pythonApiUrl + "/api/recommendation/recommend";
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", userId);
            requestBody.put("algorithm_type", algorithmType);
            requestBody.put("n", n);

            String response = restClientUtil.get(url);
            log.info("调用Python推荐API: url={}, response={}", url, response);

            JSONObject jsonResponse = JSON.parseObject(response);
            if (jsonResponse.getInteger("code") == 200) {
                String dataStr = jsonResponse.getString("data");
                List<Map<String, Object>> dataList = JSON.parseObject(dataStr, new TypeReference<List<Map<String, Object>>>() {});

                List<RecommendationVO> recommendations = new ArrayList<>();
                for (Map<String, Object> data : dataList) {
                    Long positionId = Long.valueOf(data.get("position_id").toString());
                    Double score = Double.valueOf(data.get("score").toString());
                    String reason = (String) data.get("reason");

                    Position position = positionMapper.selectById(positionId);
                    if (position != null) {
                        RecommendationVO vo = new RecommendationVO();
                        vo.setPositionId(positionId);
                        vo.setPositionName(position.getPositionName());
                        vo.setCompanyName(position.getCompanyName());
                        vo.setSalaryRange(position.getSalaryRange());
                        vo.setWorkLocation(position.getWorkLocation());
                        vo.setEducation(position.getEducation());
                        vo.setMajor(position.getMajor());
                        vo.setSkills(position.getSkills());
                        vo.setScore(score);
                        vo.setReason(reason);
                        vo.setAlgorithmType(algorithmType);

                        recommendations.add(vo);
                    }
                }

                if (!recommendations.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, recommendations, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
                }

                return recommendations;
            } else {
                log.error("Python推荐API返回错误: {}", jsonResponse.getString("message"));
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("调用Python推荐API失败", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<SimilarUserVO> getSimilarUsers(Long userId, Integer n) {
        Integer count = n == null ? 5 : n;
        String cacheKey = SIMILAR_USERS_CACHE_PREFIX + userId + ":" + count;

        List<SimilarUserVO> cachedResult = (List<SimilarUserVO>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedResult != null && !cachedResult.isEmpty()) {
            log.info("从缓存获取相似用户: userId={}", userId);
            return cachedResult;
        }

        try {
            String url = pythonApiUrl + "/api/recommendation/similar-users/" + userId + "?n=" + count;
            String response = restClientUtil.get(url);

            JSONObject jsonResponse = JSON.parseObject(response);
            if (jsonResponse.getInteger("code") == 200) {
                String dataStr = jsonResponse.getString("data");
                List<Map<String, Object>> dataList = JSON.parseObject(dataStr, new TypeReference<List<Map<String, Object>>>() {});

                List<SimilarUserVO> similarUsers = new ArrayList<>();
                for (Map<String, Object> data : dataList) {
                    Long similarUserId = Long.valueOf(data.get("user_id").toString());
                    Double similarity = Double.valueOf(data.get("similarity").toString());

                    User user = userMapper.selectById(similarUserId);
                    if (user != null) {
                        SimilarUserVO vo = new SimilarUserVO();
                        vo.setUserId(similarUserId);
                        vo.setUserName(user.getUserName());
                        vo.setEducation(user.getEducation());
                        vo.setMajor(user.getMajor());
                        vo.setSchool(user.getSchool());
                        vo.setCompanyName(user.getCompanyName());
                        vo.setPosition(user.getPosition());
                        vo.setSimilarity(similarity);

                        similarUsers.add(vo);
                    }
                }

                if (!similarUsers.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, similarUsers, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
                }

                return similarUsers;
            } else {
                log.error("Python推荐API返回错误: {}", jsonResponse.getString("message"));
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("调用Python推荐API失败", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<SimilarPositionVO> getSimilarPositions(Long positionId, Integer n) {
        Integer count = n == null ? 10 : n;
        String cacheKey = SIMILAR_POSITIONS_CACHE_PREFIX + positionId + ":" + count;

        List<SimilarPositionVO> cachedResult = (List<SimilarPositionVO>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedResult != null && !cachedResult.isEmpty()) {
            log.info("从缓存获取相似职位: positionId={}", positionId);
            return cachedResult;
        }

        try {
            String url = pythonApiUrl + "/api/recommendation/similar-positions/" + positionId + "?n=" + count;
            String response = restClientUtil.get(url);

            JSONObject jsonResponse = JSON.parseObject(response);
            if (jsonResponse.getInteger("code") == 200) {
                String dataStr = jsonResponse.getString("data");
                List<Map<String, Object>> dataList = JSON.parseObject(dataStr, new TypeReference<List<Map<String, Object>>>() {});

                List<SimilarPositionVO> similarPositions = new ArrayList<>();
                for (Map<String, Object> data : dataList) {
                    Long similarPositionId = Long.valueOf(data.get("position_id").toString());
                    Double similarity = Double.valueOf(data.get("similarity").toString());

                    Position position = positionMapper.selectById(similarPositionId);
                    if (position != null) {
                        SimilarPositionVO vo = new SimilarPositionVO();
                        vo.setPositionId(similarPositionId);
                        vo.setPositionName(position.getPositionName());
                        vo.setCompanyName(position.getCompanyName());
                        vo.setSalaryRange(position.getSalaryRange());
                        vo.setWorkLocation(position.getWorkLocation());
                        vo.setSimilarity(similarity);

                        similarPositions.add(vo);
                    }
                }

                if (!similarPositions.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, similarPositions, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
                }

                return similarPositions;
            } else {
                log.error("Python推荐API返回错误: {}", jsonResponse.getString("message"));
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("调用Python推荐API失败", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Async
    public Boolean trainModel(TrainModelRequest request) {
        Boolean forceRetrain = request.getForceRetrain() != null && request.getForceRetrain();

        if (!forceRetrain) {
            Boolean isTraining = (Boolean) redisTemplate.opsForValue().get(MODEL_TRAINING_LOCK);
            if (isTraining != null && isTraining) {
                log.info("模型正在训练中，跳过本次训练");
                return false;
            }
        }

        try {
            redisTemplate.opsForValue().set(MODEL_TRAINING_LOCK, true, 30, TimeUnit.MINUTES);
            log.info("开始训练推荐模型...");

            String url = pythonApiUrl + "/api/recommendation/train";
            String response = restClientUtil.post(url, new HashMap<>());
            log.info("调用Python训练API: url={}, response={}", url, response);

            JSONObject jsonResponse = JSON.parseObject(response);
            if (jsonResponse.getInteger("code") == 200) {
                log.info("推荐模型训练成功");

                clearRecommendationCache();
                return true;
            } else {
                log.error("推荐模型训练失败: {}", jsonResponse.getString("message"));
                return false;
            }
        } catch (Exception e) {
            log.error("训练推荐模型失败", e);
            return false;
        } finally {
            redisTemplate.delete(MODEL_TRAINING_LOCK);
        }
    }

    @Override
    @Async
    public Boolean recordBehaviorToPython(Long userId, Long positionId, String behaviorType, Float rating) {
        try {
            String url = pythonApiUrl + "/api/recommendation/behavior";
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", userId);
            requestBody.put("position_id", positionId);
            requestBody.put("behavior_type", behaviorType);
            requestBody.put("rating", rating);

            String response = restClientUtil.post(url, requestBody);
            log.info("调用Python记录行为API: url={}, response={}", url, response);

            JSONObject jsonResponse = JSON.parseObject(response);
            if (jsonResponse.getInteger("code") == 200) {
                return true;
            } else {
                log.error("Python记录行为API返回错误: {}", jsonResponse.getString("message"));
                return false;
            }
        } catch (Exception e) {
            log.error("调用Python记录行为API失败", e);
            return false;
        }
    }

    @Override
    @Async
    public Boolean submitFeedback(Long userId, Long positionId, Long recommendationId, String feedbackType, String comment) {
        try {
            String url = pythonApiUrl + "/api/recommendation/feedback";
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", userId);
            requestBody.put("position_id", positionId);
            requestBody.put("recommendation_id", recommendationId);
            requestBody.put("feedback_type", feedbackType);
            requestBody.put("comment", comment);

            String response = restClientUtil.post(url, requestBody);
            log.info("调用Python反馈API: url={}, response={}", url, response);

            JSONObject jsonResponse = JSON.parseObject(response);
            if (jsonResponse.getInteger("code") == 200) {
                clearUserRecommendationCache(userId);
                return true;
            } else {
                log.error("Python反馈API返回错误: {}", jsonResponse.getString("message"));
                return false;
            }
        } catch (Exception e) {
            log.error("调用Python反馈API失败", e);
            return false;
        }
    }

    private void clearRecommendationCache() {
        Set<String> keys = redisTemplate.keys(RECOMMENDATION_CACHE_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("清除推荐缓存，共{}个key", keys.size());
        }
    }

    private void clearUserRecommendationCache(Long userId) {
        Set<String> keys = redisTemplate.keys(RECOMMENDATION_CACHE_PREFIX + userId + ":*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("清除用户推荐缓存: userId={}, 共{}个key", userId, keys.size());
        }
    }
}
