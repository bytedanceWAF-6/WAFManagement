package com.bytedance.myfirewall.service.impl;

import com.bytedance.myfirewall.pojo.IPinfo;
import com.bytedance.myfirewall.repository.IpRepository;
import com.bytedance.myfirewall.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpServiceImpl implements IpService {
    @Autowired
    IpRepository ipRepository;

    @Override
    public List<IPinfo> findAll() {
        return ipRepository.findAll();
    }

    @Override
    public IPinfo delete(Integer id) {
        IPinfo iPinfo = ipRepository.findById(id).get();
        ipRepository.delete(iPinfo);
        return iPinfo;
    }

    @Override
    public IPinfo insert(IPinfo iPinfo) {

        return ipRepository.save(iPinfo);
    }

    @Override
    public IPinfo update(IPinfo iPinfo) {

        return ipRepository.save(iPinfo);
    }
}
