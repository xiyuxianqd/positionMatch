package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableLogic
    private Integer isDelete;

    @Version
    private Integer version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(value = "add_user_id", fill = FieldFill.INSERT)
    private Long addUserId;

    @TableField(value = "add_user_name", fill = FieldFill.INSERT)
    private String addUserName;

    @TableField(value = "edit_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long editUserId;

    @TableField(value = "edit_user_name", fill = FieldFill.INSERT_UPDATE)
    private String editUserName;

    @TableField(value = "company_id")
    private Long companyId;
}
