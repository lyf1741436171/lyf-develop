package com.liaoyangfan.client.domain.impl;


import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.StringUtil;
import com.liaoyangfan.client.domain.ISystemDictionaryDao;
import com.liaoyangfan.dto.bo.SystemDictionaryBo;
import com.liaoyangfan.dto.entity.SystemDictionary;
import com.liaoyangfan.dto.exception.CustomException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SystemDictionaryDaoImpl implements ISystemDictionaryDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private BaseJdbcDao baseJdbcDao;

    static String SELECT_SYSTEMDICTIONARY_SQL = "";
    static String INSET_SYSTEMDICTIONARY_SQL = "";
    static String UPDATE_SYSTEMDICTIONARY_SQL = "";
    static String DELETE_SYSTEMDICTIONARY_SQL = "";

    static {
        SELECT_SYSTEMDICTIONARY_SQL = "select id, sn, title from system_dictionary where 1=1";
        INSET_SYSTEMDICTIONARY_SQL = "INSERT INTO system_dictionary( sn, title) VALUES ( ?, ? )";
        UPDATE_SYSTEMDICTIONARY_SQL = "UPDATE system_dictionary set sn = ? , title = ? where id = ? ";
        DELETE_SYSTEMDICTIONARY_SQL = "DELETE from system_dictionary where  id = ? ";
    }

    @Override
    public Object queryList(PageRequest pageRequest, SystemDictionaryBo systemDictionary) {
        StringBuffer sql = new StringBuffer(SELECT_SYSTEMDICTIONARY_SQL);
        Map<String, Object> map = getQueryCondition(systemDictionary);
        sql.append(map.get("where"));
        Object[] args = (Object[]) map.get("args");
        Page page = baseJdbcDao.queryPage(pageRequest,sql.toString(),args, SystemDictionary.class);
        return page;
    }

    @Override
    public SystemDictionary get(String id) {
        StringBuffer sql = new StringBuffer(SELECT_SYSTEMDICTIONARY_SQL);
        sql.append(" and id = '"+id+"'");
        sql.append(" limit 1 ");
        SystemDictionary systemDictionary = (SystemDictionary) baseJdbcDao.queryForObject(sql.toString(), null,SystemDictionary.class);
        return systemDictionary;
    }

    @Override
    public int add(SystemDictionary systemDictionary) {
        Object[] args = {systemDictionary.getSn(),systemDictionary.getTitle()};
        int save = baseJdbcDao.save(INSET_SYSTEMDICTIONARY_SQL, args);
        return save;
    }

    @Override
    public int update(SystemDictionary systemDictionary) {
        Object[] args = {systemDictionary.getSn(),systemDictionary.getTitle(),systemDictionary.getId()};
        int update = baseJdbcDao.update(UPDATE_SYSTEMDICTIONARY_SQL, args);

        return update;
    }

    @Override
    public int delete(String id) {
        Object[] args = {id};
        int delete = baseJdbcDao.delete(DELETE_SYSTEMDICTIONARY_SQL, args);
        if(delete != 1){
            throw new CustomException("删除失败");
        }
        return delete;
    }

    private Map<String,Object> getQueryCondition(SystemDictionaryBo systemDictionary) {
        // 拼接查询条件
        StringBuffer whereBuffer = new StringBuffer();
        List<Object> args = new ArrayList<Object>();

        if(StringUtil.hasText(systemDictionary.getTitle())){
            whereBuffer.append(" and  title like ? ");
            args.add("%"+systemDictionary.getTitle()+"%");
        }
        if(StringUtil.hasText(systemDictionary.getSn())){
            whereBuffer.append(" and sn = ? ");
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("where",whereBuffer.toString());
        resultMap.put("args",args.toArray());
        return resultMap;
    }


}
