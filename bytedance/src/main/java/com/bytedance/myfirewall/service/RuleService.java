package com.bytedance.myfirewall.service;

import com.bytedance.myfirewall.pojo.RuleInfo;

import java.io.IOException;
import java.util.List;

public interface RuleService {
    //查询所有规则
    List<RuleInfo> findAll();

    //添加规则
    RuleInfo insertByRuleInfo(RuleInfo ruleInfo);

    //更新
    void update(RuleInfo ruleInfo);

    //删除
    RuleInfo delete(Integer id);

    //查找单条记录
    RuleInfo findById(Integer id);


    //查看自定义规则启用状态
    String onDefaultRule(String action) throws IOException;


}
