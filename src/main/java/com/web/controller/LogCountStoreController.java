package com.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.ResultDict;
import com.service.LogCountStoreService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuanjie.fang on 2017/11/2.
 */
@Controller("logCountStoreController")
@RequestMapping("/logCountStore")
public class LogCountStoreController extends BaseDecodedController {

    private LogCountStoreService logCountStoreService;
    private String defaultTheme = "熊猫赚";

    @RequestMapping(value = "/totalDown", method = RequestMethod.POST)
    public String updateTotalDown(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        String themeName = params.getString("themeName");
        if (StringUtils.isEmpty(themeName)) {
            themeName = defaultTheme;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String logDate = sdf.format(new Date());
        try {
            logCountStoreService.updateTotalDown(themeName, logDate);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    @RequestMapping(value = "/totalVisit", method = RequestMethod.POST)
    public String updateTotalVisit(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        String themeName = params.getString("themeName");
        if (StringUtils.isEmpty(themeName)) {
            themeName = defaultTheme;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String logDate = sdf.format(new Date());
        try {
            logCountStoreService.updateTotalVisit(themeName, logDate);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    @Autowired
    public void setLogCountStoreService(LogCountStoreService logCountStoreService) {
        this.logCountStoreService = logCountStoreService;
    }

}
