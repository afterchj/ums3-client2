package com.web.controller;

import com.model.HPFeatured;
import com.model.dd.ResultDict;
import com.service.HPFeaturedService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 新推荐位接口
 * Created by nannan.li on 2018/6/7.
 */
@Controller("hPFeaturedController")
@RequestMapping("/home")
public class HPFeaturedController {

    @Resource
    private HPFeaturedService hpFeaturedService;

    @RequestMapping("/getHPFeatured")
    public Map<String,Object> getHPFeatured(){
        Map<String,Object> result = new HashedMap();
        List<HPFeatured> hpFeatureds = null;
        try {
            hpFeatureds = hpFeaturedService.getHPFeatured();
            if (hpFeatureds.size()>0){
                result.put("data", hpFeatureds);
                result.put("result",  ResultDict.SUCCESS.getCode());
            }else{
                result.put("result", ResultDict.SYSTEM_ERROR.getCode());
            }
        }catch (Exception e){
            System.err.println(e.getCause());
            result.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return result;
    }
}
