package com.liaoyangfan.client.service;


import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import com.liaoyangfan.dto.bo.SystemDictionaryBo;
import com.liaoyangfan.dto.entity.SystemDictionary;
import com.liaoyangfan.dto.exception.CustomException;

public interface ISystemDictionaryService {

    //分页查询
    Object queryList(PageRequest pageRequest, SystemDictionaryBo systemDictionary)throws CustomException,Exception;

    //根据id查询数据
    SystemDictionary get(String id)throws CustomException,Exception;

    //添加数据
    int saveAndUpdate(SystemDictionary systemDictionary)throws CustomException,Exception;

    //删除数据
    int delete(String id)throws CustomException,Exception;
}
