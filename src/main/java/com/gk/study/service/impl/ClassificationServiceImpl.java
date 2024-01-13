package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.mapper.ClassificationMapper;
import com.gk.study.entity.Classification;
import com.gk.study.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassificationServiceImpl extends ServiceImpl<ClassificationMapper, Classification> implements ClassificationService {

    @Autowired
    ClassificationMapper mapper;

    @Override
    public List<Classification> getClassificationList() {
        return mapper.selectList(new QueryWrapper<>());
    }

    @Override
    @Transactional
    public void createClassification(Classification classification) {
        classification.setCreateTime(String.valueOf(System.currentTimeMillis()));
        mapper.insert(classification);
    }

    @Override
    @Transactional
    public void deleteClassification(String id) {
        mapper.deleteById(id);
    }

    @Override
    @Transactional
    public void updateClassification(Classification classification) {
        mapper.updateById(classification);
    }
}
