package com.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.model.SPFile;
import com.model.SPTopic;
import com.model.dd.ResultDict;
import com.service.SPTopicService;
import com.utils.DateUtil;
import com.web.vo.SPTopicVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller("spTopicController")
@RequestMapping("/sptopic")
public class SPTopicController extends BaseDecodedController {
    @Resource(name = "spTopicService")
    private SPTopicService spTopicService;
    private Logger logger = Logger.getLogger(SPTopicController.class);

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException re, HttpServletRequest request) {
        if (!(re instanceof TypeMismatchException)) {
            logger.error("sptopic." + re.getMessage());
        }
        return "forward:/app/f/home";
    }

    /**
     * 专题页面,显示所有专题
     */
    @RequestMapping(value = "/show/home", method = RequestMethod.POST)
    public String showTopics(@ModelAttribute("decodedParams") JSONObject decodedParams, ModelMap model) {
        //返回所有的专题
        List<SPTopic> spTopics = spTopicService.showTopics();
        //对sptopics判空处理
        if (spTopics == null || spTopics.size() <= 0) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
            return null;
        }
        //创建一个类型为SPTopicVo的集合
        List<SPTopicVo> spTopicVos = new ArrayList<>();
        //遍历sptopics将SPTopic转换成SPTopicVo并加到sptopicVos集合中
        for (SPTopic spTopic : spTopics) {
            spTopicVos.add(paramSPTopicVo(spTopic));
        }
        //对sptopicVos判空处理
        if (spTopicVos != null && spTopicVos.size() > 0) {
            //返回状态码"000"表示成功
            model.put("result", ResultDict.SUCCESS.getCode());
            //返回sptopicVos
            model.put("topics", spTopicVos);
        } else {
            //参数解析错误
            model.put("result", ResultDict.PARAMS_NOT_PARSED.getCode());
        }
        return null;
    }

    /**
     * 显示单个专题的具体内容
     */
    @RequestMapping(value = "/seek/one", method = RequestMethod.POST)
    public String showOneTopic(@ModelAttribute("decodedParams") JSONObject decodedParams, ModelMap model) {
        //对参数判空处理
        if (StringUtils.isBlank(decodedParams.toJSONString())) {
            //返回参数不能为空状态码
            model.put("result", ResultDict.PARAMS_BLANK.getCode());
            return null;
        }
        //获取传入的topicId
        String topicId = decodedParams.getString("topicId");
        //对参数判空处理
        if (StringUtils.isBlank(topicId)) {
            //返回参数不能为空状态码
            model.put("result", ResultDict.PARAMS_BLANK.getCode());
            return null;
        }
        //返回所有的专题
        List<SPTopic> sptopics = spTopicService.showTopics();
        //对sptopics判空处理
        if (sptopics == null || sptopics.size() <= 0) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
            return null;
        }
        //获取该id的专题信息
        for (SPTopic spTopic : sptopics) {
            //如果id相等,将专题对象赋给topic
            if (topicId.equals(String.valueOf(spTopic.getId()))) {
                model.put("topic", spTopic);
            }
        }
        //根据topicId获取专题内容
        List<SPFile> files = spTopicService.showFilesByTopicId(Integer.parseInt(topicId));
        //对files判空处理
        if (files == null || files.size() <= 0) {
            //查不到返回系统错误状态码
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
            return null;
        }
        //返回状态码"000"表示成功
        model.put("result", ResultDict.SUCCESS.getCode());
        //返回fileVos
        model.put("files", files);
        return null;
    }

    /**
     * 将后台查询的专题信息对象转换为前端显示的专题对象
     *
     * @param spTopic
     * @return
     */
    public static SPTopicVo paramSPTopicVo(SPTopic spTopic) {
        SPTopicVo vo = new SPTopicVo();
        vo.setId(spTopic.getId());
        vo.setName(spTopic.getName());
        vo.setValue(spTopic.getValue());
        vo.setDescription(spTopic.getDescription());
        vo.setIcon(spTopic.getIcon());
        vo.setSort(spTopic.getSort());
        vo.setEditDate(DateUtil.format(spTopic.getEditDate(), "yyyy-MM-dd HH:mm:ss"));
        return vo;
    }

}
