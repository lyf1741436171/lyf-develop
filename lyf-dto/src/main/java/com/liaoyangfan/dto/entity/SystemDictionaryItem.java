package com.liaoyangfan.dto.entity;

import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class SystemDictionaryItem extends PageRequest {

    private String id;
    //数据字典明细对应的分类id
    private Long parentId;

    //数据字典明细显示名称
    private String title;

    //数据字典明细在该分类中的排序
    private Integer sequence = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}