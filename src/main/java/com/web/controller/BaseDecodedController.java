package com.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;

public class BaseDecodedController extends BaseController {

    public BaseDecodedController() {
        super.encryption = true;
    }

    @ModelAttribute("decodedParams")
    public JSONObject beforeInvokingHandlerMethod(HttpServletRequest request) {
        if (request.getAttribute("decodedParams") == null) {
            return JSON.parseObject(request.getParameter("params"));
        }
        return (JSONObject) request.getAttribute("decodedParams");
    }

}
