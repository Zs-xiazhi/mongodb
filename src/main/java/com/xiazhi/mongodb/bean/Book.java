package com.xiazhi.mongodb.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ZhaoShuai
 * @date Create in 2020/5/7
 **/
@Data
@Accessors(chain = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String content;
    private BigDecimal price;
}
