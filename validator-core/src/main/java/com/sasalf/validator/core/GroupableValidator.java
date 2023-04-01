package com.sasalf.validator.core;

import cn.hutool.core.lang.Assert;

import java.util.*;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 15:13
 */
public class GroupableValidator<T> extends AbstractRuleBasedValidator<T, String> {

    /**
     * 分组规则的map
     */
    private final Map<String, Set<GroupableFuncRule<T>>> groupMap;

    /**
     * 构造方法，需要传入 resultDecider
     * @param resultDecider 结果决策者
     */
    public GroupableValidator(ResultDecider<T> resultDecider, Collection<GroupableFuncRule<T>> groupableRules) {
        super(resultDecider);
        groupMap = new HashMap<>();
        for (GroupableFuncRule<T> rule : groupableRules) {
            Set<String> groups = rule.groups();
            for (String group : groups) {
                groupMap.compute(group, (s, groupableFuncRules) -> {
                    if (groupableFuncRules == null) {
                        groupableFuncRules = new HashSet<>();
                    }
                    groupableFuncRules.add(rule);
                    return groupableFuncRules;
                });
            }
        }
    }

    /**
     * 获取规则
     * @param extraParam 额外参数
     * @return 获取规则
     */
    @Override
    protected Set<? extends Rule<T>> getRules(String extraParam) {
        Assert.notBlank(extraParam, "获取规则的key不能为空");
        return groupMap.get(extraParam);
    }

}
