package com.bytedance.myfirewall.utility;


public class ConfigFile {
    static String filename = "/usr/local/openresty/nginx/conf/waf/config.lua";

    public static String configfile() {
        return filename;

    }
}
