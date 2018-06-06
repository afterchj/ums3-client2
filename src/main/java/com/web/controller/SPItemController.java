package com.web.controller;

import com.model.SPFile;
import com.model.SPItem;
import com.model.dd.ResultDict;
import com.service.SPItemService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjian.chen on 2018/1/11.
 */

@Controller
@RequestMapping("/spfile")
public class SPItemController extends BaseDecodedController {

    @Resource
    SPItemService itemService;

    @ResponseBody
    @RequestMapping(value = "/allParentTypes", produces = "application/json; charset=utf-8")
    public Map<String, Object> parentType() {
        Map<String, Object> result = new HashedMap();
        List<SPItem> list = itemService.getParentTypes();
        if (list.size() > 0) {
            result.put("data", list);
            result.put("result", ResultDict.SUCCESS.getCode());
        } else {
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/allSubTypes", produces = "application/json; charset=utf-8")
    public Map<String, Object> subType() {
        Map<String, Object> result = new HashedMap();
        List<SPItem> list = itemService.getSubTypes();
        if (list.size() > 0) {
            result.put("data", list);
            result.put("result", ResultDict.SUCCESS.getCode());
        } else {
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getChildrenTypes/pid{id}", produces = "application/json; charset=utf-8")
    public Map<String, Object> getSubTypeById(@PathVariable("id") int id) {
        Map<String, Object> result = new HashedMap();
        List<SPItem> list = itemService.getSubTypesById(id);
        if (list.size() > 0) {
            result.put("data", list);
            result.put("result", ResultDict.SUCCESS.getCode());
        } else {
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getChildrenTypes/info{id}", produces = "application/json; charset=utf-8")
    public Map<String, Object> getSubTypeInfo(@PathVariable("id") int id) {
        Map<String, Object> result = new HashedMap();
        List<SPFile> list = null;
        try {
            list = itemService.getInfo(id);
        } catch (Exception e) {
            System.err.println(e.getCause());
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        if (list.size() > 0) {
            result.put("data", list);
            result.put("result", ResultDict.SUCCESS.getCode());
        } else {
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getNewTypeByNewId/newId{id}", produces = "application/json; charset=utf-8")
    public Map<String, Object> getNewTypeByNewId(@PathVariable("id") String id){
        Map<String, Object> result = new HashedMap();
        List<SPItem> list = null;
        try {
            list = itemService.getNewId(id);
        } catch (Exception e) {
            System.err.println(e.getCause());
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        if (list.size() > 0) {
            result.put("data", list);
            result.put("result", ResultDict.SUCCESS.getCode());
        } else {
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return result;
    }

}
