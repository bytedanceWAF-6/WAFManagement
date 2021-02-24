package com.bytedance.myfirewall.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * IPList实体类
 */
@Data
@Entity
@Table(name = "ip_list")
public class IPinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ip;
    private String bow;
}
