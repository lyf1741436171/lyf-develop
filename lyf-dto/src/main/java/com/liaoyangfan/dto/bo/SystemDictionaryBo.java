package com.liaoyangfan.dto.bo;


import cn.cloudlink.core.common.dataaccess.data.PageRequest;

public class SystemDictionaryBo extends PageRequest {

    private String id;
    // 数据字典分类编码
    private String sn;

    // 数据字典分类名称
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}