package com.xiyuxian.positionmatch.model.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.gson.Gson;

import java.io.Serializable;

public class BaseBean implements Serializable {

    public <T> T fromBean(Object srcObj, Class<T> requiredType) {
        try {
            T targetObj = requiredType.newInstance();
            BeanUtil.copyProperties(srcObj, targetObj, CopyOptions.create().setIgnoreNullValue(true));
            return targetObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T toBean(Class<T> requiredType) {
        return this.fromBean(this, requiredType);
    }

    public final String toJsonString() {
        return new Gson().toJson(this);
    }
}
