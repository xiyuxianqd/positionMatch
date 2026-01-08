package com.xiyuxian.positionmatch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyuxian.positionmatch.mapper.UserMapper;
import com.xiyuxian.positionmatch.model.entity.User;
import com.xiyuxian.positionmatch.model.vo.statistics.*;
import com.xiyuxian.positionmatch.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public EmploymentStatisticsVO getEmploymentStatistics() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_role", "student");
        List<User> students = userMapper.selectList(queryWrapper);

        int totalGraduates = students.size();
        int employedGraduates = (int) students.stream()
                .filter(u -> u.getCompanyName() != null && !u.getCompanyName().isEmpty())
                .count();

        int employmentRate = totalGraduates > 0 ? (employedGraduates * 100 / totalGraduates) : 0;

        int averageSalary = students.stream()
                .filter(u -> u.getPosition() != null && !u.getPosition().isEmpty())
                .mapToInt(u -> extractSalaryFromPosition(u.getPosition()))
                .filter(s -> s > 0)
                .findFirst()
                .orElse(0);

        Map<String, Long> industryCount = students.stream()
                .filter(u -> u.getCompanyName() != null && !u.getCompanyName().isEmpty())
                .collect(Collectors.groupingBy(
                        u -> extractIndustryFromCompany(u.getCompanyName()),
                        Collectors.counting()
                ));

        String topIndustry = industryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("未知");

        int topIndustryCount = industryCount.getOrDefault(topIndustry, 0L).intValue();

        EmploymentStatisticsVO statistics = new EmploymentStatisticsVO();
        statistics.setTotalGraduates(totalGraduates);
        statistics.setEmployedGraduates(employedGraduates);
        statistics.setEmploymentRate(employmentRate);
        statistics.setAverageSalary(averageSalary);
        statistics.setTopIndustry(topIndustry);
        statistics.setTopIndustryCount(topIndustryCount);

        return statistics;
    }

    @Override
    public List<MajorEmploymentVO> getMajorEmploymentStatistics() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_role", "student");
        queryWrapper.isNotNull("major");
        queryWrapper.ne("major", "");
        List<User> students = userMapper.selectList(queryWrapper);

        Map<String, List<User>> studentsByMajor = students.stream()
                .collect(Collectors.groupingBy(User::getMajor));

        List<MajorEmploymentVO> result = new ArrayList<>();

        for (Map.Entry<String, List<User>> entry : studentsByMajor.entrySet()) {
            String major = entry.getKey();
            List<User> majorStudents = entry.getValue();

            int totalStudents = majorStudents.size();
            int employedStudents = (int) majorStudents.stream()
                    .filter(u -> u.getCompanyName() != null && !u.getCompanyName().isEmpty())
                    .count();

            double employmentRate = totalStudents > 0 ?
                    BigDecimal.valueOf(employedStudents * 100.0 / totalStudents)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue() : 0.0;

            int averageSalary = majorStudents.stream()
                    .filter(u -> u.getPosition() != null && !u.getPosition().isEmpty())
                    .mapToInt(u -> extractSalaryFromPosition(u.getPosition()))
                    .filter(s -> s > 0)
                    .findFirst()
                    .orElse(0);

            String topCompany = majorStudents.stream()
                    .filter(u -> u.getCompanyName() != null && !u.getCompanyName().isEmpty())
                    .collect(Collectors.groupingBy(User::getCompanyName, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("未知");

            MajorEmploymentVO vo = new MajorEmploymentVO();
            vo.setMajor(major);
            vo.setTotalStudents(totalStudents);
            vo.setEmployedStudents(employedStudents);
            vo.setEmploymentRate(employmentRate);
            vo.setAverageSalary(averageSalary);
            vo.setTopCompany(topCompany);

            result.add(vo);
        }

        result.sort((a, b) -> Double.compare(b.getEmploymentRate(), a.getEmploymentRate()));

        return result;
    }

    @Override
    public List<EducationEmploymentVO> getEducationEmploymentStatistics() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_role", "student");
        queryWrapper.isNotNull("education");
        queryWrapper.ne("education", "");
        List<User> students = userMapper.selectList(queryWrapper);

        Map<String, List<User>> studentsByEducation = students.stream()
                .collect(Collectors.groupingBy(User::getEducation));

        List<EducationEmploymentVO> result = new ArrayList<>();

        for (Map.Entry<String, List<User>> entry : studentsByEducation.entrySet()) {
            String education = entry.getKey();
            List<User> educationStudents = entry.getValue();

            int totalStudents = educationStudents.size();
            int employedStudents = (int) educationStudents.stream()
                    .filter(u -> u.getCompanyName() != null && !u.getCompanyName().isEmpty())
                    .count();

            double employmentRate = totalStudents > 0 ?
                    BigDecimal.valueOf(employedStudents * 100.0 / totalStudents)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue() : 0.0;

            int averageSalary = educationStudents.stream()
                    .filter(u -> u.getPosition() != null && !u.getPosition().isEmpty())
                    .mapToInt(u -> extractSalaryFromPosition(u.getPosition()))
                    .filter(s -> s > 0)
                    .findFirst()
                    .orElse(0);

            EducationEmploymentVO vo = new EducationEmploymentVO();
            vo.setEducation(education);
            vo.setTotalStudents(totalStudents);
            vo.setEmployedStudents(employedStudents);
            vo.setEmploymentRate(employmentRate);
            vo.setAverageSalary(averageSalary);

            result.add(vo);
        }

        result.sort((a, b) -> Double.compare(b.getEmploymentRate(), a.getEmploymentRate()));

        return result;
    }

    @Override
    public List<IndustryDistributionVO> getIndustryDistribution() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_role", "student");
        queryWrapper.isNotNull("company_name");
        queryWrapper.ne("company_name", "");
        List<User> students = userMapper.selectList(queryWrapper);

        int totalEmployed = students.size();

        Map<String, Long> industryCount = students.stream()
                .collect(Collectors.groupingBy(
                        u -> extractIndustryFromCompany(u.getCompanyName()),
                        Collectors.counting()
                ));

        List<IndustryDistributionVO> result = new ArrayList<>();

        for (Map.Entry<String, Long> entry : industryCount.entrySet()) {
            String industry = entry.getKey();
            int count = entry.getValue().intValue();

            double percentage = totalEmployed > 0 ?
                    BigDecimal.valueOf(count * 100.0 / totalEmployed)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue() : 0.0;

            IndustryDistributionVO vo = new IndustryDistributionVO();
            vo.setIndustry(industry);
            vo.setCount(count);
            vo.setPercentage(percentage);

            result.add(vo);
        }

        result.sort((a, b) -> Double.compare(b.getPercentage(), a.getPercentage()));

        return result;
    }

    @Override
    public List<YearlyTrendVO> getYearlyTrend() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_role", "student");
        queryWrapper.isNotNull("graduation_year");
        List<User> students = userMapper.selectList(queryWrapper);

        Map<Integer, List<User>> studentsByYear = students.stream()
                .filter(u -> u.getGraduationYear() != null)
                .collect(Collectors.groupingBy(User::getGraduationYear));

        List<YearlyTrendVO> result = new ArrayList<>();

        for (Map.Entry<Integer, List<User>> entry : studentsByYear.entrySet()) {
            Integer year = entry.getKey();
            List<User> yearStudents = entry.getValue();

            int totalGraduates = yearStudents.size();
            int employedGraduates = (int) yearStudents.stream()
                    .filter(u -> u.getCompanyName() != null && !u.getCompanyName().isEmpty())
                    .count();

            double employmentRate = totalGraduates > 0 ?
                    BigDecimal.valueOf(employedGraduates * 100.0 / totalGraduates)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue() : 0.0;

            int averageSalary = yearStudents.stream()
                    .filter(u -> u.getPosition() != null && !u.getPosition().isEmpty())
                    .mapToInt(u -> extractSalaryFromPosition(u.getPosition()))
                    .filter(s -> s > 0)
                    .findFirst()
                    .orElse(0);

            YearlyTrendVO vo = new YearlyTrendVO();
            vo.setYear(year);
            vo.setTotalGraduates(totalGraduates);
            vo.setEmployedGraduates(employedGraduates);
            vo.setEmploymentRate(employmentRate);
            vo.setAverageSalary(averageSalary);

            result.add(vo);
        }

        result.sort(Comparator.comparing(YearlyTrendVO::getYear));

        return result;
    }

    @Override
    public List<YearlyTrendVO> getYearlyTrendByMajor(String major) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_role", "student");
        queryWrapper.eq("major", major);
        queryWrapper.isNotNull("graduation_year");
        List<User> students = userMapper.selectList(queryWrapper);

        Map<Integer, List<User>> studentsByYear = students.stream()
                .filter(u -> u.getGraduationYear() != null)
                .collect(Collectors.groupingBy(User::getGraduationYear));

        List<YearlyTrendVO> result = new ArrayList<>();

        for (Map.Entry<Integer, List<User>> entry : studentsByYear.entrySet()) {
            Integer year = entry.getKey();
            List<User> yearStudents = entry.getValue();

            int totalGraduates = yearStudents.size();
            int employedGraduates = (int) yearStudents.stream()
                    .filter(u -> u.getCompanyName() != null && !u.getCompanyName().isEmpty())
                    .count();

            double employmentRate = totalGraduates > 0 ?
                    BigDecimal.valueOf(employedGraduates * 100.0 / totalGraduates)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue() : 0.0;

            int averageSalary = yearStudents.stream()
                    .filter(u -> u.getPosition() != null && !u.getPosition().isEmpty())
                    .mapToInt(u -> extractSalaryFromPosition(u.getPosition()))
                    .filter(s -> s > 0)
                    .findFirst()
                    .orElse(0);

            YearlyTrendVO vo = new YearlyTrendVO();
            vo.setYear(year);
            vo.setTotalGraduates(totalGraduates);
            vo.setEmployedGraduates(employedGraduates);
            vo.setEmploymentRate(employmentRate);
            vo.setAverageSalary(averageSalary);

            result.add(vo);
        }

        result.sort(Comparator.comparing(YearlyTrendVO::getYear));

        return result;
    }

    private int extractSalaryFromPosition(String position) {
        if (position == null || position.isEmpty()) {
            return 0;
        }

        try {
            String[] parts = position.split("-");
            if (parts.length >= 2) {
                String salaryStr = parts[1].replaceAll("[^0-9]", "");
                return Integer.parseInt(salaryStr);
            }
        } catch (Exception e) {
            log.error("解析薪资失败: {}", position, e);
        }

        return 0;
    }

    private String extractIndustryFromCompany(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            return "未知";
        }

        if (companyName.contains("科技") || companyName.contains("软件") || companyName.contains("互联网")) {
            return "互联网/科技";
        } else if (companyName.contains("金融") || companyName.contains("银行") || companyName.contains("证券")) {
            return "金融";
        } else if (companyName.contains("教育") || companyName.contains("学校")) {
            return "教育";
        } else if (companyName.contains("医疗") || companyName.contains("医院") || companyName.contains("制药")) {
            return "医疗健康";
        } else if (companyName.contains("制造") || companyName.contains("工厂")) {
            return "制造业";
        } else if (companyName.contains("零售") || companyName.contains("电商")) {
            return "零售/电商";
        } else if (companyName.contains("房地产")) {
            return "房地产";
        } else if (companyName.contains("交通") || companyName.contains("物流")) {
            return "交通/物流";
        } else {
            return "其他";
        }
    }
}
