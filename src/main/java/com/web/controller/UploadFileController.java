package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by hongjian.chen on 2018/6/11.
 */

@Controller
@RequestMapping("/heads")
public class UploadFileController extends BaseController {
    @ResponseBody
    @RequestMapping("/upload")
    public String upload2(HttpServletRequest request) {
        String savePath = request.getSession().getServletContext().getRealPath("/img/");
        System.out.println(savePath + "\trequest\t" + request.getParameter("params"));

        File file1 = new File(savePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                int pre = (int) System.currentTimeMillis();
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    System.out.println("fileName=" + fileName);
                    if (fileName.trim() != "") {
                        File localFile = new File(savePath + fileName);
                        try {
                            file.transferTo(localFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return "{\"result\":\"200\"}";
                        }
                    }
                }
                int finalTime = (int) System.currentTimeMillis();
                System.out.println("上传时间=" + (finalTime - pre));
            }
        }
        String result = "{\"result\":\"000\"}";
        return result;
    }
}
