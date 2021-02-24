package com.bytedance.myfirewall.repository;

import com.bytedance.myfirewall.pojo.RuleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<RuleInfo, Integer> {

}
