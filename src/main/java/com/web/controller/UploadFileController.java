package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.model.dd.ResultDict;
import com.service.UpdateHeadService;
import com.utils.Constants;
import com.utils.convert.DBUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by hongjian.chen on 2018/6/11.
 */

@Controller
@RequestMapping("/heads")
public class UploadFileController extends BaseDecodedController {

    public static String headShow = "http://www.uichange.com/ums3-share/user/";
    private Logger logger = Logger.getLogger(UploadFileController.class);

//    @Resource
//    UpdateHeadService headService;

    DBUtils db = DBUtils.getInstance();

    @ResponseBody
    @RequestMapping("/upload")
    public String upload2(@ModelAttribute("decodedParams") JSONObject params, HttpServletRequest request) {
        Map map = new HashMap();
        String icon;
        String uid = request.getParameter("uid");
        if (params != null) {
            uid = params.getString("uid");
        }
//        String savePath = request.getSession().getServletContext().getRealPath("/img/");
        String savePath = Constants.HEADS_STORAGE;
        MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                String str = file.getOriginalFilename();
                if (file != null) {
                    String prefix = UUID.randomUUID().toString().replace("-", "");
                    String suffix = str.substring(str.lastIndexOf("."), str.length());
                    String fileName = uid + suffix;
                    icon = headShow + fileName;
                    File localFile = new File(savePath + fileName);
                    try {
                        file.transferTo(localFile);
                        List<Object> list = new ArrayList<>();
                        list.add(icon);
                        list.add(uid);
                        db.getConnection();
                        db.executeUpdate(" update uic.f_app_user set icon=? where id=?", list);
//                        headService.updateHead(uid, icon);
                        map.put("result", ResultDict.SUCCESS.getCode());
                        map.put("headUrl", icon);
                    } catch (Exception e) {
                        map.put("result", ResultDict.SYSTEM_ERROR.getCode());
                    } finally {
                        db.closeDB();
                    }
                }
            }
        }
        return JSON.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping("/download")
    public String download(@ModelAttribute("decodedParams") JSONObject params, HttpServletRequest request) {
        Map map = new HashMap();
        String uid = request.getParameter("uid");
        if (params != null) {
            uid = params.getString("uid");
        }
        db.getConnection();
        String icon = null;// headService.getUrl(uid);
        try {
            icon = db.getIcon("SELECT icon FROM uic.f_app_user where id=?", uid);
            map.put("headUrl", icon);
            map.put("result", ResultDict.SUCCESS.getCode());
        } catch (SQLException e) {
            map.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } finally {
            db.closeDB();
        }

        return JSON.toJSONString(map);
    }
}
