package com.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.model.dd.ResultDict;
import com.service.SPAdvertisementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.service.AdvertisementService;
import com.service.FileStoreInfoService;
import com.utils.FrontDisplayUtils;
import com.web.vo.FrontCategoryInfoVo;
import com.web.vo.FrontThemeFileVo;

@Controller("homeController")
@RequestMapping("/app")
public class HomeController extends BaseDecodedController {

    private FileStoreInfoService fileStoreInfoService;
    private AdvertisementService advertisementService;
    private SPAdvertisementService spadvertisementService;
    private Logger logger = Logger.getLogger(HomeController.class);


    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException re, HttpServletRequest request) {
        if (request.getContextPath().indexOf("\\/m\\/") > 0) {
            return "forword:/app/m/home";
        } else {
            return "forward:/app/f/home";
        }
    }

    @RequestMapping("/home")
    public String oldHome(String g, ModelMap model) {
        List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getHomePage(g);
        String top = FrontDisplayUtils.json(advertisementService.getByType("store"));
        String bottom = FrontDisplayUtils.json(advertisementService.getByType("sbot"));
        model.put("tbars", top);
        model.put("bbars", bottom);
        model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
        return "home";
    }


    @RequestMapping("/{gender}/home")
    public String home(@PathVariable("gender") String gender, ModelMap model) {
        oldHome(gender, model);
        return "home";
    }

    @RequestMapping("/home_{num}")
    public String newHome(@PathVariable("num") Integer num, ModelMap model) {
        try {
            List<FrontCategoryInfoVo> fileStoreInfoVos = fileStoreInfoService.getNewHomePage(num);
            String topicBars = FrontDisplayUtils.json(advertisementService.getByType("category"));
            model.put("tbars", topicBars);
            model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping("/sphome")
    public String newSphome(ModelMap model) {
        try {
//			List<FrontCategoryInfoVo> fileStoreInfoVos = fileStoreInfoService.getNewHomePage(num);
            String topicBars = FrontDisplayUtils.jsonSp(spadvertisementService.getSpByType("top"));
            model.put("tbars", topicBars);
//			model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping("/sprecommend")
    public String sprecommend(ModelMap model) {
        try {
            String data = FrontDisplayUtils.jsonSpOfRecommend(spadvertisementService.getSpByType("recommend"));
            model.put("result", ResultDict.SUCCESS.getCode());
            model.put("data", data);
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping("/sptopic")
    public String sptopic(ModelMap model) {
        try {
            String data = FrontDisplayUtils.jsonSpOfTopic(spadvertisementService.getSpByType("topic"));
            model.put("result", ResultDict.SUCCESS.getCode());
            model.put("data", data);
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
            e.printStackTrace();
        }
        return "home";
    }


    @Autowired
    public void setFileStoreInfoService(FileStoreInfoService fileStoreInfoService) {
        this.fileStoreInfoService = fileStoreInfoService;
    }

    @Autowired
    public void setAdvertisementService(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Autowired
    public void setSPAdvertisementService(SPAdvertisementService spadvertisementService) {
        this.spadvertisementService = spadvertisementService;
    }
}
