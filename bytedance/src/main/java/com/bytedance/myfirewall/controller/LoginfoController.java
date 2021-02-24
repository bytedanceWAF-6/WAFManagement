package com.bytedance.myfirewall.controller;

import com.bytedance.myfirewall.pojo.Levelinfo;
import com.bytedance.myfirewall.pojo.Loginfo;
import com.bytedance.myfirewall.service.LevelService;
import com.bytedance.myfirewall.service.LogService;
import com.bytedance.myfirewall.utility.ExecCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;
import java.util.Map;


/**
 * 处理/loginfo及其他接口
 */
@RestController
public class LoginfoController {
    @Autowired
    LogService logService;
    @Autowired
    LevelService levelService;

    //查询所有日志
    @GetMapping(value = "/loginfo")
    public List<Loginfo> getLogList() {
        return logService.findAll();
    }


    //删除所有日志流量
    @GetMapping(value = "/loginfo/delete")
    public String deleteAllLog() {
        return logService.deleteAllLog();
    }


    @GetMapping("/reload")
    public String reload() {

        String command = "./nginx -s reload";
        String output = ExecCmd.executeCommand(command, new File("/usr/local/openresty/nginx/sbin"));
        System.out.println(output);
        return "success";
    }

    //查看当前防护等级
    @GetMapping(value = "/level")
    public Levelinfo findLevelOfDefence() {

        return levelService.findLevel();
    }

    @PostMapping(value = "/level")
    public String changeLevel(@RequestBody Levelinfo levelinfo) {

        return levelService.changeLevel(levelinfo);
    }

}
