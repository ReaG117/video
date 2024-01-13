package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.service.BannerService;
import com.gk.study.entity.Banner;
import com.gk.study.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    @Autowired
    BannerMapper mapper;

    @Override
    public List<Banner> getBannerList() {
        return mapper.selectList(new QueryWrapper<>());
    }

    @Override
    @Transactional
    public void createBanner(Banner banner) {
        banner.setCreateTime(String.valueOf(System.currentTimeMillis()));
        mapper.insert(banner);
    }

    @Override
    @Transactional
    public void deleteBanner(String id) {
        mapper.deleteById(id);
    }

    @Override
    @Transactional
    public void updateBanner(Banner banner) {
        mapper.updateById(banner);
    }
}
