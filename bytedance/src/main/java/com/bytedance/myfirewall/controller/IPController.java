package com.bytedance.myfirewall.controller;

import com.bytedance.myfirewall.pojo.IPinfo;
import com.bytedance.myfirewall.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/IP")
public class IPController {
    @Autowired
    IpService ipService;

    @GetMapping()
    public List<IPinfo> findAll() {

        return ipService.findAll();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteIP(@PathVariable(name = "id") Integer id) {
        ipService.delete(id);
        return "success";
    }

    @PostMapping(value = "/update")
    public String insertIP(@RequestBody IPinfo iPinfo) {
        ipService.insert(iPinfo);
        return "success";
    }

    @PostMapping(value = "/update/{id}")
    public String updateIP(@RequestBody IPinfo iPinfo) {
        ipService.update(iPinfo);
        return "success";
    }


}
