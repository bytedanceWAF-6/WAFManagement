package com.bytedance.myfirewall.repository;

import com.bytedance.myfirewall.pojo.Loginfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Loginfo, Integer> {
}
