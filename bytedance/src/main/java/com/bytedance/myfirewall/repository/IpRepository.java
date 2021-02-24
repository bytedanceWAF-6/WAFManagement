package com.bytedance.myfirewall.repository;

import com.bytedance.myfirewall.pojo.IPinfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRepository extends JpaRepository<IPinfo, Integer> {
}
