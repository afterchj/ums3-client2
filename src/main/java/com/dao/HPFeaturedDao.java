package com.dao;

import com.model.HPFeatured;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by nannan.li on 2018/6/7.
 */

public interface HPFeaturedDao {
    List<HPFeatured> getHPFeatured();
}
