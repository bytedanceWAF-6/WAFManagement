package com.bytedance.myfirewall.utility;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


import javax.servlet.http.HttpServletResponse;


/**
 * 命令执行
 */
public class ExecCmd {

    /*
     * 执行dos命令的方法
     * @param command 需要执行的dos命令
     * @param file 指定开始执行的文件目录
     *
     * @return true 转换成功，false 转换失败
     */
    public static String executeCommand(String command, File file) {
        StringBuilder output = new StringBuilder();
        Process p;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            p = Runtime.getRuntime().exec(command, null, file);
            p.waitFor();
            inputStreamReader = new InputStreamReader(p.getInputStream(), "GBK");
            reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
        }
        System.out.println(output.toString());
        return output.toString();
    }

}
