package com.sasalf.validator.core;

import cn.hutool.core.lang.Assert;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 15:19
 */
public class GroupableFuncRule<T> extends FunctionalRule<T> implements Groupable {

    /**
     * 属于的分组
     */
    private final Set<String> groups;

    /**
     * constructor
     * @param consumer consumer
     */
    public GroupableFuncRule(String ruleId, Consumer<T> consumer, Set<String> groups) {
        super(ruleId, consumer);
        Assert.notEmpty(ruleId, "规则id不能为空");
        Assert.notEmpty(groups, "分组不能为空");
        this.groups = groups;
    }

    /**
     * 获取分组
     * @return 分组信息
     */
    @Override
    public Set<String> groups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
