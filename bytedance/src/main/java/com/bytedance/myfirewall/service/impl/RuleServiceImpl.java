package com.bytedance.myfirewall.service.impl;


import com.bytedance.myfirewall.Enum.IsDefaultRule;
import com.bytedance.myfirewall.pojo.RuleInfo;
import com.bytedance.myfirewall.repository.RuleRepository;
import com.bytedance.myfirewall.service.RuleService;
import com.bytedance.myfirewall.utility.ConfigFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Properties;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public List<RuleInfo> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public RuleInfo insertByRuleInfo(RuleInfo ruleInfo) {

        return ruleRepository.save(ruleInfo);
    }

    @Override
    public void update(RuleInfo ruleInfo) {
        Integer id = ruleInfo.getId();
        if (ruleRepository.findById(id).isPresent()) {
            ruleRepository.save(ruleInfo);
        }
    }

    @Override
    public RuleInfo delete(Integer id) {
        RuleInfo ruleInfo = ruleRepository.findById(id).get();
        ruleRepository.delete(ruleInfo);
        return ruleInfo;
    }

    @Override
    public RuleInfo findById(Integer id) {
        return ruleRepository.findById(id).get();
    }

    @Override
    public String onDefaultRule(String action) throws IOException {
        //配置文件所在位置
        String filename = ConfigFile.configfile();
        Properties prop = new Properties();

        FileInputStream inFile = new FileInputStream(filename);

        try {
            //读取属性文件
            InputStream in = new BufferedInputStream(inFile);
            prop.load(in);     ///加载属性列表
            String userDefine = prop.getProperty("UserDefine");

            in.close();
            if (action.equals("read")) {
                return userDefine;
            }
            userDefine = userDefine.replace("\"", "");
            if (action.equals("switch")) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("UserDefine")) {
                        String newvalue = userDefine.equals(IsDefaultRule.USER_DEFINED.getStatus()) ?
                                IsDefaultRule.DEFAULT_RULE.getStatus() :
                                IsDefaultRule.USER_DEFINED.getStatus();
                        sb.append("UserDefine" + "=").append("\"").append(newvalue).append("\"").append("\n");
                    } else
                        sb.append(line).append("\n");
                }
                bufferedReader.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                bw.write(sb.toString());
                bw.close();
                return "success";
            }


        } catch (Exception e) {
            System.out.println(e);
        }
        inFile.close();
        return "error";
    }


}
