package com.bytedance.myfirewall.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 漏洞等级的抽象类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Levelinfo {


    public Integer sqlInjection;
    public Integer csrf;
    public Integer xss;
    public Integer ssrf;
    public Integer file;


}
