-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_account` VARCHAR(50) NOT NULL COMMENT '用户账号',
    `user_password` VARCHAR(100) NOT NULL COMMENT '用户密码',
    `user_name` VARCHAR(50) DEFAULT NULL COMMENT '用户昵称',
    `user_avatar` VARCHAR(500) DEFAULT NULL COMMENT '用户头像',
    `user_profile` VARCHAR(500) DEFAULT NULL COMMENT '用户简介',
    `user_role` VARCHAR(20) NOT NULL DEFAULT 'student' COMMENT '用户角色：student-学生，hr-企业HR，admin-管理员',
    `education` VARCHAR(50) DEFAULT NULL COMMENT '学历',
    `major` VARCHAR(50) DEFAULT NULL COMMENT '专业',
    `skills` VARCHAR(500) DEFAULT NULL COMMENT '技能',
    `graduation_year` INT DEFAULT NULL COMMENT '毕业年份',
    `school` VARCHAR(100) DEFAULT NULL COMMENT '学校',
    `company_name` VARCHAR(100) DEFAULT NULL COMMENT '公司名称',
    `position` VARCHAR(50) DEFAULT NULL COMMENT '职位',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `resume_url` VARCHAR(500) DEFAULT NULL COMMENT '简历链接',
    `user_status` INT DEFAULT 0 COMMENT '用户状态：0-正常，1-禁用',
    `version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    `add_user_id` BIGINT DEFAULT NULL COMMENT '添加人ID',
    `add_user_name` VARCHAR(50) DEFAULT NULL COMMENT '添加人姓名',
    `edit_user_id` BIGINT DEFAULT NULL COMMENT '编辑人ID',
    `edit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '编辑人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete` INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_account` (`user_account`),
    KEY `idx_user_role` (`user_role`),
    KEY `idx_user_status` (`user_status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 公司表
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '公司ID',
    `company_name` VARCHAR(100) NOT NULL COMMENT '公司名称',
    `company_desc` TEXT DEFAULT NULL COMMENT '公司简介',
    `industry` VARCHAR(50) DEFAULT NULL COMMENT '所属行业',
    `company_size` VARCHAR(50) DEFAULT NULL COMMENT '公司规模',
    `company_address` VARCHAR(200) DEFAULT NULL COMMENT '公司地址',
    `company_logo` VARCHAR(500) DEFAULT NULL COMMENT '公司Logo',
    `website` VARCHAR(200) DEFAULT NULL COMMENT '公司官网',
    `contact_person` VARCHAR(50) DEFAULT NULL COMMENT '联系人',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `contact_email` VARCHAR(100) DEFAULT NULL COMMENT '联系邮箱',
    `company_status` INT DEFAULT 0 COMMENT '公司状态：0-正常，1-已禁用',
    `version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    `add_user_id` BIGINT DEFAULT NULL COMMENT '添加人ID',
    `add_user_name` VARCHAR(50) DEFAULT NULL COMMENT '添加人姓名',
    `edit_user_id` BIGINT DEFAULT NULL COMMENT '编辑人ID',
    `edit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '编辑人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete` INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_company_name` (`company_name`),
    KEY `idx_industry` (`industry`),
    KEY `idx_company_status` (`company_status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司表';

-- 职位表
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '职位ID',
    `position_name` VARCHAR(100) NOT NULL COMMENT '职位名称',
    `company_name` VARCHAR(100) NOT NULL COMMENT '公司名称',
    `company_id` BIGINT DEFAULT NULL COMMENT '公司ID',
    `position_desc` TEXT DEFAULT NULL COMMENT '职位描述',
    `requirements` TEXT DEFAULT NULL COMMENT '职位要求',
    `salary_range` VARCHAR(50) DEFAULT NULL COMMENT '薪资范围',
    `work_location` VARCHAR(100) DEFAULT NULL COMMENT '工作地点',
    `education` VARCHAR(50) DEFAULT NULL COMMENT '学历要求',
    `major` VARCHAR(50) DEFAULT NULL COMMENT '专业要求',
    `skills` VARCHAR(500) DEFAULT NULL COMMENT '技能要求',
    `work_years` INT DEFAULT NULL COMMENT '工作年限要求',
    `position_type` VARCHAR(50) DEFAULT NULL COMMENT '职位类型：全职、兼职、实习',
    `tags` VARCHAR(500) DEFAULT NULL COMMENT '职位标签，多个标签用逗号分隔',
    `position_status` INT DEFAULT 0 COMMENT '职位状态：0-正常，1-已关闭，2-已招满',
    `view_count` INT DEFAULT 0 COMMENT '浏览次数',
    `apply_count` BIGINT DEFAULT 0 COMMENT '申请次数',
    `version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    `add_user_id` BIGINT DEFAULT NULL COMMENT '添加人ID',
    `add_user_name` VARCHAR(50) DEFAULT NULL COMMENT '添加人姓名',
    `edit_user_id` BIGINT DEFAULT NULL COMMENT '编辑人ID',
    `edit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '编辑人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete` INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_company_id` (`company_id`),
    KEY `idx_position_type` (`position_type`),
    KEY `idx_position_status` (`position_status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位表';

-- 用户行为表
DROP TABLE IF EXISTS `user_behavior`;
CREATE TABLE `user_behavior` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '行为ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `position_id` BIGINT NOT NULL COMMENT '职位ID',
    `behavior_type` VARCHAR(20) NOT NULL COMMENT '行为类型：view-浏览，apply-申请，collect-收藏，feedback-反馈',
    `rating` FLOAT DEFAULT 0.0 COMMENT '评分：0-1之间，用于协同过滤',
    `version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    `add_user_id` BIGINT DEFAULT NULL COMMENT '添加人ID',
    `add_user_name` VARCHAR(50) DEFAULT NULL COMMENT '添加人姓名',
    `edit_user_id` BIGINT DEFAULT NULL COMMENT '编辑人ID',
    `edit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '编辑人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete` INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_position_id` (`position_id`),
    KEY `idx_user_position` (`user_id`, `position_id`),
    KEY `idx_behavior_type` (`behavior_type`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为表';

-- 用户收藏表
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `position_id` BIGINT NOT NULL COMMENT '职位ID',
    `version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    `add_user_id` BIGINT DEFAULT NULL COMMENT '添加人ID',
    `add_user_name` VARCHAR(50) DEFAULT NULL COMMENT '添加人姓名',
    `edit_user_id` BIGINT DEFAULT NULL COMMENT '编辑人ID',
    `edit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '编辑人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete` INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_position` (`user_id`, `position_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_position_id` (`position_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏表';

-- 用户申请表
DROP TABLE IF EXISTS `user_apply`;
CREATE TABLE `user_apply` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '申请ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `position_id` BIGINT NOT NULL COMMENT '职位ID',
    `apply_status` VARCHAR(20) DEFAULT 'pending' COMMENT '申请状态：pending-待处理，approved-已通过，rejected-已拒绝',
    `resume_url` VARCHAR(500) DEFAULT NULL COMMENT '简历链接',
    `cover_letter` TEXT DEFAULT NULL COMMENT '求职信',
    `version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    `add_user_id` BIGINT DEFAULT NULL COMMENT '添加人ID',
    `add_user_name` VARCHAR(50) DEFAULT NULL COMMENT '添加人姓名',
    `edit_user_id` BIGINT DEFAULT NULL COMMENT '编辑人ID',
    `edit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '编辑人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete` INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_position_id` (`position_id`),
    KEY `idx_apply_status` (`apply_status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户申请表';

-- 插入测试数据
INSERT INTO `user` (`user_account`, `user_password`, `user_name`, `user_role`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin'),
('hr001', 'e10adc3949ba59abbe56e057f20f883e', 'HR张三', 'hr'),
('student001', 'e10adc3949ba59abbe56e057f20f883e', '学生李四', 'student');

-- 密码都是：12345678（MD5加密后）
