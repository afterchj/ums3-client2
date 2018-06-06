package com.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.web.vo.SPAdvertisementVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.web.vo.AdvertisementVo;
import com.web.vo.FileStoreInfoVo;

public class FrontDisplayUtils {

	private static final Logger logger = Logger.getLogger(FrontDisplayUtils.class);
	
	public static void setDownloadType(FileStoreInfoVo fsi) {

		StringBuilder httpBuffer = new StringBuilder();
		httpBuffer.append("http://"
				+ StringUtils.remove(Constants.STATIC_SHOW, "http://")
				+ "/");
		httpBuffer.append("files/");
		if (fsi.getTheme().getDtype().equals("1")) {
			httpBuffer.append(fsi.getTheme().getUxPath());
		} else {
			httpBuffer.append(fsi.getTheme().getApkPath());
		}

//		try {
//			httpBuffer.append("?title=" + URLEncoder.encode(fsi.getTitle(), "utf-8"));
//			httpBuffer.append(URLEncoder.encode("|", "utf-8")).append(URLEncoder.encode(fsi.getTheme().getTitle(), "utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			logger.error("DownloadUrlUtils " + e.getMessage() + " title:" + fsi.getTitle() + " theme.title" + fsi.getTheme().getTitle());
//		}
		fsi.getTheme().setDownloadURL(httpBuffer.toString());
	}
	
	public static String json(List<AdvertisementVo> ads){
        StringBuilder buffer=new StringBuilder();
        buffer.append("[");
        for(AdvertisementVo ad:ads){
            buffer.append("{");
            buffer.append("\"pic\":\"" + Constants.STATIC_SHOW + "/files/"+ad.getImgLink()+"\"");
            buffer.append(",");
            buffer.append("\"href\":\""+ad.getLink()+"\"");
            buffer.append(",");
            buffer.append("\"type\":\""+ad.getType()+"\"");
            buffer.append(",");
            try {
				buffer.append("\"name\":"+URLEncoder.encode(ad.getName(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            buffer.append("}");
            buffer.append(",");
        }
        String json=StringUtils.substringBeforeLast(buffer.toString(),",");
        json+="]";
        return json;
    }

	public static String jsonSp(List<SPAdvertisementVo> ads){
		StringBuilder buffer=new StringBuilder();
		buffer.append("[");
		for(SPAdvertisementVo ad:ads){
			buffer.append("{");
			buffer.append("\"pic\":\"" + Constants.STATIC_SHOW + "/files/"+ad.getImgLink()+"\"");
			buffer.append(",");
			buffer.append("\"href\":\""+ad.getLink()+"\"");
			buffer.append(",");
			buffer.append("\"type\":\""+ad.getType()+"\"");
			buffer.append(",");
			buffer.append("\"name\":\""+ad.getName()+"\"");
			buffer.append("}");
			buffer.append(",");
		}
		String json=StringUtils.substringBeforeLast(buffer.toString(),",");
		json+="]";
		return json;
	}

    public static String jsonSpOfRecommend(List<SPAdvertisementVo> ads){
        StringBuilder buffer=new StringBuilder();
        buffer.append("[");
        for(SPAdvertisementVo ad:ads){
            buffer.append("{");
            buffer.append("\"id\":\""+ad.getLink()+"\"");
            buffer.append(",");
			buffer.append("\"name\":\""+ad.getName()+"\"");
            buffer.append(",");
            buffer.append("\"value\":\""+ad.getValue()+"\"");
            buffer.append(",");
            buffer.append("\"description\":\""+ad.getDescription()+"\"");
            buffer.append(",");
            buffer.append("\"icon\":\"" + Constants.STATIC_SHOW + "/files/"+ad.getImgLink()+"\"");
            buffer.append(",");
            buffer.append("\"dtype\":\""+ad.getDtype()+"\"");
            buffer.append("}");
            buffer.append(",");
        }
        String json=StringUtils.substringBeforeLast(buffer.toString(),",");
        json+="]";
        return json;
    }

	public static String jsonSpOfTopic(List<SPAdvertisementVo> ads){
		StringBuilder buffer=new StringBuilder();
		buffer.append("[");
		for(SPAdvertisementVo ad:ads){
			buffer.append("{");
			buffer.append("\"id\":\""+ad.getLink()+"\"");
			buffer.append(",");
			buffer.append("\"name\":\""+ad.getName()+"\"");
			buffer.append(",");
			buffer.append("\"value\":\""+ad.getValue()+"\"");
			buffer.append(",");
			buffer.append("\"description\":\""+ad.getDescription()+"\"");
			buffer.append(",");
			buffer.append("\"icon\":\"" + Constants.STATIC_SHOW + "/files/"+ad.getImgLink()+"\"");
			buffer.append(",");
			buffer.append("\"sort\":\""+ad.getSort()+"\"");
			buffer.append(",");
			buffer.append("\"editDate\":\""+DateUtil.format(ad.getCreateTime(), "yyyy-MM-dd HH:mm:ss")+"\"");
			buffer.append("}");
			buffer.append(",");
		}
		String json=StringUtils.substringBeforeLast(buffer.toString(),",");
		json+="]";
		return json;
	}
}
