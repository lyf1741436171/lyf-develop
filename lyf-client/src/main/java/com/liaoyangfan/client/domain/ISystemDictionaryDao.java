package com.liaoyangfan.client.domain;


import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import com.liaoyangfan.dto.bo.SystemDictionaryBo;
import com.liaoyangfan.dto.entity.SystemDictionary;

public interface ISystemDictionaryDao {
    Object queryList(PageRequest pageRequest, SystemDictionaryBo systemDictionary);

    SystemDictionary get(String id);

    int add(SystemDictionary systemDictionary);

    int update(SystemDictionary systemDictionary);

    int delete(String id);
}
