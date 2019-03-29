package com.liaoyangfan.client.service.impl;


import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.AssertUtil;
import cn.cloudlink.core.common.utils.StringUtil;
import com.liaoyangfan.client.domain.ISystemDictionaryDao;
import com.liaoyangfan.client.service.ISystemDictionaryService;
import com.liaoyangfan.dto.bo.SystemDictionaryBo;
import com.liaoyangfan.dto.entity.SystemDictionary;
import com.liaoyangfan.dto.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {

    @Autowired
    private ISystemDictionaryDao systemDictionaryDao;

    @Override
    public Object queryList(PageRequest pageRequest,SystemDictionaryBo systemDictionary) throws CustomException, Exception {
        return systemDictionaryDao.queryList(pageRequest,systemDictionary);
    }

    @Override
    public SystemDictionary get(String id) throws CustomException, Exception {
        AssertUtil.hasText(id, new CustomException("id不能为空"));
        return systemDictionaryDao.get(id);
    }

    @Override
    public int saveAndUpdate(SystemDictionary systemDictionary) throws CustomException, Exception {
        AssertUtil.hasText(systemDictionary.getSn(), new CustomException("sn不能为空"));
        AssertUtil.hasText(systemDictionary.getTitle(), new CustomException("标题不能为空"));
        int row = 0;
        if(StringUtil.hasText(systemDictionary.getId())){
            row = systemDictionaryDao.update(systemDictionary);
        }else {
            row = systemDictionaryDao.add(systemDictionary);
        }
        AssertUtil.isTrue(row != -1,new CustomException("数据操作失败"));
        return row;
    }

    @Override
    public int delete(String id) {
        AssertUtil.hasText(id, new CustomException("id不能为空"));
        return systemDictionaryDao.delete(id);
    }


}
