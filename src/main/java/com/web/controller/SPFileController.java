package com.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.model.SPFile;
import com.model.dd.ResultDict;
import com.service.SPFileDownloadService;
import com.service.SPFileService;
import com.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

/**
 * Created by yuanjie.fang on 2017/11/2.
 */
@Controller("spFileController")
@RequestMapping("/spfile")
public class SPFileController extends BaseDecodedController {
    private Logger logger = Logger.getLogger(SPFileController.class);

    private SPFileService spFileService;
    private SPFileDownloadService spFileDownloadService;

    //分页获取热门必看列表
    @RequestMapping(value = "/hottest/p{page}", method = RequestMethod.GET)
    public String getSPFileHottest(@PathVariable("page") Integer page, ModelMap model) {
        try {
            List<SPFile> hottestSPFile = spFileService.getHottestPage(page);
            model.put("data", hottestSPFile);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (Exception e) {
//            e.printStackTrace();
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    //分页获取  新作力荐->点击更多
    @RequestMapping(value = "/newest/p{page}", method = RequestMethod.GET)
    public String getSPFileNewest(@PathVariable("page") Integer page, ModelMap model) {
        try {
            List<SPFile> newestSPFile = spFileService.getNewestPage(page);
            model.put("data", newestSPFile);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    //推荐页->新作力荐
    @RequestMapping(value = "/recommend/all", method = RequestMethod.POST)
    public String getSPFileRecommend(@ModelAttribute("decodedParams") JSONObject decodedParams, ModelMap model) {
        try {
            List<SPFile> recommendSPFile = spFileService.getRecommendFiles();
            model.put("data", recommendSPFile);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    //获取某个视频文件
    @RequestMapping(value = "/getSPFile", method = RequestMethod.POST)
    public String getSPFile(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        try {
            Integer fileId = params.getInteger("fileId");
            SPFile spFile = spFileService.findById(fileId);
            model.put("data", spFile);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }


    //更新视频文件下载量
    @RequestMapping(value = "/totalDownload", method = RequestMethod.POST)
    public String updateTotalDown(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        Integer fileId = params.getInteger("fileId");
        String logDate = DateUtil.format(new Date(), "yyyy-MM-dd");
        try {
            spFileDownloadService.updateTotalDown(fileId, logDate);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }

        return null;
    }

    @Autowired
    public void setSpFileService(SPFileService spFileService) {
        this.spFileService = spFileService;
    }

    @Autowired
    public void setSpFileDownloadService(SPFileDownloadService spFileDownloadService) {
        this.spFileDownloadService = spFileDownloadService;
    }
}
