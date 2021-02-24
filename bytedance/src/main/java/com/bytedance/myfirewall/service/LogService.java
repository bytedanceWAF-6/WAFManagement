package com.bytedance.myfirewall.service;


import com.bytedance.myfirewall.pojo.Loginfo;

import java.util.List;

public interface LogService {

    //解析文件，将属性封装到loginfo实体类
    List<Loginfo> findAll();

    //删除日志文件
    String deleteAllLog();
}
