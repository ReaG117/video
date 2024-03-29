package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.Notice;
import com.gk.study.mapper.NoticeMapper;
import com.gk.study.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    NoticeMapper mapper;

    @Override
    public List<Notice> getNoticeList() {
        return mapper.selectList(new QueryWrapper<>());
    }

    @Override
    @Transactional
    public void createNotice(Notice notice) {
        notice.setCreateTime(String.valueOf(System.currentTimeMillis()));
        mapper.insert(notice);
    }

    @Override
    @Transactional
    public void deleteNotice(String id) {
        mapper.deleteById(id);
    }

    @Override
    @Transactional
    public void updateNotice(Notice notice) {
        mapper.updateById(notice);
    }
}
