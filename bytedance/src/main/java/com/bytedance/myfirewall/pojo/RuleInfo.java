package com.bytedance.myfirewall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;

/**
 * 规则实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "reg_match")
public class RuleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //主键id

    private String reg_expr_name; //规则名字
    private String reg_expr; //正则表达式
    private String url; //匹配位置
    private Integer global_level; //全局等级
    private Integer local_level; //漏洞等级
    private Date time; //创建时间
    private String jump; //行为
    private String note; //备注
    private Integer is_default; //是否为默认规则


}
