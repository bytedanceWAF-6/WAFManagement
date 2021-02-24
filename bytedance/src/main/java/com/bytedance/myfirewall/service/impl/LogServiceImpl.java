package com.bytedance.myfirewall.service.impl;

import com.bytedance.myfirewall.pojo.Loginfo;
import com.bytedance.myfirewall.repository.LogRepository;
import com.bytedance.myfirewall.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogRepository logRepository;

    @Override
    public List<Loginfo> findAll() {
        return logRepository.findAll();
    }

    @Override
    public String deleteAllLog() {
        logRepository.deleteAll();
        return "success";
    }
}
