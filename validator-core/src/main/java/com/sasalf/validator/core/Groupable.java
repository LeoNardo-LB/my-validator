package com.sasalf.validator.core;

import java.util.Set;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 15:16
 * 可分组接口
 */
public interface Groupable {

    /**
     * 获取分组
     * @return 分组信息
     */
    Set<String> groups();

}
