package com.model;

import com.google.common.collect.Lists;
import com.utils.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nannan.li on 2018/7/20.
 */
public class MNumValidation implements Serializable {

    private static final long serialVersionUID = 1L;
    private String value;
    private String key;
    private String formatter;
    private Long createDate = System.currentTimeMillis();
    private Integer interval;
    private Long deadline;
    private Integer trial;
    private List<Date> postDate = Lists.newArrayList();

    private MNumValidation(String key, String value, String formatter, Integer interval, Integer trial) {
        super();
        this.key = key;
        this.value = value;
        this.interval = interval;
        this.deadline = System.currentTimeMillis() + interval * 1000;
        this.trial = trial;
        this.formatter = formatter;
    }

    public static class Builder {
        private Integer interval;
        private Integer trail = 3;
        private String key;
        private String formatter = "%s（天天锁屏手机动态验证码）为了您的账号安全，验证码请勿转发给他人。";
        private String code;

        public Builder(String mobile, String type, Integer interval, String appid) {
            this(mobile, type, interval, appid, 3);
        }

        public Builder(String mobile, String type, Integer interval, String appid, Integer trail) {
            this.key = type + "_" + mobile;
            this.interval = interval;
            this.trail = trail;
            this.formatter = Constants.propertiesLoader.getProperty("message.sender.appid" + appid + ".msg");
        }

        public Builder(String mobile, String value, int expiredTime, String appid, String code) {
            this(mobile, value, expiredTime, appid);
            this.code = code;
        }

        public MNumValidation getModel() {
//			String code = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
            String code = StringUtils.isBlank(this.code) ? getRandomeNum(6) : this.code;
            MNumValidation invitation = new MNumValidation(key, code, formatter, interval, trail);
            return invitation;
        }

        private String getRandomeNum(int len) {
            Random r = new Random();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<len; i++){
                sb.append(r.nextInt(10));
            }
            return sb.toString();
        }

    }

    public Long getCreateDate() {
        return createDate;
    }

    public Integer getInterval() {
        return interval;
    }

    public Long getDeadline() {
        return deadline;
    }

    public Integer getTrial() {
        return trial;
    }

    public List<Date> getPostDate() {
        return postDate;
    }


    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MNumValidation other = (MNumValidation) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

}

