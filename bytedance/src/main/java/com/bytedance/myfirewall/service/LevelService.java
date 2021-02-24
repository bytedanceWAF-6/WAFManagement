package com.bytedance.myfirewall.service;

import com.bytedance.myfirewall.pojo.Levelinfo;

import java.util.Map;

public interface LevelService {
    Levelinfo findLevel();

    String changeLevel(Levelinfo levelinfo);
}
