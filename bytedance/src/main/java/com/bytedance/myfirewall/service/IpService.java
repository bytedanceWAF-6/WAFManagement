package com.bytedance.myfirewall.service;


import com.bytedance.myfirewall.pojo.IPinfo;

import java.util.List;

public interface IpService {
    List<IPinfo> findAll();

    IPinfo delete(Integer id);

    IPinfo insert(IPinfo iPinfo);

    IPinfo update(IPinfo iPinfo);


}
