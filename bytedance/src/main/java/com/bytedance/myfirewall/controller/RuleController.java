package com.bytedance.myfirewall.controller;

import com.bytedance.myfirewall.pojo.RuleInfo;
import com.bytedance.myfirewall.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理/rule及其子接口
 */
@RestController
@RequestMapping(value = "/rule")
public class RuleController {

    @Autowired
    RuleService ruleService;

    //获取当前所有规则列表
    @GetMapping()
    public List<RuleInfo> getRuleList() {

        return ruleService.findAll();
    }

    //删除一条规则
    @GetMapping(value = "/delete/{id}")
    public String deleteRule(@PathVariable(name = "id") Integer id) {
        ruleService.delete(id);
        return "success";
    }

    //更新一个规则的详细信息，返回值组装为一个RuleInfo实体类
    @PostMapping(value = "/update/{id}")
    public String updateRule(@RequestBody RuleInfo ruleInfo) {
        ruleInfo.setTime(new Date());
        ruleService.update(ruleInfo);
        return "success";
    }

    //插入一个新规则
    @PostMapping(value = "/update")
    public String insertRule(@RequestBody RuleInfo ruleInfo) {
        ruleInfo.setTime(new Date());
        ruleService.insertByRuleInfo(ruleInfo);
        return "success";
    }

    //查看自定义规则启用状态
    @GetMapping(value = "/userdefine")
    public Map<String, String> readStatus() throws IOException {
        String status = ruleService.onDefaultRule("read");
        Map<String, String> map = new HashMap<>();
        map.put("UserDefine", status);
        return map;
    }

    //启用/禁用自定义规则
    @GetMapping(value = "/switchuserdefine")
    public String switchStatus() throws IOException {

        return ruleService.onDefaultRule("switch");
    }


}
