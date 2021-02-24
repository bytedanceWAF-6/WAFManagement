package com.bytedance.myfirewall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bytedance.myfirewall.pojo.Levelinfo;
import com.bytedance.myfirewall.service.LevelService;
import com.bytedance.myfirewall.utility.ConfigFile;

import org.springframework.stereotype.Service;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Service
public class LevelServiceImpl implements LevelService {


    @Override
    public Levelinfo findLevel() {
        String filename = ConfigFile.configfile();
        Properties prop = new Properties();
        Levelinfo levelinfo = new Levelinfo();
        Map<String, String> map = new HashMap<>();
        try {
            FileInputStream inFile = new FileInputStream(filename);
            //读取属性文件
            InputStream in = new BufferedInputStream(inFile);
            prop.load(in);     ///加载属性列表
            String level = prop.getProperty("Level");

            if (level.equals("{}"))
                return levelinfo;

            String[] split = level.substring(1, level.length() - 1).split(",");

            for (String str : split) {
                String[] kv = str.split("=");
                map.put(kv[0], kv[1]);
            }
            Field[] field = levelinfo.getClass().getDeclaredFields();
            for (Field fi : field) {
                // 判断key值是否存在
                String fieldname = fi.getName();
                if (map.containsKey(fieldname)) {
                    String value = map.get(fieldname).toString();
                    // 将属性的第一个字母转换为大写
                    String frist = fi.getName().substring(0, 1).toUpperCase();
                    // 属性封装set方法
                    String setter = "set" + frist + fi.getName().substring(1);
                    // 获取当前属性类型
                    Class<?> type = fi.getType();
                    // 获取JavaBean的方法,并设置类型
                    Method method = levelinfo.getClass().getMethod(setter, type);
                    method.invoke(levelinfo, Integer.parseInt(value));
                }
            }

        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return levelinfo;
    }

    @Override
    public String changeLevel(Levelinfo levelinfo) {
        String filename = ConfigFile.configfile();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Level")) {
                    Map<String, Object> stringObjectMap = JSON.parseObject(JSON.toJSONString(levelinfo), new TypeReference<Map<String, Object>>() {
                    });
                    sb.append("Level" + "=").append(stringObjectMap.toString().replace(" ", "")).append("\n");
                } else
                    sb.append(line).append("\n");
            }
            bufferedReader.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
