package com.service.impl;

import com.dao.HPFeaturedDao;
import com.model.HPFeatured;
import com.service.HPFeaturedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nannan.li on 2018/6/7.
 */
@Service
public class HPFeaturedServiceImpl implements HPFeaturedService {

    @Resource
    private HPFeaturedDao hpFeaturedDao;

    @Override
    public List<HPFeatured> getHPFeatured() {
        return hpFeaturedDao.getHPFeatured();
    }
}
