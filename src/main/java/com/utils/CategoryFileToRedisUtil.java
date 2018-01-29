package com.utils;

/**
 * Created by yuanjie.fang on 2017/8/10.
 */
public class CategoryFileToRedisUtil {
//    @Autowired
//    private RedisTemplate<String, ?> redisTemplate;
//
//    //修改redis数据库
//    public void updateRedis(){
//        String[] categoryArray = Constants.CATEGORY_LIST.split(",");
//
//        Integer categoryId = 178;
//        CategoryFileToRedisServiceImpl service = new CategoryFileToRedisServiceImpl();
//        List<CategoryFileToRedis> categoryFiles = service.findCategoryFileByCategoryId(categoryId);
//
//        for (CategoryFileToRedis file : categoryFiles) {
//            file.setDownloadTimes(null);
//        }
//        System.out.println(categoryFiles);
//
//        //读取hash助手
//        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
//        Map<Object, Object> resultMap = hash.entries("ums3_locker_category");
//
//        for (Object key : resultMap.keySet()) {
//
//            Object value = resultMap.get(key);
//
//            System.out.println("Key = " + key + ", Value = " + value);
//
//        }
//    }




}
