package com.liaoyangfan.client.controller;


import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import com.liaoyangfan.client.service.ISystemDictionaryService;
import com.liaoyangfan.dto.bo.SystemDictionaryBo;
import com.liaoyangfan.dto.comment.ResponseResult;
import com.liaoyangfan.dto.entity.SystemDictionary;
import com.liaoyangfan.dto.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据字典Controller
 */
@RestController
@RequestMapping("systemDictionary")
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @PostMapping("queryList")
    public Object queryList(@RequestBody SystemDictionaryBo systemDictionary){
        try {
            PageRequest pageRequest = new PageRequest(systemDictionary.getPageNum(),systemDictionary.getPageSize(),systemDictionary.getOrderBy(),systemDictionary.isCountTotal());
            Page page = (Page) systemDictionaryService.queryList(pageRequest, systemDictionary);
            return new ResponseResult(page);
        }catch(CustomException e){
            return new ResponseResult(e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseResult(-400,e.getMessage());
        }
    }

    @GetMapping("get")
    public Object get(@RequestParam("id") String id){
        try {
            return new ResponseResult(systemDictionaryService.get(id));
        }catch(CustomException e){
            return new ResponseResult(e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseResult(400,e.getMessage());
        }
    }

    @PostMapping("saveAndUpdate")
    public Object saveAndUpdate(@RequestBody SystemDictionary systemDictionary){
        try {
            return new ResponseResult(systemDictionaryService.saveAndUpdate(systemDictionary));
        }catch(CustomException e){
            return new ResponseResult(e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseResult(400,e.getMessage());
        }
    }

    @PostMapping("delete")
    public Object delete(@RequestParam("id") String id){
        try {
            return new ResponseResult(systemDictionaryService.delete(id));
        }catch(CustomException e){
            return new ResponseResult(e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseResult(400,e.getMessage());
        }
    }
}
