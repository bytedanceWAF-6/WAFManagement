package com.bytedance.myfirewall.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 流量实体类
 */
@Data
@Entity
@Table(name = "log")
public class Loginfo {
    @Id
    private Integer id;
    @Column(name = "simplify_content")
    @JsonProperty("simplify_content")
    private String scontent;
    @Column(name = "full_content")
    @JsonProperty("full_content")
    private String fcontent;
    private String jump;
    @Column(name = "time")
    @JsonProperty("time")
    private Date created;
}
