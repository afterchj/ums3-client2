package com.cache;

public enum MemcachedObjectType {

    //===========================日志key前缀==============================

    COUNTER_WORK_VISITOR("counter_work_visitor:", 60 * 60 * 24),
    
    COUNTER_MEMBER_VISITOR("counter_member_visitor:", 60 * 60 * 24),
    
    COUNTER_USER_VISITOR("counter_user_visitor:", 60 * 60 * 24),
    
    COUNTER_WORK_PRAISER("counter_work_praiser:", 60 * 60 * 24),
    
    USER_TOKEN("user_token:", 30 * 24 * 3600), 
    
    CACHE_FILTERED_USER("ums3_cache_filtered_user_%s_%s",  30 * 24 * 3600),
    
    CACHE_FILTERED_USER_NUM("ums3_cache_filtered_user_%s_%s_num",  30 * 24 * 3600), 
    
    COUNTER_GOODS_STOCK("ums3_counter_goods_stock_%s", 30 * 24 * 3600), 
    
    COUNTER_GOODS_SALES("ums3_counter_goods_sales_%s", 30 * 24 * 3600),
    
    CACHE_USER_TASK_DATAMAP("MemcachedState-%s-%s-dataMap", 0);


    //=================================================================

    private String prefix;
    private int expiredTime;

    MemcachedObjectType(String prefix, int expiredTime) {
        this.prefix = prefix;
        this.expiredTime = expiredTime;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiredTime() {
        return expiredTime;
    }
}
